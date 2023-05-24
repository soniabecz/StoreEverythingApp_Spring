package com.example.storeeverythingapp_spring.controllers;

import com.example.storeeverythingapp_spring.data.Category;
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
    public String getInfos(Model model) {
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
        model.addAttribute("infos", informationRepository.getInfosFromCategory(category));
        model.addAttribute("categories", informationRepository.getCategories());
        return "infos";
    }

    @GetMapping("/manage/add")
    public String manageAdd(Model model) {
        model.addAttribute("categories", informationRepository.getCategories());
        model.addAttribute("newInfo", new Information());
        return "add_info";
    }

    @PostMapping("/manage/add")
    public String manageAddPost(@Valid @ModelAttribute("newInfo") Information newInfo, BindingResult result, Model model) {
        System.out.println(result.hasErrors());
        if (result.hasErrors()) {
            result.getAllErrors().forEach(el -> System.out.println(el));
            model.addAttribute("categories", informationRepository.getCategories());
            return "add_info";
        }
        informationRepository.addInfo(newInfo);
        return "redirect:/infos/";
    }

    @GetMapping("/manage/add/category")
    public String manageCat(Model model) {
        model.addAttribute("newCategory", new Category());
        return "add_category";
    }

    @PostMapping("/manage/add/category")
    public String manageCatPost(@Valid @ModelAttribute("newCategory") Category newCategory, BindingResult result, Model model) {
        System.out.println(result.hasErrors());
        if (result.hasErrors()) {
            result.getAllErrors().forEach(el -> System.out.println(el));
            return "add_category";
        }
        informationRepository.addCategory(newCategory);
        return "redirect:/infos/";
    }

    @GetMapping("/manage/delete/{id}")
    public String deleteInfo(@PathVariable("id") int id) {
        informationRepository.removeInfo(id);
        return "redirect:/infos/";
    }

    @GetMapping("/manage/edit/{id}")
    public String manageEdit(@PathVariable("id") int id, Model model) {
        model.addAttribute("categories", informationRepository.getCategories());
        model.addAttribute("newInfo", informationRepository.getInfo(id));
        return "edit_info";
    }

    @PostMapping("/manage/edit/{id}")
    public String manageEditPost(@PathVariable("id") int id, @Valid @ModelAttribute("newInfo") Information newInfo, BindingResult result, Model model) {
        System.out.println(result.hasErrors());
        if (result.hasErrors()) {
            result.getAllErrors().forEach(el -> System.out.println(el));
            model.addAttribute("categories", informationRepository.getCategories());
            return "edit_info";
        }
        informationRepository.editInfo(id, newInfo);
        return "redirect:/infos/";
    }

}
