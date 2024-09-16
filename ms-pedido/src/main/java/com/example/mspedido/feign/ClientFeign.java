package com.example.mspedido.feign;

import com.example.mspedido.dto.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "ms-cliente-service", path = "/client")
public interface ClientFeign {
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Client>> getById(@PathVariable Integer id);
}
