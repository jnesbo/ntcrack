package com.example.demo.controller;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repos.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private PostRepo postRepo;

    @GetMapping("/")
    public String hello(Model model) {
        Iterable<Post> posts = postRepo.findAll();
        model.addAttribute("posts", posts);
        return "hello";
    }

    @GetMapping("filter")
    public String main(Model model, @RequestParam(required = false, defaultValue = "") String filter) {
        Iterable<Post> posts;

        if (filter != null && !filter.isEmpty()) {
            posts = postRepo.findByTag(filter);
        } else {
            posts = postRepo.findAll();
        }
        model.addAttribute("posts", posts);
        model.addAttribute("filter", filter);
        return "hello";
    }

    @PostMapping("/hello")
    public String add(
            @AuthenticationPrincipal User user,
            @Valid Post post,
            BindingResult bindingResult,
            Model model) {

        post.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("post", post);
        } else {
            model.addAttribute("post", null);
            postRepo.save(post);
        }
        Iterable<Post> posts = postRepo.findAll();
        model.addAttribute("posts", posts);
        return "hello";
    }

    @PostMapping("resPas")
    public String resetPassword(Model model,
                                @RequestParam String username,
                                @RequestParam String password,
                                @RequestParam String newPassword) {
        model.addAttribute("pass", "work");
        return "redirect:/login";
    }

    @GetMapping("/resetPassword")
    public String resetPass() {
        return "resetPassword";
    }



}
