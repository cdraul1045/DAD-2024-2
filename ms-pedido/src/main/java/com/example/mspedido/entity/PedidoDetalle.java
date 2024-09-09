package com.example.mspedido.entity;

import com.example.mspedido.dto.Product;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PedidoDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private Double cantidad;
    private Double precio;
    private Integer productId;
    @Transient
    private Product product;


    public PedidoDetalle() {
        this.cantidad = (double) 0;
        this.precio = (double) 0;
    }
}