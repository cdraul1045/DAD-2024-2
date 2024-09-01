package com.example.mscliente.controller;

import com.example.mscliente.entity.Client;
import com.example.mscliente.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @GetMapping
    public ResponseEntity<List<Client>> getAll() {
        return ResponseEntity.ok(clientService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Client>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(clientService.findById(id));
    }
    @PostMapping
    public ResponseEntity<Client> create(Client client) {
        return ResponseEntity.ok(clientService.save(client));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable Integer id, @RequestBody Client client) {
        client.setId(id);
        return ResponseEntity.ok(clientService.save(client));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<List<Client>> delete(@PathVariable Integer id) {
        clientService.delete(id);
        return ResponseEntity.ok(clientService.findAll());
    }
}
