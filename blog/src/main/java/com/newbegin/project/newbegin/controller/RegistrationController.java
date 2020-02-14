package com.newbegin.project.newbegin.controller;

import com.newbegin.project.newbegin.model.User;
import com.newbegin.project.newbegin.repository.UserRepository;
import com.newbegin.project.newbegin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/reg")
    public String registration() { return "reg"; }


    @PostMapping("/reg")
    public String addUser(@Valid User user, BindingResult result, Model model) throws MessagingException {

        if (StringUtils.isEmpty(user.getEmail())) {
            model.addAttribute("emailError", "Заполните почту");
        }

        if (result.hasErrors()) {
            Map<String, String> errors = ControllerUtil.getErrors(result);
            model.mergeAttributes(errors);
            return "reg";
        }
        if(userService.isEmailFree(user.getEmail())!=null){
            model.addAttribute("emailError", "Почта уже используется");
            return "reg";
        }

        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "Пользователь с таким именем существует");
            return "reg";
        }


        return "redirect:/login";
    }


    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model) {
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("message", "Активация прошла успешно");
        } else {
            model.addAttribute("Код активации не найден");
        }
        return "login";
    }
    @PostMapping("/forgotPassword")
    public String forgotPassword(@RequestParam String email, Model model) throws MessagingException {
        boolean isEmailExists = userService.forgotPassword(email);
        if (isEmailExists) {
            model.addAttribute("message", "Проверьте почту");
        } else {
            model.addAttribute("message", "Почта не найдена");
        }
        return "forgotPassword";
    }

    @GetMapping("/forgotPassword")
    public String forgot() {
        return "forgotPassword";
    }


    @GetMapping("/resetPassword")
    public String res() {
        return "resetPassword";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam String password,
                                @RequestParam String username,
                                @RequestParam String code, Model model) {
        String regex = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{6,}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        User user = userRepository.findByUsername(username);
        if (user == null) {
            model.addAttribute("usernameError", "Юзер не найден");
            return "resetPassword";
        }
        if(!matcher.matches()){
            model.addAttribute("passwordError", "Пароль должен быть от 6 символов" +
                    ", содержать верхний и нижний регистр, цифры и символы");
            return "resetPassword";
        }
        if(StringUtils.isEmpty(code)){
            model.addAttribute("codeError", "Заполните это поле");
            return "resetPassword";
        }

        boolean resetPass = userService.resetPass(username, password, code);
        if(!resetPass){
            model.addAttribute("message", "Ошибка. Попробуйте еще раз сбросить пароль");
            return "resetPassword";
        }
        model.addAttribute("message", "Пароль обновлен");
        return "login";
    }

}
