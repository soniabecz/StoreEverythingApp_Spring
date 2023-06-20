package com.example.storeeverythingapp_spring.controllers;

import com.example.storeeverythingapp_spring.data.Information;
import com.example.storeeverythingapp_spring.data.Role;
import com.example.storeeverythingapp_spring.data.db.UserEntity;
import com.example.storeeverythingapp_spring.services.AppService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
@Scope("session")
public class LoginController {

    @Autowired
    AppService appService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/allUsers")
    public String allUsers(Model model) {
        model.addAttribute("users", appService.getAllUsers());
        model.addAttribute("roles", Arrays.asList(Role.values()));
        return "users";
    }

    @GetMapping("/register")
    public String manageAdd(Model model) {
        model.addAttribute("newUser", new UserEntity());
        return "register";
    }

    @PostMapping("/register")
    public String manageAddPost(@Valid @ModelAttribute("newUser") UserEntity user, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "register";
        }

        appService.addUser(user);

        return "redirect:/login";
    }

    @GetMapping("/changeRole/{username}")
    public String filterItems(@PathVariable("username") String username, @RequestParam("role") String role, Model model) {
        appService.changeRole(username,role);
        model.addAttribute("users", appService.getAllUsers());
        model.addAttribute("roles", Arrays.asList(Role.values()));
        return "users";
    }

    @GetMapping("/deleteUser/{username}")
    public String deleteInfo(@PathVariable("username") String username) {

        appService.deleteUser(username);

        return "redirect:/allUsers";
    }

    @GetMapping("/editUser/{username}")
    public String manageEdit(@PathVariable("username") String username, Model model) {
        model.addAttribute("newUser", appService.getUser(username));
        return "edit_user";
    }

    @PostMapping("/editUser/{username}")
    public String manageEditPost(@PathVariable("username") String username, @Valid @ModelAttribute("newUser") UserEntity newUser, BindingResult result, Model model) {
        System.out.println(result.hasErrors());
        if (result.hasErrors()) {
            result.getAllErrors().forEach(System.out::println);
            return "edit_user";
        }
        appService.editUser(newUser,username);
        return "redirect:/allUsers";
    }
}
