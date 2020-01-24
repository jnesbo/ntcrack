package com.example.demo.controller;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repos.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
public class PostController {

    @Autowired
    private PostRepo postRepo;

    @GetMapping("/user-posts/{user}")
    public String userPosts(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model,
            @RequestParam(required = false) Post post
    ) {
        Set<Post> posts = user.getPosts();

        model.addAttribute("userChannel", user);
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        model.addAttribute("posts", posts);
        model.addAttribute("post", post);
        model.addAttribute("isCurrentUser", currentUser.equals(user));

        return "userPosts";
    }

/*    @PostMapping("/user-posts/{user}")
    public String updatePost(@AuthenticationPrincipal User currentUser,
                             @PathVariable Long user,
                             @RequestParam("id") Post post,
                             @RequestParam("text") String text,
                             @RequestParam("tag") String tag) {

        if (post.getAuthor().equals(currentUser)) {
            if (!StringUtils.isEmpty(text)) {
                post.setText(text);
            }

            if (!StringUtils.isEmpty(tag)) {
                post.setTag(tag);
            }

            postRepo.save(post);
        }
        return "redirect://user-posts/" + user;
    }*/
}
