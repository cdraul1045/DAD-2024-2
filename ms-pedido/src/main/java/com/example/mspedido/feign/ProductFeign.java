package com.example.mspedido.feign;

import com.example.mspedido.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "ms-catalogo-service", path = "/product")
public interface ProductFeign {

    @GetMapping("/{id}")
    public ResponseEntity<Product> listById(@PathVariable(required = true) Integer id);
}
