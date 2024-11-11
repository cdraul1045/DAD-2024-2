package com.example.mspedido.controller;

import com.example.mspedido.dto.Client;
import com.example.mspedido.dto.ErrorResponser;
import com.example.mspedido.dto.Product;
import com.example.mspedido.entity.Order;
import com.example.mspedido.entity.OrderDetail;
import com.example.mspedido.feign.ClientFeign;
import com.example.mspedido.feign.ProductFeign;
import com.example.mspedido.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ClientFeign clientFeign;
    @Autowired
    private ProductFeign productFeign;

    @GetMapping
    public ResponseEntity<List<Order>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Order>> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.findById(id));
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Order order) {
        Client clientDto = clientFeign.getById(order.getClientId()).getBody();

        if (clientDto == null || clientDto.getId() == null) {
            String errorMessage = "Error: Cliente no encontrado.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponser(errorMessage));
        }
        for (OrderDetail orderDetail : order.getDetail()) {
            Product product = productFeign.getById(orderDetail.getProductId()).getBody();

            if (product == null || product.getId() == null) {
                String errorMessage = "Error: producto no encontrado.";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponser(errorMessage));
            }
        }
        Order newOrder = orderService.save(order);
        return ResponseEntity.ok(newOrder);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Order> update(@PathVariable Integer id, @RequestBody Order order) {
        order.setId(id);
        return ResponseEntity.ok(orderService.save(order));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<List<Order>> delete(@PathVariable Integer id) {
        orderService.delete(id);
        return ResponseEntity.ok(orderService.findAll());
    }
}
