package com.example.mspedido.entity;

import com.example.mspedido.dto.Product;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer quantity;
    private Double price;
    private Integer productId;
    @Transient
    private Product product;

    public OrderDetail() {
        this.quantity = 0;
        this.price = 0.0;
    }
}
