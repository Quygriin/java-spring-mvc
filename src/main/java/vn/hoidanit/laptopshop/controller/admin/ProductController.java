package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;

@Controller
public class ProductController {
    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/product")
    public String getDashboard(Model model,@RequestParam("page") int page) {
        Pageable pageable=PageRequest.of(page - 1, 2);
        Page<Product> prs = this.productService.getAllProduct(pageable);
        List<Product> listpProducts=prs.getContent();

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", prs.getTotalPages());
     
        model.addAttribute("products", listpProducts);
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String createProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping(value = "/admin/product/create")
    public String createUserPage(Model model,
            @ModelAttribute("newProduct") @Valid Product p,
            BindingResult newUserBindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {

        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>" + error.getField() + " - " + error.getDefaultMessage());
        }

        // validate
        if (newUserBindingResult.hasErrors()) {
            return "admin/product/create";
        }

        String image = this.uploadService.handleSaveUploadFile(file, "product");

        p.setImage(image);
        // save
        this.productService.handleProductSave(p);
        return "redirect:/admin/product";
    }

    @RequestMapping("/admin/product/delete/{id}")
    public String deleteProduct(Model model, @PathVariable long id) {

        model.addAttribute("id", id);
        Product product1 = new Product();
        product1.setId(id);
        model.addAttribute("newProduct", product1);
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String postDeleteUser(Model model, @ModelAttribute("newProduct") Product u) {
        this.productService.deleteProduct(u.getId());
        return "redirect:/admin/product";
    }

    @RequestMapping("/admin/product/update/{id}")
    public String updateProduct(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        Product product = new Product();
        product = this.productService.getProductById(id);
        model.addAttribute("newProduct", product);
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String postUpdateProduct(Model model,
            @ModelAttribute("newProduct") @Valid Product p,
            BindingResult newUserBindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {

        Product product = this.productService.getProductById(p.getId());

        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>" + error.getField() + " - " + error.getDefaultMessage());
        }

        // validate
        if (newUserBindingResult.hasErrors()) {
            return "admin/product/update";
        }

        String image = this.uploadService.handleSaveUploadFile(file, "product");

        if (product != null) {
            product.setDetailDesc(p.getDetailDesc());
            product.setName(p.getName());
            product.setFactory(p.getFactory());
            product.setPrice(p.getPrice());
            product.setQuantity(p.getQuantity());
            product.setShortDesc(p.getShortDesc());
            product.setTarget(p.getTarget());

            if (image != null) {
                product.setImage(image);
            }
            
            this.productService.handleProductSave(product);
        }
        return "redirect:/admin/product";
    }
    @RequestMapping("admin/product/{id}")
    public String detailProduct(Model model,@PathVariable long id){
        Product product=this.productService.getProductById(id);
        model.addAttribute("product", product);
        return "admin/product/detail";
    }
}
