package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam("confirmPassword") String confirmPassword, @Valid User user,
                          BindingResult bindingResult, Model model) {

        Zxcvbn passwordCheck = new Zxcvbn();
        Strength strength = passwordCheck.measure(user.getPassword());

        boolean isConfirmEmpty = StringUtils.isEmpty(confirmPassword);

        boolean checkPassword = (user.getPassword() != null);

        boolean differentPasswords = checkPassword & !user.getPassword().equals(confirmPassword);

        if (isConfirmEmpty) {
            model.addAttribute("confirmPasswordError", "Поле не может быть пустым");
        }

        if (!isConfirmEmpty) {
            if (differentPasswords) {
                model.addAttribute("passwordError", "Пароли не совпадают");
            } else if (strength.getScore() < 3) {
                model.addAttribute("passwordError", "Пароль легкий");
            }
        }

        if (isConfirmEmpty || bindingResult.hasErrors() || differentPasswords || strength.getScore() < 3) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);

            return "registration";
        }

        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "Пользователь с таким именем существует");
            return "registration";
        }

            return "login";

    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Пользователь активирован");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Код активации не найден");
        }
        return "login";
    }
}
