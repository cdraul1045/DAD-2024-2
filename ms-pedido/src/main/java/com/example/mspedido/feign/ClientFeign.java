package com.example.mspedido.feign;

import com.example.mspedido.dto.Client;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "ms-cliente-service", path = "/client")
public interface ClientFeign {
    @GetMapping("/{id}")
    @CircuitBreaker(name = "clientListByIdCB", fallbackMethod = "clientListById")
    public ResponseEntity<Client> getById(@PathVariable Integer id);
    default ResponseEntity<Client> clientListById(Integer id, Exception e) {
        return ResponseEntity.ok(new Client());
    }
}
