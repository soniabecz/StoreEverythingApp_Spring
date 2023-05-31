package com.example.storeeverythingapp_spring.controllers;

import com.example.storeeverythingapp_spring.data.Category;
import com.example.storeeverythingapp_spring.data.Information;
import com.example.storeeverythingapp_spring.repositories.InformationRepository;
import com.example.storeeverythingapp_spring.validators.category.CategoryValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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

    @GetMapping("/all")
    public String getAllInfos(Model model) {
        model.addAttribute("infos", informationRepository.getAllInfos());
        model.addAttribute("categories", informationRepository.getCategories());
        return "infos";
    }

    @GetMapping("/{idx}")
    public String getInfo(@PathVariable("idx") int id, Model model) {
        model.addAttribute("info", informationRepository.getInfo(id));
        return "info";
    }

    @GetMapping("/filter")
    public String filterItems(@RequestParam("choice") String choice, Model model) {
        model.addAttribute("infos", informationRepository.filterInfos(choice));
        model.addAttribute("categories", informationRepository.getCategories());
        return "infos";
    }

    @GetMapping("/sort/title/asc")
    public String sortItemsByTitleASC(Model model) {
        model.addAttribute("infos", informationRepository.sortByNameASC());
        model.addAttribute("categories", informationRepository.getCategories());
        return "redirect:/infos/";
    }

    @GetMapping("/sort/title/dsc")
    public String sortItemsByTitleDSC(Model model) {
        model.addAttribute("infos", informationRepository.sortByNameDSC());
        model.addAttribute("categories", informationRepository.getCategories());
        return "redirect:/infos/";
    }

    @GetMapping("/sort/date/asc")
    public String sortItemsByDateASC(Model model) {
        model.addAttribute("infos", informationRepository.sortByDateASC());
        model.addAttribute("categories", informationRepository.getCategories());
        return "redirect:/infos/";
    }

    @GetMapping("/sort/date/dsc")
    public String sortItemsByDateDSC(Model model) {
        model.addAttribute("infos", informationRepository.sortByDateDSC());
        model.addAttribute("categories", informationRepository.getCategories());
        return "redirect:/infos/";
    }

    @GetMapping("/sort/category/asc")
    public String sortItemsByCategoryASC(Model model) {
        model.addAttribute("infos", informationRepository.sortByCategoryASC());
        model.addAttribute("categories", informationRepository.getCategories());
        return "redirect:/infos/";
    }

    @GetMapping("/sort/category/dsc")
    public String sortItemsByCategoryDSC(Model model) {
        model.addAttribute("infos", informationRepository.sortByCategoryDSC());
        model.addAttribute("categories", informationRepository.getCategories());
        return "redirect:/infos/";
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

    public String manageCatPost(@Validated(CategoryValidation.class) @ModelAttribute("newCategory") Category newCategory, BindingResult result, Model model) {
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
