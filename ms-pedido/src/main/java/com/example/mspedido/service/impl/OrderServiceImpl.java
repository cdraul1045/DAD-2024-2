package com.example.mspedido.service.impl;

import com.example.mspedido.dto.Client;
import com.example.mspedido.dto.Product;
import com.example.mspedido.entity.Order;
import com.example.mspedido.entity.OrderDetail;
import com.example.mspedido.feign.ClientFeign;
import com.example.mspedido.feign.ProductFeign;
import com.example.mspedido.repository.OrderRepository;
import com.example.mspedido.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ClientFeign clientFeign;
    @Autowired
    private ProductFeign productFeign;

    @Override
    public List<Order> findAll() {
        List<Order> orders = orderRepository.findAll();

        // Para cada orden en la lista, obtenemos los detalles del cliente y productos
        return orders.stream().map(order -> {
            // Obtener cliente
            ResponseEntity<Optional<Client>> clientResponse = clientFeign.getById(order.getClientId());
            if (clientResponse.getStatusCode().is2xxSuccessful()) {
                Optional<Client> client = clientResponse.getBody();
                if (client.isPresent()) {
                    order.setClient(client.get());
                }
            }

            // Obtener productos en cada detalle
            List<OrderDetail> orderDetails = order.getDetail().stream().map(orderDetail -> {
                ResponseEntity<Optional<Product>> productResponse = productFeign.getById(orderDetail.getProductId());
                if (productResponse.getStatusCode().is2xxSuccessful()) {
                    Optional<Product> product = productResponse.getBody();
                    if (product.isPresent()) {
                        orderDetail.setProduct(product.get());
                    }
                }
                return orderDetail;
            }).collect(Collectors.toList());

            order.setDetail(orderDetails);
            return order;
        }).collect(Collectors.toList());
    }


    @Override
    public Optional<Order> findById(Integer id) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();

            // Obtener cliente
            ResponseEntity<Optional<Client>> clientResponse = clientFeign.getById(order.getClientId());
            if (clientResponse.getStatusCode().is2xxSuccessful()) {
                Optional<Client> client = clientResponse.getBody();
                if (client.isPresent()) {
                    order.setClient(client.get());
                    System.out.println("Cliente obtenido: " + client); // Log para verificar cliente
                } else {
                    System.out.println("Cliente es null"); // Log para verificar si es null
                }
            }

            // Obtener productos
            List<OrderDetail> orderDetails = order.getDetail().stream().map(orderDetail -> {
                ResponseEntity<Optional<Product>> productResponse = productFeign.getById(orderDetail.getProductId());
                if (productResponse.getStatusCode().is2xxSuccessful()) {
                    Optional<Product> product = productResponse.getBody();
                    if (product.isPresent()) {
                        orderDetail.setProduct(product.get());
                        System.out.println("Producto obtenido: " + product); // Log para verificar producto
                    } else {
                        System.out.println("Producto es null"); // Log para verificar si es null
                    }
                }
                return orderDetail;
            }).collect(Collectors.toList());

            order.setDetail(orderDetails);
            return Optional.of(order);
        }
        return Optional.empty();
    }


    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void delete(Integer id) {
        orderRepository.deleteById(id);
    }
}
