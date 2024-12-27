package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    // public List getUserById1(Long id){
    //     return this.userRepository.moi(id);
    // }

}
