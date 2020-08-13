package com.alijaver.blogSite.controllers;

import com.alijaver.blogSite.models.Post;
import com.alijaver.blogSite.repos.PostRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {

    final PostRepo postRepo;
    public BlogController(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> posts = postRepo.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("title", "Blog");

        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        model.addAttribute("title", "Adding blog");

        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogAddPost(@RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String author,
                              @RequestParam String period,
                              @RequestParam String full_text
    ) {
        Post post = new Post(title, anons, author, period, full_text);
        postRepo.save(post);

        return "redirect:/blog";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Model model) {
        Iterable<Post> posts;
        if (filter != null && !filter.isEmpty()) {
             posts = postRepo.findByAuthor(filter);
        } else {
            posts = postRepo.findAll();
        }
        model.addAttribute("posts", posts);

        return "blog-main";
    }

    @GetMapping("/blog/{id}")
    public String blogMain(@PathVariable(value = "id") Integer id, Model model) {
        if (!postRepo.existsById(id)) {

            return "redirect:/blog";
        }
        Optional<Post> post = postRepo.findById(id);
        ArrayList<Post> result = new ArrayList<>();
        post.ifPresent(result::add);
        model.addAttribute("post", result);

        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") Integer id, Model model) {
        if (!postRepo.existsById(id)) {

            return "redirect:/blog";
        }
        Optional<Post> post = postRepo.findById(id);
        ArrayList<Post> result = new ArrayList<>();
        post.ifPresent(result::add);
        model.addAttribute("post", result);

        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") Integer id,
                                 @RequestParam String title,
                                 @RequestParam String anons,
                                 @RequestParam String author,
                                 @RequestParam String period,
                                 @RequestParam String full_text
    ) {
        Post post = postRepo.findById(id).orElseThrow(RuntimeException::new);
        post.setTitle(title);
        post.setAnons(anons);
        post.setAnons(author);
        post.setPeriod(period);
        post.setFull_text(full_text);
        postRepo.save(post);

        return "redirect:/blog{id}";
    }

    @PostMapping("blog/{id}/remove")
    public String blogPostRemove(@PathVariable(value = "id") Integer id) {
        Post post = postRepo.findById(id).orElseThrow(RuntimeException::new);
        postRepo.delete(post);

        return "redirect:/blog/{id}";
    }
}
