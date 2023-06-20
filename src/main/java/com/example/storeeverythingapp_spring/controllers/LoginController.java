package com.example.storeeverythingapp_spring.controllers;

import com.example.storeeverythingapp_spring.data.db.UserEntity;
import com.example.storeeverythingapp_spring.services.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Scope("session")
public class LoginController {

    @Autowired
    AppService appService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /*@GetMapping("/logout")
    public String logout() {
        return "login";
    }*/

    @GetMapping("/register")
    public String manageAdd(Model model) {
        model.addAttribute("newUser", new UserEntity());
        return "register";
    }

    @PostMapping("/register")
    public String manageAddPost(@ModelAttribute("newUser") UserEntity user, BindingResult result, Model model) {

        if (result.hasErrors()) {
            result.getAllErrors().forEach(el -> System.out.println(el));
            return "register";
        }

        appService.addUser(user);

        return "redirect:/login";
    }
}
