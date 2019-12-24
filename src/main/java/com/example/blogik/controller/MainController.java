package com.example.blogik.controller;

import com.example.blogik.model.Message;
import com.example.blogik.model.User;
import com.example.blogik.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
// @RequiredArgsConstructor doesnt work
public class MainController {
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/")
    public String main(){
        return "main";
    }

    @GetMapping("/main1")
    public String main1(@RequestParam(required = false, defaultValue = "") String filter, Model model){
        Iterable<Message> messages = messageRepository.findAll();
        if(filter !=null && !filter.isEmpty()){
            messages = messageRepository.findByTag(filter);

        }else {
            messages = messageRepository.findAll();
        }
        model.addAttribute("messages",messages);
        model.addAttribute("filter ", filter);
        return "main1";
    }
    @PostMapping("/main1")
    public String add(@AuthenticationPrincipal User user,
            @RequestParam String text,
                      @RequestParam String tag,
                      Map<String, Object> model){
        Message message = new Message(text, tag, user);

        messageRepository.save(message);
        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages",messages);
        return"main1";
    }


}
