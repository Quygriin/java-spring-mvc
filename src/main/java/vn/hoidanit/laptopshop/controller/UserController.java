package vn.hoidanit.laptopshop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;

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

  @RequestMapping("/admin/user/create")
  public String getUserCreatePage(Model model) {
    return "admin/user/create";
  }

  @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
  public String CreateUserPage(Model model, @ModelAttribute("newUser") User hoidanit) {
    System.out.println("khach" + hoidanit);
    userService.handleSaveUser(hoidanit);
    return "redirect:/admin/user";
  }

  @RequestMapping("/admin/user/{id}")
  public String getUserDetailPage(Model model, @PathVariable long id) {
    System.out.println("check path:" + id);
    model.addAttribute("id", id);
    User user1 = this.userService.getUserById(id);
    model.addAttribute("user", user1);
    return "admin/user/show";
  }

  @RequestMapping("/admin/user")
  public String getUserPage(Model model) {
    List<User> users = this.userService.getAllUser();
    model.addAttribute("users1", users);
    System.out.println("check:" + users);
    return "admin/user/tableUser";
  }

  @RequestMapping("/admin/user/update/{id}")
  public String UpdateUser(Model model, @PathVariable long id) {
    model.addAttribute("id", id);
    User user1 = this.userService.getUserById(id);
    model.addAttribute("newUser", user1);
    return "/admin/user/update";
  }
  @RequestMapping("/admin/user/delete/{id}")
  public String DeleteUser(Model model, @PathVariable long id) {
    model.addAttribute("id", id);
    User user1=new User();
    user1.setId(id);
    model.addAttribute("newUser", user1);
    // this.userService.deleteUser(id);
    return "/admin/user/delete";
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
