package com.example.storeeverythingapp_spring.controllers;

import com.example.storeeverythingapp_spring.data.db.InformationEntity;
import com.example.storeeverythingapp_spring.services.AppService;
import com.example.storeeverythingapp_spring.data.Category;
import com.example.storeeverythingapp_spring.data.Information;
import com.example.storeeverythingapp_spring.data.db.CategoryEntity;
import com.example.storeeverythingapp_spring.services.RestClientService;
import com.example.storeeverythingapp_spring.validators.category.CategoryValidation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Optional;

@Controller
@RequestMapping("/infos")
@Scope("session")
public class InformationController {

    @Autowired
    AppService informationRepository;

    @Autowired
    RestClientService clientService;

    @GetMapping("/")
    public String getInfos(@CookieValue(value = "sortType", defaultValue = "none") String sortType, Model model) {

        System.out.println("sort:" + sortType);

        model.addAttribute("categories", informationRepository.getCategories());

        if(sortType.equals("nameASC")) {
            model.addAttribute("infos", informationRepository.getAllInfos());
            System.out.println("nameasc");
        }
        if(sortType.equals("nameDSC")) {
            model.addAttribute("infos", informationRepository.getAllInfos());
            System.out.println("namedsc");
        }
        if(sortType.equals("dateASC")) {
            model.addAttribute("infos", informationRepository.getAllInfos());
        }
        if(sortType.equals("dateDSC")) {
            model.addAttribute("infos", informationRepository.getAllInfos());
        }
        if(sortType.equals("categoryASC")) {
            model.addAttribute("infos", informationRepository.getAllInfos());
        }
        if(sortType.equals("categoryDSC")) {
            model.addAttribute("infos", informationRepository.getAllInfos());
        }
        else {
            model.addAttribute("infos", informationRepository.getAllInfos());
        }

        return "infos";
    }

    @GetMapping("/all")
    public String getAllInfos(@CookieValue(value = "sortType", defaultValue = "none") String sortType, Model model) {

        model.addAttribute("infos", informationRepository.getAllInfos());

        model.addAttribute("categories", informationRepository.getCategories());
        return "redirect:/infos/";
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
    public String sortItemsByTitleASC(HttpServletResponse response, Model model) {

        Cookie cookie = new Cookie("sortType", "nameASC");
        cookie.setPath("/");
        response.addCookie(cookie);

        model.addAttribute("infos", informationRepository.sortByNameASC());
        model.addAttribute("categories", informationRepository.getCategoryEntityRepository().findAll());
        return "redirect:/infos/";
    }

    @GetMapping("/sort/title/dsc")
    public String sortItemsByTitleDSC(HttpServletResponse response, Model model) {

        Cookie cookie = new Cookie("sortType", "nameDSC");
        cookie.setPath("/");
        response.addCookie(cookie);

        model.addAttribute("infos", informationRepository.sortByNameDSC());
        model.addAttribute("categories", informationRepository.getCategoryEntityRepository().findAll());
        return "redirect:/infos/";
    }

    @GetMapping("/sort/date/asc")
    public String sortItemsByDateASC(HttpServletResponse response, Model model) {

        Cookie cookie = new Cookie("sortType", "dateASC");
        cookie.setPath("/");
        response.addCookie(cookie);

        model.addAttribute("infos", informationRepository.sortByDateASC());
        model.addAttribute("categories", informationRepository.getCategoryEntityRepository().findAll());
        return "redirect:/infos/";
    }

    @GetMapping("/sort/date/dsc")
    public String sortItemsByDateDSC(HttpServletResponse response, Model model) {

        Cookie cookie = new Cookie("sortType", "dateDSC");
        cookie.setPath("/");
        response.addCookie(cookie);

        model.addAttribute("infos", informationRepository.sortByDateDSC());
        model.addAttribute("categories", informationRepository.getCategoryEntityRepository().findAll());

        return "redirect:/infos/";
    }

    @GetMapping("/sort/category/asc")
    public String sortItemsByCategoryASC(HttpServletResponse response, Model model) {

        Cookie cookie = new Cookie("sortType", "categoryASC");
        cookie.setPath("/");
        response.addCookie(cookie);

        model.addAttribute("infos", informationRepository.sortByCategoryASC());
        model.addAttribute("categories", informationRepository.getCategoryEntityRepository().findAll());
        return "redirect:/infos/";
    }

    @GetMapping("/sort/category/dsc")
    public String sortItemsByCategoryDSC(HttpServletResponse response, Model model) {

        Cookie cookie = new Cookie("sortType", "categoryDSC");
        cookie.setPath("/");
        response.addCookie(cookie);

        model.addAttribute("infos", informationRepository.sortByCategoryDSC());
        model.addAttribute("categories", informationRepository.getCategoryEntityRepository().findAll());
        return "redirect:/infos/";
    }

    @GetMapping("/manage/add")
    public String manageAdd(Model model) {
        model.addAttribute("categories", informationRepository.getCategoryEntityRepository().findAll());
        model.addAttribute("newInfo", new Information());
        return "add_info";
    }

    @PostMapping("/manage/add")
    public String manageAddPost(@Valid @ModelAttribute("newInfo") Information newInfo, BindingResult result, Model model, HttpServletRequest request) {
        System.out.println(result.hasErrors());
        if (result.hasErrors()) {
            result.getAllErrors().forEach(el -> System.out.println(el));
            model.addAttribute("categories", informationRepository.getCategoryEntityRepository().findAll());
            return "add_info";
        }

        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        InformationEntity newInformationEntity = new InformationEntity();
        String catName = newInfo.getCategory().getName();
        Optional<CategoryEntity> categoryEntity = informationRepository.getCategoryEntityRepository().findByNameIgnoreCase(catName);
        newInformationEntity.setTitle(newInfo.getTitle());
        newInformationEntity.setContent(newInfo.getContent());
        newInformationEntity.setDate(new Date(newInfo.getDate().getTime()));
        newInformationEntity.setLink(newInfo.getLink());
        newInformationEntity.setUsername(currentUserName);
        newInformationEntity.setCategoryName(categoryEntity.get());*/

        request.getSession().setAttribute("newInfos",informationRepository.getNewInfos());

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
        if (!clientService.checkIfWordIsInDictionary(newCategory.getName())) {
            System.out.println("nie ma w słowniku");
            return "add_category";
        }
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
        model.addAttribute("categories", informationRepository.getCategoryEntityRepository().findAll());
        model.addAttribute("newInfo", informationRepository.getInfo(id));
        return "edit_info";
    }

    @PostMapping("/manage/edit/{id}")
    public String manageEditPost(@PathVariable("id") Integer id, @Valid @ModelAttribute("newInfo") Information newInfo, BindingResult result, Model model) {
        System.out.println(result.hasErrors());
        if (result.hasErrors()) {
            result.getAllErrors().forEach(el -> System.out.println(el));
            model.addAttribute("categories", informationRepository.getCategoryEntityRepository().findAll());
            return "edit_info";
        }
        informationRepository.editInfo(id, newInfo);
        return "redirect:/infos/";
    }

}
