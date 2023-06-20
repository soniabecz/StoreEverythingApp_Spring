package com.example.storeeverythingapp_spring.controllers;

import com.example.storeeverythingapp_spring.services.AppService;
import com.example.storeeverythingapp_spring.data.Category;
import com.example.storeeverythingapp_spring.data.Information;
import com.example.storeeverythingapp_spring.services.RestClientService;
import com.example.storeeverythingapp_spring.validators.category.CategoryValidation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/infos")
@Scope("session")
public class InformationController {

    @Autowired
    AppService service;

    @Autowired
    RestClientService clientService;

    @GetMapping("/")
    public String getInfos(@CookieValue(value = "sortType", defaultValue = "none") String sortType, Model model) {

        model.addAttribute("categories", service.getCategories());

        if(sortType.equals("nameASC")) {
            model.addAttribute("infos", service.sortByNameASC());
            return "infos";
        }
        if(sortType.equals("nameDSC")) {
            model.addAttribute("infos", service.sortByNameDSC());
            return "infos";
        }
        if(sortType.equals("dateASC")) {
            model.addAttribute("infos", service.sortByDateASC());
            return "infos";
        }
        if(sortType.equals("dateDSC")) {
            model.addAttribute("infos", service.sortByDateDSC());
            return "infos";
        }
        if(sortType.equals("categoryASC")) {
            model.addAttribute("infos", service.sortByCategoryASC());
            return "infos";
        }
        if(sortType.equals("categoryDSC")) {
            model.addAttribute("infos", service.sortByCategoryDSC());
            return "infos";
        }
        else {
            model.addAttribute("infos", service.getAllInfos());
            return "infos";
        }
    }

    @GetMapping("/all")
    public String getAllInfos(@CookieValue(value = "sortType", defaultValue = "none") String sortType, Model model) {

        model.addAttribute("infos", service.getAllInfos());

        model.addAttribute("categories", service.getCategories());
        return "redirect:/infos/";
    }

    @GetMapping("/shared")
    public String getSharedInfos(@CookieValue(value = "sortType", defaultValue = "none") String sortType, Model model) {

        model.addAttribute("infos", service.getSharedInfos());

        model.addAttribute("categories", service.getCategories());
        return "shared_infos";
    }

    @GetMapping("/{idx}")
    public String getInfo(@PathVariable("idx") int id, Model model) {
        model.addAttribute("info", service.getInfo(id));
        return "info";
    }

    @GetMapping("/filter")
    public String filterItems(@RequestParam("choice") String choice, Model model) {
        model.addAttribute("infos", service.filterInfos(choice));
        model.addAttribute("categories", service.getCategories());
        return "infos";
    }

    @GetMapping("/sort/title/asc")
    public String sortItemsByTitleASC(HttpServletResponse response, Model model) {

        Cookie cookie = new Cookie("sortType", "nameASC");
        cookie.setPath("/");
        response.addCookie(cookie);

        model.addAttribute("infos", service.sortByNameASC());
        model.addAttribute("categories", service.getCategories());
        return "redirect:/infos/";
    }

    @GetMapping("/sort/title/dsc")
    public String sortItemsByTitleDSC(HttpServletResponse response, Model model) {

        Cookie cookie = new Cookie("sortType", "nameDSC");
        cookie.setPath("/");
        response.addCookie(cookie);

        model.addAttribute("infos", service.sortByNameDSC());
        model.addAttribute("categories", service.getCategories());
        return "redirect:/infos/";
    }

    @GetMapping("/sort/date/asc")
    public String sortItemsByDateASC(HttpServletResponse response, Model model) {

        Cookie cookie = new Cookie("sortType", "dateASC");
        cookie.setPath("/");
        response.addCookie(cookie);

        model.addAttribute("infos", service.sortByDateASC());
        model.addAttribute("categories", service.getCategories());
        return "redirect:/infos/";
    }

    @GetMapping("/sort/date/dsc")
    public String sortItemsByDateDSC(HttpServletResponse response, Model model) {

        Cookie cookie = new Cookie("sortType", "dateDSC");
        cookie.setPath("/");
        response.addCookie(cookie);

        model.addAttribute("infos", service.sortByDateDSC());
        model.addAttribute("categories", service.getCategories());

        return "redirect:/infos/";
    }

    @GetMapping("/sort/category/asc")
    public String sortItemsByCategoryASC(HttpServletResponse response, Model model) {

        Cookie cookie = new Cookie("sortType", "categoryASC");
        cookie.setPath("/");
        response.addCookie(cookie);

        model.addAttribute("infos", service.sortByCategoryASC());
        model.addAttribute("categories", service.getCategories());
        return "redirect:/infos/";
    }

    @GetMapping("/sort/category/dsc")
    public String sortItemsByCategoryDSC(HttpServletResponse response, Model model) {

        Cookie cookie = new Cookie("sortType", "categoryDSC");
        cookie.setPath("/");
        response.addCookie(cookie);

        model.addAttribute("infos", service.sortByCategoryDSC());
        model.addAttribute("categories", service.getCategories());
        return "redirect:/infos/";
    }

    @GetMapping("/manage/add")
    public String manageAdd(Model model) {
        model.addAttribute("categories", service.getCategories());
        model.addAttribute("newInfo", new Information());
        return "add_info";
    }

    @PostMapping("/manage/add")
    public String manageAddPost(@Valid @ModelAttribute("newInfo") Information newInfo, BindingResult result, Model model, HttpServletRequest request) {
        System.out.println(result.hasErrors());
        if (result.hasErrors()) {
            result.getAllErrors().forEach(el -> System.out.println(el));
            model.addAttribute("categories", service.getCategories());
            return "add_info";
        }

        request.getSession().setAttribute("newInfos", service.getNewInfos());

        service.addInfo(newInfo);

        return "redirect:/infos/";
    }

    @GetMapping("/manage/add/category")
    public String manageCat(Model model) {
        model.addAttribute("newCategory", new Category());
        return "add_category";
    }

    @PostMapping("/manage/add/category")

    public String manageCatPost(@Validated(CategoryValidation.class) @ModelAttribute("newCategory") Category newCategory, BindingResult result, Model model, HttpServletRequest request) {
        System.out.println(result.hasErrors());
        if (!clientService.checkIfWordIsInDictionary(newCategory.getName())) {
            System.out.println("nie ma w słowniku");
            return "add_category";
        }
        if (result.hasErrors()) {
            result.getAllErrors().forEach(System.out::println);
            return "add_category";
        }
        request.getSession().setAttribute("newCategories", service.getNewCategories());
        service.addCategory(newCategory);
        return "redirect:/infos/";
    }

    @GetMapping("/manage/delete/{id}")
    public String deleteInfo(@PathVariable("id") int id) {
        Information info = service.getInfo(id);

        service.removeInfo(id, info);

        return "redirect:/infos/";
    }

    @GetMapping("/manage/edit/{id}")
    public String manageEdit(@PathVariable("id") int id, Model model) {
        model.addAttribute("categories", service.getCategories());
        model.addAttribute("newInfo", service.getInfo(id));
        return "edit_info";
    }

    @PostMapping("/manage/edit/{id}")
    public String manageEditPost(@PathVariable("id") Integer id, @Valid @ModelAttribute("newInfo") Information newInfo, BindingResult result, Model model) {
        System.out.println(result.hasErrors());
        if (result.hasErrors()) {
            result.getAllErrors().forEach(System.out::println);
            model.addAttribute("categories", service.getCategories());
            return "edit_info";
        }
        service.editInfo(id, newInfo);
        return "redirect:/infos/";
    }

    @GetMapping("/manage/share/{id}")
    public String manageShare(@PathVariable("id") int id, Model model) {
        model.addAttribute("categories", service.getCategories());
        model.addAttribute("newInfo", service.getInfo(id));
        return "share_info";
    }

    @PostMapping("/manage/share/{id}")
    public String manageShare(@PathVariable("id") Integer id, @RequestParam("username") String username, @Valid @ModelAttribute("newInfo") Information newInfo, BindingResult result, Model model) {
        System.out.println(result.hasErrors());
        if (result.hasErrors()) {
            result.getAllErrors().forEach(el -> System.out.println(el));
            model.addAttribute("categories", service.getCategories());
            return "share_info";
        }
        service.shareInfo(id, newInfo,username);
        return "redirect:/infos/";
    }

}
