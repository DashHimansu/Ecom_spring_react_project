package com.cart.ecom_proj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cart.ecom_proj.model.Cart;
import com.cart.ecom_proj.model.Product;
import com.cart.ecom_proj.repo.CartRepo;
import com.cart.ecom_proj.repo.ProductRepo;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CartController {
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private ProductRepo prodRepo;


    @GetMapping("/get-cart")
    public List<Map<String,Object>> getCart() {
        System.out.println("kk");
       List<Cart> cartList = cartRepo.findAll();

    List<Map<String, Object>> result = cartList.stream()
        .map(c -> {
            Map<String, Object> data = new HashMap<>();
            data.put("cartId", c.getId());
            data.put("quantity", c.getQuantity());
            data.put("product", c.getProduct()); // fetches Product automatically
            return data;
        })
        .collect(Collectors.toList());

    return result;
    }


    @PostMapping("/add-to-cart/{id}")
    public Cart addToCart(@PathVariable long id){
      Product product = prodRepo.findById((int) id)
            .orElseThrow(() -> new RuntimeException("Product not found"));

    // Check if this product is already in the cart
    Optional<Cart> existingCart = cartRepo.findById(id);

    if (existingCart.isPresent()) {
        // Increase quantity
        Cart cart = existingCart.get();
        cart.setQuantity(cart.getQuantity() + 1);
        return cartRepo.save(cart);
    } else {
        // Add new cart entry
        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setQuantity(1);
        return cartRepo.save(cart);
    }
       
    }
    @PostMapping("/cart-up/{id}")
    public Cart cartUp(@PathVariable Long id){
        
         Cart cart = cartRepo.findById(id).orElseThrow(()-> new RuntimeException("Cart not found wirh id :"+id));
         cart.setQuantity(cart.getQuantity()+1);
         return cartRepo.save(cart);


    }
    @PostMapping("/cart-down/{id}")
    public Cart cartDown(@PathVariable Long id){
        
         Cart cart = cartRepo.findById(id).orElseThrow(()-> new RuntimeException("Cart not found wirh id :"+id));
         cart.setQuantity(cart.getQuantity()+1);
         return cartRepo.save(cart);


    }
}
