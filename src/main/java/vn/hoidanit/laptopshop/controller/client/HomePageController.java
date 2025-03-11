package vn.hoidanit.laptopshop.controller.client;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.dto.RegisterDTO;
import vn.hoidanit.laptopshop.service.OrderService;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class HomePageController {
   private final ProductService productService;
   private final UserService userService;
   private final PasswordEncoder passwordEncoder;
   private final OrderService orderService;
   public HomePageController(ProductService productService, UserService userService,
   OrderService orderService, PasswordEncoder passwordEncoder) {
      this.productService = productService;
      this.userService = userService;
      this.passwordEncoder = passwordEncoder;
      this.orderService=orderService;
   }

   @GetMapping("/")
   public String getHomePage(Model model) {
 Pageable pageable=PageRequest.of(0, 10);
        Page<Product> prs = this.productService.getAllProduct(pageable);
        List<Product> products=prs.getContent();
      model.addAttribute("products", products);

      return "client/homepage/show";
   }

   @GetMapping("/register")
   public String getRegisterPage(Model model) {
      model.addAttribute("registerUser", new RegisterDTO());
      return "client/auth/register";
   }

   @PostMapping("/register")
   public String postRegisterPage(Model model, @ModelAttribute("registerUser") @Valid RegisterDTO registerDTO,
         BindingResult bindingResult) {

      if (bindingResult.hasErrors()) {
         return "client/auth/register";
      }
      User user = this.userService.registerDTOtoUser(registerDTO);
      String hashPassword = this.passwordEncoder.encode(user.getPassword());
      user.setPassword(hashPassword);
      this.userService.handleSaveUser(user);
      return "redirect:/login";
   }

   @GetMapping("/login")
   public String getLoginPage(Model model) {

      return "client/auth/login";
   }

   @GetMapping("/access-deny")
   public String getDenyPage(Model model) {

      return "client/auth/deny";
   }

   @GetMapping("/orderhistory")
   public String getOrderHistoryPage(Model model, HttpServletRequest request) {

      HttpSession session = request.getSession(false);
      User currentUser = new User();
      long id =(long) session.getAttribute("id");
      currentUser.setId(id);
      List<Order> orders=this.orderService.fetchOrderByUser(currentUser.getId());
      model.addAttribute("orders", orders);
      return "client/cart/orderhistory";
   }
}
