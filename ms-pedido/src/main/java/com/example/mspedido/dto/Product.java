package com.example.mspedido.dto;

import lombok.Data;

@Data
public class Product {
    private Integer id;
    private String name;
    private String description;
    private String price;
    private String stock;
}