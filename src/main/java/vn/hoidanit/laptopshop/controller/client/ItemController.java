package vn.hoidanit.laptopshop.controller.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.service.ProductService;

@Controller
public class ItemController {
    private final ProductService productService;
    private final CartDetailRepository cartDetailRepository;

    public ItemController(ProductService productService, CartDetailRepository cartDetailRepository) {
        this.productService = productService;
        this.cartDetailRepository = cartDetailRepository;
    }

    @GetMapping("/product/{id}")
    public String getProductPage(Model model, @PathVariable Long id) {
        Product product = this.productService.getProductById(id);
        model.addAttribute("id", id);
        model.addAttribute("product", product);
        return "client/product/detail";
    }

    @PostMapping("/add-product-to-cart/{id}")
    public String addProductToCard(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long productId = id;
        String email = (String) session.getAttribute("email");

        this.productService.handleAddProductToCart(email, productId, session,1);

        return "redirect:/";
    }

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);

        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        Cart cart = this.productService.fetchByUser(currentUser);

        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

        double totalPrice = 0;
        for (CartDetail cartDetail : cartDetails) {
            totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
        }
        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cart", cart);
        return "client/cart/show";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String deleteCartDetail(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long CartDetailId = id;

        this.productService.deleteCartDetail(CartDetailId, session);

        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String getCheckOutPage(Model model, HttpServletRequest request) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);

        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        Cart cart = this.productService.fetchByUser(currentUser);

        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

        double totalPrice = 0;
        for (CartDetail cartDetail : cartDetails) {
            totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
        }
        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        return "client/cart/checkout";
    }

    @PostMapping("/confirm-checkout")
    public String getCheckOutPage(@ModelAttribute("cart") Cart cart) {

        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        this.productService.handleUpdateCartBeforeCheckout(cartDetails);
        return "redirect:/checkout";
    }

    @PostMapping("/place-order")
    public String handlePlaceOrder(
            HttpServletRequest request,
            @RequestParam("receiverName") String receiverName,
            @RequestParam("receiverAddress") String receiverAddress,
            @RequestParam("receiverPhone") String receiverPhone) {
        HttpSession session = request.getSession(false);
        User currentUser=new User();
        currentUser.setId((long)session.getAttribute("id"));

        this.productService.handlePlaceOrder(currentUser, session, receiverName, receiverAddress, receiverPhone);

        return "client/cart/orderSuccess";
    }
    @PostMapping("/add-product-from-view-detail")
    public String handleAddProuctfromViewDetail(
         @RequestParam("productId") long id,
         @RequestParam("quantity") long quantity,
         HttpServletRequest request
    ){
        HttpSession session=request.getSession(false);
        String email=(String)session.getAttribute("email");
        this.productService.handleAddProductToCart(email, id, session, quantity);
        return "redirect:/product/"+id;
    }
}