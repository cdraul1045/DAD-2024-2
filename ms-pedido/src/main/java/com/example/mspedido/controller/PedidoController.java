package com.example.mspedido.controller;


import com.example.mspedido.entity.Pedido;
import com.example.mspedido.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<Pedido>> list() {
        List<Pedido> pedidos = pedidoService.list();
        return ResponseEntity.ok(pedidos);
    }

    @PostMapping
    public ResponseEntity<Pedido> save(@RequestBody Pedido pedido) {
        Pedido savedPedido = pedidoService.save(pedido);
        return ResponseEntity.ok(savedPedido);
    }

    @PutMapping
    public ResponseEntity<Pedido> update(@RequestBody Pedido pedido) {
        Pedido updatedPedido = pedidoService.update(pedido);
        return ResponseEntity.ok(updatedPedido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> listById(@PathVariable Integer id) {
        Optional<Pedido> pedido = pedidoService.findById(id);
        return pedido.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        pedidoService.deleteById(id);
        return ResponseEntity.ok("Eliminación Correcta");
    }
}
