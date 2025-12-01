package com.cart.ecom_proj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.cart.ecom_proj.model.Product;
import com.cart.ecom_proj.repo.ProductRepo;
import com.cart.ecom_proj.service.ProductService;

@SpringBootTest
public class ProductServiceTest {
  
    @InjectMocks
    ProductService productService;
    @Mock
    ProductRepo repo;

    private int counter;

    @BeforeEach // Runs before test1 and before test2
    public void setup() {
        // Initialize counter for the upcoming test
        counter = 0;
        System.out.println("Setup: Counter reset to " + counter);
    }

    @AfterEach // Runs after test1 and after test2
    public void tearDown() {
        // Cleanup or logging after the test
        System.out.println("Teardown: Test finished.");
    }

    @Test
    public void getProductById(){
        
        when(repo.findById(5)).thenReturn(createProductStub());
        Product prod = productService.getProductById(5);
        System.out.println("DEBUG: Product Name found: " + prod.getName());
        assertEquals("realme gt6t", prod.getName(), "Product name should match stubbed value.");

    }

  private Optional<Product> createProductStub(){
   Product stubPro = Product.builder()
        .id(5)
        .name("realme gt6t")
        .build();
    return Optional.of(stubPro);
  }

}
