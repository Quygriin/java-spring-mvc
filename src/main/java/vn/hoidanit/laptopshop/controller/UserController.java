package vn.hoidanit.laptopshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController{
      private UserService UserService;
    
   public UserController(UserService userService) {
        this.UserService = userService;
    }
   @RequestMapping("/")
   public String getHomePage(Model model){
      String test=this.UserService.handleHello();
      model.addAttribute("quy", test);
      model.addAttribute("anhquy", "moimoi");
    return "hello";
   }
   @RequestMapping("/admin/user")
   public String getUserPage(Model model){
      String test=this.UserService.handleHello();
      model.addAttribute("quy", test);
      model.addAttribute("anhquy", "moimoi");
    return "admin/user/create";
   }
   
}


// @RestController
// public class UserController {
//     private UserService UserService;
    
//    public UserController(UserService userService) {
//         this.UserService = userService;
//     }

// @GetMapping("")
//     public String getHomePage(){
//         return this.UserService.handleHello();
//     }

