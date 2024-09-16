package com.example.mscatalogo.service;

import com.example.mscatalogo.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public List<Product> findAll();
    public Optional<Product> findById(Integer id);
    public Product save(Product product);
    public Product update(Product product);
    public void delete(Integer id);
}
