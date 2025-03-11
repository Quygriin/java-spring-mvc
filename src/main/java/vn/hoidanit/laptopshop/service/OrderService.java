package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserService userService;
   

    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository,UserService userService) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.userService=userService;
    }

    public List<Order> getAllOrder(){
        return this.orderRepository.findAll();
    }
    public Order getOrderById(long id){
        return this.orderRepository.findById(id).orElse(null);
    }
    public void handleSaveOrder(Order order){
        this.orderRepository.save(order);
    }
    public void handleDeleteOrder(long id){
        Order order=this.getOrderById(id);
        List<OrderDetail> orderDetails=order.getOrderDetails();
        for (OrderDetail orderDetail : orderDetails) {
            this.orderDetailRepository.delete(orderDetail);
        }
        this.orderRepository.deleteById(id);
    }
    public List<Order> fetchOrderByUser(long id){
        User user=this.userService.getUserById(id);
        System.out.println(user.getFullName());
        List<Order> o=user.getOrder();
        return o;
    }
}
