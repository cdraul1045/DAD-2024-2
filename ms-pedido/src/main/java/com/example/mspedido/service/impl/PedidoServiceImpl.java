package com.example.mspedido.service.impl;


import com.example.mspedido.dto.Client;
import com.example.mspedido.dto.Product;
import com.example.mspedido.entity.Pedido;
import com.example.mspedido.entity.PedidoDetalle;
import com.example.mspedido.feign.ClientFeign;
import com.example.mspedido.feign.ProductFeign;
import com.example.mspedido.repository.PedidoRepository;
import com.example.mspedido.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClientFeign clientFeign;

    @Autowired
    private ProductFeign productFeign;

    @Override
    public List<Pedido> list() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido update(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    public Optional<Pedido> findById(Integer id) {
        return pedidoRepository.findById(id);
    }

    @Override
    public Optional<Pedido> listarPorId(Integer id) {
        Optional<Pedido> optionalPedido = pedidoRepository.findById(id);
        if (optionalPedido.isPresent()) {
            Pedido pedido = optionalPedido.get();
            ResponseEntity<Client> clientResponse = clientFeign.listById(pedido.getClientId());
            Client client = clientResponse.getBody();
            List<PedidoDetalle> pedidoDetalles = pedido.getDetalle().stream().map(pedidoDetalle -> {
                ResponseEntity<Product> productResponse = productFeign.listById(pedidoDetalle.getProductId());
                Product product = productResponse.getBody();
                pedidoDetalle.setProduct(product);
                return pedidoDetalle;
            }).collect(Collectors.toList());
            pedido.setDetalle(pedidoDetalles);
            pedido.setClient(client);
            return Optional.of(pedido);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(Integer id) {
        pedidoRepository.deleteById(id);
    }
}
