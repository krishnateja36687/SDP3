package com.example.cmscart.controllers;

import java.io.IOException;
import java.net.http.WebSocket.Listener;
import java.util.List;
import org.springframework.util.*;
import javax.mail.MessagingException;
import javax.persistence.criteria.ListJoin;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.example.cmscart.models.Emailservice;
import com.example.cmscart.models.Loginrepository;
import com.example.cmscart.models.Managerrepository;
import com.example.cmscart.models.Pagerepository;
import com.example.cmscart.models.Registerrepostiory;
import com.example.cmscart.models.Userrepository;
import com.example.cmscart.models.data.Apply;
import com.example.cmscart.models.data.Login;
import com.example.cmscart.models.data.Manager;
import com.example.cmscart.models.data.Page;
import com.example.cmscart.models.data.Register;
import com.example.cmscart.models.data.Type;
import com.example.cmscart.models.data.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    @Autowired
	private Emailservice emailService;
    
    @Autowired
    private Pagerepository pagerepository;

    @Autowired
    private Userrepository userrepository;

    @Autowired
    private Loginrepository loginrepository;

    @Autowired
    private Registerrepostiory registerrepostiory;

    @Autowired
    private Managerrepository managerrepository;
    @GetMapping("/home")
    public String home(Model model,Register regi)
    {
        
        Page page = pagerepository.findBySlug("home");
        model.addAttribute("pages", page);


        List<Apply> appli = userrepository.findAll();
        model.addAttribute("full",appli);

        
        
        
        return "home";
    }
    
    @ModelAttribute
    public void sharedData(Model model)
    {
        List<Page> pages = pagerepository.findAll();
        model.addAttribute("cpages", pages);
    }

    @GetMapping("/apply")
    public String apply(Model model,@ModelAttribute Apply appli)
    {
        Page page = pagerepository.findBySlug("apply");
        model.addAttribute("apply",page);
        
        model.addAttribute("form",appli);

        return "application";
    }

    @PostMapping("/apply")
    public String apply(@Validated Apply appli,BindingResult bindingResult,RedirectAttributes redirectAttributes,Model model,@ModelAttribute Login login,@RequestParam("image") MultipartFile multipartFile) throws MessagingException, IOException
    {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        appli.setPhotos(fileName);
        Page page = pagerepository.findBySlug("balance");
    model.addAttribute("pages", page);
    List<Apply> appli1 = userrepository.findAll();
    model.addAttribute("full",appli1);
        userrepository.save(appli);
        redirectAttributes.addFlashAttribute("message","Form submitted");
        redirectAttributes.addFlashAttribute("alertclass", "alert-sucess");
        String email = appli.getEmail();
        System.out.println(email);
        model.addAttribute("email", email);
        String uploadDir = "user-photos/" + appli.getId();
 
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
       //emailService.sendSimpleMessage(email, "Welcome to Our Family", "Your have applied successfully. your can check the staus of application");

        return "balance1";
    }

    @GetMapping("/about-us")
    public String about(Model model)
    {
        Page page= pagerepository.findBySlug("about-us");
        model.addAttribute("pages",page);

        return "aboutus";
    }

    @GetMapping("/")
    public String home()
    {
        return "bestbank";
    }

    @GetMapping("/register")
    public String register(Model model,@ModelAttribute Register register)
    {
        
        model.addAttribute("register", register);
        return "register";
    }
    @PostMapping("/register")
    public String register(@Validated Register register,BindingResult bindingResult,Model model,RedirectAttributes redirectAttributes)
    {
        Page page = pagerepository.findBySlug("home");
        model.addAttribute("pages", page);

        List<Apply> appli = userrepository.findAll();
        model.addAttribute("full",appli);
        redirectAttributes.addFlashAttribute("message","go to login page");
        redirectAttributes.addFlashAttribute("alertclass", "alert-sucess");


        registerrepostiory.save(register);
        return "register";
    }
    @GetMapping("/login")
    public String login(Model model,@ModelAttribute Login login)
    {
        model.addAttribute("login", login);
        
        

        return "login";
    }

    

 @PostMapping("/login") 
 public String loginvalidation(@ModelAttribute Login login,HttpSession session,Model model,@ModelAttribute Type type1,HttpServletRequest request) 
 { 
    loginrepository.save(login);
     
    Page page = pagerepository.findBySlug("home");
    model.addAttribute("pages", page);
    List<Apply> appli = userrepository.findAll();
    model.addAttribute("full",appli);
    String email = login.getEmail();
    model.addAttribute("email", email);

    HttpSession session1 = request.getSession();
    /*model.addAttribute("type",type1);
    String v = type1.getLoantype();
    model.addAttribute("loant", v);*/
    
    

     List<Register> reg = registerrepostiory.findAll();
     for(Register x: reg)
     {
         if(x.getEmail().equals(login.getEmail()) && x.getPassword().equals(login.getPassword()))
         {
            session1.setAttribute("email", x.getEmail());
             return "home";
         }
     }

  
    return null;
 }

 @GetMapping("/plans")
 public String plan(@ModelAttribute Type type2,Model model,HttpSession session1)
 {
    List<Apply> appli = userrepository.findAll();
    model.addAttribute("full",appli);
    model.addAttribute("email", session1.getAttribute("email"));
    model.addAttribute("type",type2);
    String v = type2.getLoantype();
    model.addAttribute("loant", v);


     return "plans";
 }
 @PostMapping("/plans")
 public String plan1(@ModelAttribute Type type2,Model model,HttpSession session1)
 {
    List<Apply> appli = userrepository.findAll();
    model.addAttribute("full",appli);

    
    model.addAttribute("email", (String) session1.getAttribute("email"));
    model.addAttribute("type",type2);
    String v = type2.getLoantype();
    model.addAttribute("loant", v);

    return "plans";
 }
 @GetMapping("/balance")
 public String balance(Model model,@ModelAttribute Login log)
 {
    Page page = pagerepository.findBySlug("balance");
    model.addAttribute("pages", page);
    List<Apply> appli1 = userrepository.findAll();
    model.addAttribute("full",appli1);
    model.addAttribute("full1",appli1);

    return "balance";
 }
@PostMapping("/balance")
public String balance(@Validated Login log,Model model,Register register)
{
    Page page = pagerepository.findBySlug("balance");
    model.addAttribute("pages", page);
    List<Apply> appli1 = userrepository.findAll();
    model.addAttribute("full",appli1);
    String p = log.getEmail1();
    model.addAttribute("email", p);
    return "balance";
}

@GetMapping("/documents/{id}")
public ModelAndView documents(@PathVariable("id") int id)
{
    ModelAndView mv=new ModelAndView("documents");
    Apply a=userrepository.findById(id);
    mv.addObject("appli",a);
    return mv;
}
@GetMapping("/Manager")
public String Mlogin(@ModelAttribute Manager manager,Model model)
{
    model.addAttribute("Manager",manager);
 return "Manager";
}
@PostMapping("/Manager")
public String Mlogin1(@ModelAttribute Manager manager,Model model)
{
    int flag=0;
    List<Manager> manager1 = managerrepository.findAll();
    if(manager1.isEmpty())
    {
        flag=0;
        managerrepository.save(manager);
    }
    
    for(Manager X:manager1)
    {
        if(X.getEmail().equals(manager.getEmail()))
        {
          String access = manager.getAccessmanager();
         if(access=="Pending")
         {
          flag=1;
         }
        else{
        flag=0;
        }
        }
        else{
            managerrepository.save(manager);
        }
       
    }
    if(flag==1)
    {
        return "mandash";
    }
    else{
        return "error1";
    }

    
}

@GetMapping("/approval1")
    public String status(Model model)
    {
        List<Apply> appli = userrepository.findAll();
        model.addAttribute("full",appli);

        return "approval1";
    }

    @GetMapping("/approval1/{id}")
    public String status(@PathVariable int id,Model model)
    {
        Apply appli = userrepository.getOne(id);
        String lt = "Completed";
        appli.setRoles(lt);
        userrepository.save(appli);
        return "redirect:/approval1";
    }

    @GetMapping("/delete1/{id}")
    public String delete1(@PathVariable int id,RedirectAttributes redirectAttributes){
        userrepository.deleteById(id);


        return "redirect:/approval1";
    }

    @GetMapping("/calculator")
    public String calculator()
    {
        return "calculator";
    }

    
    @GetMapping("/dashboard")
    public String dashboard()
    {
        return "dashboard";
    }
    @GetMapping("/admin")
    public String admin(Model model,@ModelAttribute admin ad)
    {
        model.addAttribute("admin", ad);
        return "admin";
    }
    @PostMapping("/admin")
    public String admin(@Validated admin ad)
    {
        String email= "admin";
        String password = "admin";
        if(ad.getEmail().equals(email) && ad.getPassword().equals(password))
        {
            return "redirect:/admin/pages";
        }
        else
        {
            return "incorrect details";
        }

       
    }
}
