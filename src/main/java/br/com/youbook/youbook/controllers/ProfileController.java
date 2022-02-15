package br.com.youbook.youbook.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {
    
    @GetMapping("/profile")
    public String profilePage() {
        return "profile";
    }
    
    @GetMapping("/register") 
    public String registerPage() {
        return "register";
    }
    
    @PostMapping("/register")
    public String addUser() {
        return "redirect:/login";
    }
}
