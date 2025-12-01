package com.cart.ecom_proj.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "product_id") // same column name as before, so data stays safe
   private Product product;
  private Integer quantity;
  @CreationTimestamp
  private LocalDateTime createdAt;
 @UpdateTimestamp
  private LocalDateTime updatedAt;

}
