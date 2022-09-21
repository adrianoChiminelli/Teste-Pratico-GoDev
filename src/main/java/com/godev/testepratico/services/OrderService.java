package com.godev.testepratico.services;

import com.godev.testepratico.model.OrderEntity;
import com.godev.testepratico.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public OrderEntity saveOrder(OrderEntity order){
        return orderRepository.save(order);
    }

    public Optional<OrderEntity> getOrderById(UUID id){
        return orderRepository.findById(id);
    }

    public void deleteOrderById(UUID id){
        orderRepository.deleteById(id);
    }

    public List<OrderEntity> getOrders(){
        return orderRepository.findAll();
    }

}
