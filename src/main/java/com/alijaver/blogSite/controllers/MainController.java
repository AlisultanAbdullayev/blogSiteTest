package com.alijaver.blogSite.controllers;

import com.alijaver.blogSite.models.Post;
import com.alijaver.blogSite.repos.PostRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    final PostRepo postRepo;
    public MainController(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    @GetMapping("/")
    public String home(Model model) {
        Iterable<Post> posts = postRepo.findAll();
        model.addAttribute("posts",posts);
        model.addAttribute("title", "Main paige");

        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About us");

        return "about";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("title", "Contacts page");

        return "contact";
    }
}
