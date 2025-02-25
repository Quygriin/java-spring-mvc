package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.repository.ProductRepository;
import vn.hoidanit.laptopshop.repository.RoleRepository;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class ProductService {
      private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository=productRepository;
    }
    public void handleProductSave(Product p){
        this.productRepository.save(p);
    }
    public List<Product> getAllProduct(){
        return this.productRepository.findAll();
    }
    public Product getProductById(long id){
        return this.productRepository.findById(id).orElse(null);
    }
    public void deleteProduct(long id){
        this.productRepository.deleteById(id);
    }
}
