package com.example.cmscart.controllers;

import java.util.List;

import javax.sound.sampled.Line;

import com.example.cmscart.models.Managerrepository;
import com.example.cmscart.models.Pagerepository;
import com.example.cmscart.models.Userrepository;
import com.example.cmscart.models.data.Apply;
import com.example.cmscart.models.data.Manager;
import com.example.cmscart.models.data.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/pages")
public class AdminPagesController {

    @Autowired
    private Pagerepository pageRepo;

    @Autowired
    private Userrepository userrepository;

    @Autowired
    private Managerrepository managerrepository;

    //GetMapping we can apply only on method level and RequestMapping annotation we can apply on class level and as well as on method level
    @GetMapping
    public String index(Model model)
    {
        List<Page> page = pageRepo.findAll();
        model.addAttribute("pages", page);

        return "admin/pages/index";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute Page page){

        return "/admin/pages/add";
    }

    @PostMapping("/add")
    public String add(@Validated Page page,BindingResult bindingResult,RedirectAttributes redirectAttributes,Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "admin/pages/add";
        }

        redirectAttributes.addFlashAttribute("message","Page Added");
        redirectAttributes.addFlashAttribute("alertclass", "alert-sucess");

        String slug = page.getSlug()== " " ?page.getTitle().toLowerCase().replaceAll(" ","-") : page.getTitle().toLowerCase().replaceAll(" ","-");

        Page slugexits = pageRepo.findBySlug(slug);
        if(slugexits!=null)
        {
            redirectAttributes.addFlashAttribute("message","slug-exists,choose another");
            redirectAttributes.addFlashAttribute("alertclass", "alert-danger");

        }
        else{
            page.setSlug(slug);
            page.setSorting(100);

            pageRepo.save(page); 
        }

        return "redirect:/admin/pages/add";
    }

    //The @PathVariable annotation is used to extract the value from the URI. 
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id,Model model){
        Page page = pageRepo.getOne(id);
        model.addAttribute("page", page);

        return "admin/pages/edit";
    }

    @PostMapping("/edit")
    public String edit(@Validated Page page,BindingResult bindingResult,RedirectAttributes redirectAttributes,Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "admin/pages/edit";
        }

        redirectAttributes.addFlashAttribute("message","Page edited");
        redirectAttributes.addFlashAttribute("alertclass", "alert-sucess");

        String slug = page.getSlug()== " " ?page.getTitle().toLowerCase().replaceAll(" ","-") : page.getTitle().toLowerCase().replaceAll(" ","-");

        Page slugexits = pageRepo.findBySlug(page.getId(),slug);
        if(slugexits!=null)
        {
            redirectAttributes.addFlashAttribute("message","slug-exists,choose another");
            redirectAttributes.addFlashAttribute("alertclass", "alert-danger");

        }
        else{
            page.setSlug(slug);

            pageRepo.save(page); 
        }

        return "redirect:/admin/pages/edit/"+page.getId();
    }
    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id,RedirectAttributes redirectAttributes){
        pageRepo.deleteById(id);

        redirectAttributes.addFlashAttribute("message","page deleted");
        redirectAttributes.addFlashAttribute("alertclass", "alert-danger");

        return "redirect:/admin/pages";
    }

    @GetMapping("/approval")
    public String status(Model model)
    {
        List<Manager> manager = managerrepository.findAll();
        model.addAttribute("full",manager);

        return "/admin/pages/approval";
    }

    @GetMapping("/approval/{id}")
    public String status(@PathVariable int id,Model model)
    {
        Manager manager = managerrepository.getOne(id);
        String lt = "access";
        manager.setAccessmanager(lt);
        managerrepository.save(manager);
        return "redirect:/admin/pages/approval";
    }

    @GetMapping("/delete1/{id}")
    public String delete1(@PathVariable int id,RedirectAttributes redirectAttributes){
        managerrepository.deleteById(id);


        return "redirect:/admin/pages/approval";
    }

   /* @PostMapping("/approval")
    public String status(@Validated Apply appli,BindingResult bindingResult)
    {
        userrepository.save(appli);
        return "redirect:/admin/pages/approval";
    }*/



    
}