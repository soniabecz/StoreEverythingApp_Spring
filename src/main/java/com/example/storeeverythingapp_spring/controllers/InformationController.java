package com.example.storeeverythingapp_spring.controllers;

import com.example.storeeverythingapp_spring.data.Information;
import com.example.storeeverythingapp_spring.repositories.InformationRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

@Controller
@RequestMapping("/infos")
@SessionScope
public class InformationController {
    @Autowired
    InformationRepository informationRepository = new InformationRepository();

    @GetMapping("/")
    public String getItems(Model model) {
        model.addAttribute("infos", informationRepository.getInfos());
        model.addAttribute("categories", informationRepository.getCategories());
        return "infos";
    }

    @GetMapping("/{idx}")
    public String getInfo(@PathVariable("idx") int id, Model model) {
        model.addAttribute("info", informationRepository.getInfo(id));
        return "info";
    }

    @GetMapping("/category")
    public String getItemsFromCategory(@RequestParam("category") String category, Model model) {
        model.addAttribute("items", informationRepository.getInfosFromCategory(category));
        model.addAttribute("categories", informationRepository.getCategories());
        return "infos";
    }

    @GetMapping("/manage/add")
    public String manage(Model model) {
        model.addAttribute("categories", informationRepository.getCategories());
        model.addAttribute("newInfo", new Information());
        return "add_info";
    }

    @PostMapping("/manage/add")
    public String managePost(@Valid @ModelAttribute("newItem") Information newInfo, BindingResult result, Model model) {
        System.out.println(result.hasErrors());
        if (result.hasErrors()) {
            result.getAllErrors().forEach(el -> System.out.println(el));
            model.addAttribute("categories", informationRepository.getCategories());
            return "add_info";
        }
        informationRepository.addInfo(new Information());
        return "redirect:/items/";
    }
}
