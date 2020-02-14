package com.newbegin.project.newbegin.controller;

import com.newbegin.project.newbegin.model.Role;
import com.newbegin.project.newbegin.model.User;
import com.newbegin.project.newbegin.service.MailService;
import com.newbegin.project.newbegin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/user")
public class UserController {
    @Value("${server.port}")
    private int port;

    @Value("${upload.path}")
    private String path;

    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;

    @GetMapping
    public String userList(Model model) {
        Iterable<User> users = userService.findAll();

        model.addAttribute("users", users);
        return "userList";
    }

    @GetMapping("/search")
    public String searchPost(@RequestParam(required = false, defaultValue = "") String username, Model model) {
        List<User> users = userService.findAll();
        if (username != null && !username.isEmpty()) {
            users = userService.findUser(username);
        }
        model.addAttribute("users", users);

        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable long id, Model model) {
        userService.delete(id);
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "userList";
    }

     @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("roles", Role.values());
        model.addAttribute("user", user);
        return "userEdit";
    }

     @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(@RequestParam String username,
                           @RequestParam Map<String, String> form,
                           @RequestParam("userId") User user) {
        userService.saveUserByAdmin(user, username, form);
        return "redirect:/user";
    }


    @GetMapping("subscribe/{user}")
    public String subscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user) {
        userService.follow(currentUser, user);
        return "redirect:/posts/user-posts/" + user.getId();
    }

    @GetMapping("unsubscribe/{user}")
    public String unsubscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user) {
        userService.unfollow(currentUser, user);
        return "redirect:/posts/user-posts/" + user.getId();
    }

    @GetMapping("{type}/{user}/list")
    public String userList(
            @AuthenticationPrincipal User currentUser,
            Model model,
            @PathVariable User user,
            @PathVariable String type) {
        model.addAttribute("userChannel", user);
        model.addAttribute("type", type);
        model.addAttribute("currentUser", currentUser);

        if ("following".equals(type)) {
            model.addAttribute("users", user.getFollowing());
        } else {
            model.addAttribute("users", user.getFollowers());
        }
        return "follow";
    }


    @PostMapping("profile/updateSecurity")
    public String updateProfileSecurity(@AuthenticationPrincipal User user,
                                        @RequestParam String password,
                                        @RequestParam String email,
                                        Model model) throws MessagingException {


        String regex = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{6,}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        boolean emptyPass = StringUtils.isEmpty(password);

        if (emptyPass) {
            model.addAttribute("passwordError", "Сначала измените пароль");
            return "update";
        }

        if (!matcher.matches()) {
            model.addAttribute("passwordError", "Пароль должен быть от 6 символов" +
                    ", содержать верхний и нижний регистр, цифры и символы");
            return "update";
        } else if (matcher.matches()) {

            userService.updateProfileSecurity(user, password, email);
            if (!StringUtils.isEmpty(user.getEmail())) {

                String message = String.format(
                        "Приветсвую, %s! \n" +
                               "Теперь ваш аккаунт на сервисе InNutshell привязан к этой почте.",
                        user.getUsername()
                );
                mailService.send(user.getEmail(), "Смена почты", message);
                model.addAttribute("message", "Профиль изменен");
            }}
        return "update";
    }
    @PostMapping("update/{user}")
    public String updateProfile(@AuthenticationPrincipal User currentUser,
                                @PathVariable User user,
                                @RequestParam String description) {

        if (currentUser.getId().equals(user.getId())) {
            userService.updateProfile(user, description);
        }
        return "redirect:/posts/user-posts/" + user.getId();
    }

    @GetMapping("/update")
    public String upd(){
        return "update";
    }

}
