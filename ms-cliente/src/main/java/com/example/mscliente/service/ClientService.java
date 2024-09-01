package com.example.mscliente.service;

import com.example.mscliente.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    public List<Client> findAll();
    public Optional<Client> findById(Integer id);
    public Client save(Client client);
    public Client update(Client client);
    public void delete(Integer id);
}
