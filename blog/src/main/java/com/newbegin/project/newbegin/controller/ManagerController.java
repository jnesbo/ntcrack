package com.newbegin.project.newbegin.controller;

import com.newbegin.project.newbegin.service.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/statistics")
public class ManagerController {

    @Autowired
    private ManagementService managementService;


    @GetMapping
    public String posts(Model model) {
        model.addAttribute("users", managementService.countUsers());
        model.addAttribute("allPosts", managementService.countPostsByUsers());
        model.addAttribute("usersWithPosts", managementService.countUsersWithPosts());
        model.addAttribute("usersWithoutPosts", managementService.countUsersWithoutPosts());
        model.addAttribute("tagsStatistics", managementService.getTagsStatistics());
        Map<String, Integer> usersStatistics = managementService.getUsersStatistics();

        model.addAttribute("usersStatistics", usersStatistics);


        return "statistics";
    }

    @GetMapping(value = "/postStatistics")
    public String getPosts(Model model) {
        Map<String, Integer> tagsStatistics = managementService.getTagsStatistics();
        Map<String, Integer> postsStatistics = managementService.getPostsStatistics();

        model.addAttribute("tagsStatistics", tagsStatistics);
        model.addAttribute("postsStatistics", postsStatistics);

        return "postStatistics";
    }



}
