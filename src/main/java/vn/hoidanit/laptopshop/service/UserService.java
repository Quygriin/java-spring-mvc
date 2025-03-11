package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.Role;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.dto.RegisterDTO;
import vn.hoidanit.laptopshop.repository.OrderRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;
import vn.hoidanit.laptopshop.repository.RoleRepository;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
   
    public UserService(UserRepository userRepository,RoleRepository roleRepository,
    OrderRepository orderRepository,ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.roleRepository=roleRepository;
        this.orderRepository=orderRepository;
        this.productRepository=productRepository;
       
    }

    public String handleHello(){
        return "chao";
    }
    public List getAllUser(){
        return this.userRepository.findAll();
    }
    public User handleSaveUser(User h){
        return this.userRepository.save(h);
    }
    public List getAllUserByEmail(String email){
        return this.userRepository.findOneByEmail(email);
    }
    public User getUserById(long id){
        return this.userRepository.findById(id).orElse(null);
    }
    public void deleteUser(long id){
         this.userRepository.deleteById(id);
    }
     public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }
    public User registerDTOtoUser(RegisterDTO registerdto){
          User user=new User();
          user.setFullName(registerdto.getFirstName()+" "+registerdto.getLastName());
          user.setEmail(registerdto.getEmail());
          user.setPassword(registerdto.getPassword());
        return user;
    }
    // public List getUserById1(Long id){
    //     return this.userRepository.moi(id);
    // }
    public boolean checkEmailExists(String email){
        return this.userRepository.existsByEmail(email);
    }
    public User getUserByEmail(String email){
        return this.userRepository.findByEmail(email);
    }
    public long countUser(){
        return this.userRepository.count();
    }
    public long countProduct(){
        return this.productRepository.count();
    }
    public long countOrder(){
        return this.orderRepository.count();
    }
}
