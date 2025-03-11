package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.OrderService;
import vn.hoidanit.laptopshop.service.ProductService;

@Controller
public class OrderController {
    
private final OrderService orderService;
private final ProductService productService;


    public OrderController(OrderService orderService,ProductService productService) {
    this.orderService = orderService;
    this.productService=productService;
}



    @GetMapping("/admin/order")
    public String getDashboard(Model model) {
        List<Order> orders=this.orderService.getAllOrder();
        model.addAttribute("orders", orders);
        return "admin/order/show";
    }
    @GetMapping("/admin/order/{id}")
    public String getDetailOrderPage(Model model,@PathVariable long id){
        Order order=this.orderService.getOrderById(id);
        List<OrderDetail> orderDetails=order.getOrderDetails();
        model.addAttribute("order", order);
        model.addAttribute("orderDetails", orderDetails);
        return "admin/order/detail";
    }
    @RequestMapping("/admin/orderDetail/{id}")
    public String NewdetailProduct(Model model,@PathVariable long id){
        Product product=this.productService.getProductById(id);
        model.addAttribute("product", product);
        return "admin/product/detail";
    }
    @GetMapping("/admin/order/update/{id}")
    public String getOrderUpdate(Model model,@PathVariable long id){
        
        Order order=this.orderService.getOrderById(id);
        model.addAttribute("newOrder", order);

        return "admin/order/update";
    }

    @PostMapping("/admin/order/update")
    public String postOrderUpdate(Model model,@ModelAttribute("newOrder")Order o){
        Order order=this.orderService.getOrderById(o.getId());
        order.setStatus(o.getStatus());
        this.orderService.handleSaveOrder(order);
        return "redirect:/admin/order";
    }
    @GetMapping("/admin/order/delete/{id}")
    public String deleteOrderPage(Model model,@PathVariable long id){
        Order order=this.orderService.getOrderById(id);
        model.addAttribute("id", id);
        model.addAttribute("newOrder", order);
        return "admin/order/delete";
    }
    @PostMapping("/admin/order/delete")
    public String deleteOrderPage(Model model,@ModelAttribute("newOrder") Order order){
        
        this.orderService.handleDeleteOrder(order.getId());
        return "redirect:/admin/order";
    }
}
