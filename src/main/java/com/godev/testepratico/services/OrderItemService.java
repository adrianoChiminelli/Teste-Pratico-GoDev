package com.godev.testepratico.services;

import com.godev.testepratico.dto.OrderItemResultDTO;
import com.godev.testepratico.model.ItemEntity;
import com.godev.testepratico.model.OrderItemEntity;
import com.godev.testepratico.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public OrderItemEntity saveOrderItem(OrderItemEntity orderItem){
        return orderItemRepository.save(orderItem);
    }

    public Optional<OrderItemEntity> getOrderItemById(UUID id){
        return orderItemRepository.findById(id);
    }

    public void deleteOrderItem(UUID id){
        orderItemRepository.deleteById(id);
    }

    public List<OrderItemEntity> getOrdersItem(UUID orderId){
        return orderItemRepository.findByOrderId(orderId);
    }

    public List<OrderItemResultDTO> orderItemToDTO(List<OrderItemEntity> orderItemList) {
        return orderItemList.stream()
                .map(orderItem -> {
                    ItemEntity item = orderItem.getItem();

                    return new OrderItemResultDTO(orderItem.getId(),
                            item.getId(),
                            orderItem.getQuantity(),
                            orderItem.getTotalValue());
                }).collect(Collectors.toList());
    }

}
