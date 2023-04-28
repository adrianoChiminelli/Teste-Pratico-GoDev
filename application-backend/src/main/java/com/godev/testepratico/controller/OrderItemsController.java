package com.godev.testepratico.controller;

import com.godev.testepratico.controller.dto.OrderItemResultDTO;
import com.godev.testepratico.controller.dto.OrderWithNewPercentualDTO;
import com.godev.testepratico.controller.dto.OrderClosedDTO;
import com.godev.testepratico.controller.dto.OrderItemDTO;
import com.godev.testepratico.model.OrderItemEntity;
import com.godev.testepratico.services.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderItemsController {

    private final OrderItemService orderItemService;

    @PostMapping("/{orderId}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderItemResultDTO saveOrder(@PathVariable("orderId")UUID orderId, @RequestBody @Valid OrderItemDTO orderItemDTO){
        return orderItemService.saveOrderItem(orderId, orderItemDTO);
    }

    @GetMapping("/{orderId}/items/{orderItemId}")
    public OrderItemResultDTO getOrderItemById(@PathVariable UUID orderItemId){
        return orderItemService.getOrderItemById(orderItemId);
    }

    @GetMapping("/{orderId}/items")
    public List<OrderItemEntity> getOrderItems(@PathVariable UUID orderId){
        return orderItemService.getOrdersItem(orderId);
    }

    @DeleteMapping("/{orderId}/items/{orderItemId}")
    public void deleteOrderItem(@PathVariable("orderId") UUID orderId, @PathVariable("orderItemId") UUID orderItemId){
        orderItemService.deleteOrderItem(orderItemId, orderId);
    }

    @PutMapping("/{orderId}/items/{orderItemId}")
    public OrderItemResultDTO updateOrderItem(@PathVariable("orderId") UUID orderId,
                                              @PathVariable("orderItemId") UUID orderItemId,
                                              @RequestBody @Valid OrderItemDTO orderItemDTO){
        return orderItemService.updateOrderItem(orderId, orderItemId, orderItemDTO);
    }

    @PutMapping("/{orderId}/close")
    public OrderClosedDTO closeOrder(@PathVariable UUID orderId, @RequestBody @Valid OrderWithNewPercentualDTO newOrderPercentual){
        return orderItemService.closeOrderWithNewPercentual(orderId, newOrderPercentual);
    }

    @GetMapping("/{orderId}/close")
    public OrderClosedDTO closeOrder(@PathVariable UUID orderId){
        return orderItemService.closeOrder(orderId);
    }

    @PostMapping("/{orderId}/items/saveAll")
    @ResponseStatus(HttpStatus.CREATED)
    public List<OrderItemEntity> saveOrderItemInBatch(@RequestBody @Valid List<OrderItemEntity> orderItemList){
        return orderItemService.saveOrderItemBatch(orderItemList);
    }
}
