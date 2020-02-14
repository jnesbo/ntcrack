package com.newbegin.project.newbegin.controller;

import com.newbegin.project.newbegin.model.Post;
import com.newbegin.project.newbegin.model.User;
import com.newbegin.project.newbegin.repository.PostRepository;
import com.newbegin.project.newbegin.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Value("${upload.path}")
    private String path;

    @GetMapping
    public String showPost(@AuthenticationPrincipal User user,Model model) {
        List<Post> posts = postService.postList();
        List<String> tags = postService.toptags();

        model.addAttribute("posts", posts);
        model.addAttribute("tags", tags);

        return "posts";
    }


    @PostMapping("/newpost")
    public String addPost(@AuthenticationPrincipal User user,
                          @Valid Post post,
                          BindingResult result, Model model,
                          @RequestParam("file") MultipartFile file,
                          @RequestParam("typeTags") String typeTags) throws IOException {

        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            File uploadDir = new File(path);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(path + File.separator + resultName));
            post.setFilename(resultName);
        }
        if (result.hasErrors()) {
            Map<String, String> errorMap = ControllerUtil.getErrors(result);

            model.mergeAttributes(errorMap);

        } else {
            postService.addNewPost(post, typeTags, user, model);
        }


        model.addAttribute("posts", postService.postList());
        model.addAttribute("tags", postService.toptags());

        return "posts";
    }


    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable long id, Model model) {
        postService.delete(id);

        model.addAttribute("tags", postService.toptags());
        model.addAttribute("posts", postService.postList());

        return "posts";
    }


    @GetMapping("/search")
    public String searchPost(@RequestParam(required = false, defaultValue = "") String text, Model model) {
        List<Post> posts = postService.postList();
        if (text != null && !text.isEmpty()) {
            posts = postService.findPostByText(text);
        }
        model.addAttribute("posts", posts);
        model.addAttribute("tags", postService.toptags());
        return "posts";
    }

    @GetMapping("/search/{tag}")
    public String searchByTag(@PathVariable("tag") String tag, Model model) {
        List<Post> posts = postService.findPostsByTag(tag);

        model.addAttribute("posts", posts);
        model.addAttribute("tags", postService.toptags());
        return "posts";
    }

    @GetMapping("/user-posts/{user}")
    public String userPosts(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model) {

        List<Post> posts = postRepository.findPostByAuthor(user);


        model.addAttribute("userChannel", user);
        model.addAttribute("subscriptionsCount", user.getFollowing().size());
        model.addAttribute("subscribersCount", user.getFollowers().size());
        model.addAttribute("isSubscriber", user.getFollowers().contains(currentUser));
        model.addAttribute("posts", posts);
        model.addAttribute("isCurrentUser", currentUser.equals(user));

        return "userPosts";
    }

    @GetMapping("/postsFeed/{user}")
    public String postsFeed(@AuthenticationPrincipal @PathVariable User user,
                            Model model) {
        List<Post> posts = postService.getPostFeed(user);

        model.addAttribute("posts", posts);
        model.addAttribute("tags", postService.toptags());
        return "postsFeed";
    }


}
