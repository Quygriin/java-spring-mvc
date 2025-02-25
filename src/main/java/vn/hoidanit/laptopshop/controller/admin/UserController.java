package vn.hoidanit.laptopshop.controller.admin;


import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.UserService;


@Controller
public class UserController {
  private final UserService userService;
  private final UploadService uploadService;
  private final PasswordEncoder passwordEncoder;

  public UserController(
            UserService userService, 
            UploadService uploadService,
            PasswordEncoder passwordEncoder) {
        this.passwordEncoder=passwordEncoder;
        this.userService = userService;
        this.uploadService= uploadService;
    }

  @RequestMapping("/")
  public String getHomePage(Model model) {
    List<User> arr = this.userService.getAllUser();
    List<User> arr1 = this.userService.getAllUserByEmail("quy8@gmail.com");

    System.out.println(arr1);
    String test = this.userService.handleHello();
    model.addAttribute("quy", "test");
    model.addAttribute("anhquy", "moimoi");
    return "admin/user/create";
  }

  @GetMapping("/admin/user/create")
  public String getUserCreatePage(Model model) {
    model.addAttribute("newUser", new User());
    return "admin/user/create";
  }

  @PostMapping(value = "/admin/user/create")
  public String createUserPage(Model model,
            @ModelAttribute("newUser") @Valid User hoidanit,
            BindingResult newUserBindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {

        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>" + error.getField() + " - " + error.getDefaultMessage());
        }

        // validate
        if (newUserBindingResult.hasErrors()) {
            return "admin/user/create";
        }

        //
        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
        String hashPassword = this.passwordEncoder.encode(hoidanit.getPassword());

        hoidanit.setAvatar(avatar);
        hoidanit.setPassword(hashPassword);
        hoidanit.setRole(this.userService.getRoleByName(hoidanit.getRole().getName()));
        // save
        this.userService.handleSaveUser(hoidanit);
        return "redirect:/admin/user";
    }

  @RequestMapping("/admin/user/{id}")
  public String getUserDetailPage(Model model, @PathVariable long id) {
    System.out.println("check path:" + id);
    model.addAttribute("id", id);
    User user1 = this.userService.getUserById(id);
    model.addAttribute("user", user1);
    return "admin/user/detail";
  }

  @RequestMapping("/admin/user")
  public String getUserPage(Model model) {
    List<User> users = this.userService.getAllUser();
    model.addAttribute("users1", users);
    System.out.println("check:" + users);
    return "admin/user/show";
  }

  @RequestMapping("/admin/user/update/{id}")
  public String UpdateUser(Model model, @PathVariable long id) {
    model.addAttribute("id", id);
    User user1 = this.userService.getUserById(id);
    model.addAttribute("newUser", user1);
    return "admin/user/update";
  }
  @RequestMapping("/admin/user/delete/{id}")
  public String DeleteUser(Model model, @PathVariable long id) {
    model.addAttribute("id", id);
    User user1=new User();
    user1.setId(id);
    model.addAttribute("newUser", user1);
    // this.userService.deleteUser(id);
    return "admin/user/delete";
  }
  @PostMapping("/admin/user/delete")
  public String postDeleteUser(Model model, @ModelAttribute("newUser") User u) {
    this.userService.deleteUser(u.getId());
    return "redirect:/admin/user";
  }
  @PostMapping("/admin/user/update")
  public String postUpdateUser(Model model, @ModelAttribute("newUser") User quy) {
    User user1 = this.userService.getUserById(quy.getId());
    if (user1 != null) {
      user1.setPhone(quy.getPhone());
      user1.setAddress(quy.getAddress());
      user1.setFullName(quy.getFullName());
      this.userService.handleSaveUser(user1);
    }
    return "redirect:/admin/user";
  }
}

// @RestController
// public class UserController {
// private UserService UserService;

// public UserController(UserService userService) {
// this.UserService = userService;
// }

// @GetMapping("")
// public String getHomePage(){
// return this.UserService.handleHello();
// }
