package com.godev.testepratico.services;

<<<<<<< HEAD
import com.godev.testepratico.model.OrderEntity;
import com.godev.testepratico.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
=======
import com.godev.testepratico.exception.ItemNotFoundException;
import com.godev.testepratico.exception.OrderNotFoundException;
import com.godev.testepratico.model.OrderEntity;
import com.godev.testepratico.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
>>>>>>> Nova-Implementacao

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public OrderEntity saveOrder(OrderEntity order){
<<<<<<< HEAD
        return orderRepository.save(order);
    }

    public Optional<OrderEntity> getOrderById(UUID id){
        return orderRepository.findById(id);
    }

    public void deleteOrderById(UUID id){
        orderRepository.deleteById(id);
=======
        if(order.getTotalValue() == null){
            order.setTotalValue(0.);
        }

        return orderRepository.save(order);
    }

    public OrderEntity getOrderById(UUID id){
        return orderRepository.findById(id).orElseThrow(() ->
                new OrderNotFoundException("Pedido não foi encontrado!!!"));
    }

    public OrderEntity updateOrder(UUID id, OrderEntity newOrder) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setNumber(newOrder.getNumber());
                    order.setDate(newOrder.getDate());
                    order.setPercentualDiscount(newOrder.getPercentualDiscount());
                    order.setTotalValue(newOrder.getTotalValue());
                    return orderRepository.save(order);
                }).orElseThrow(() -> new OrderNotFoundException("Pedido não foi encontrado!!!"));
    }

    public void deleteOrderById(UUID id){
        orderRepository.findById(id)
                .ifPresentOrElse(item -> orderRepository.deleteById(id),
                        () -> new OrderNotFoundException("Pedido não foi encontrado!!!"));
>>>>>>> Nova-Implementacao
    }

    public List<OrderEntity> getOrders(){
        return orderRepository.findAll();
    }

}
