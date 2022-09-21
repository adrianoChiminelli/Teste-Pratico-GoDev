package com.godev.testepratico.controller;

import com.godev.testepratico.dto.OrderClosedDTO;
import com.godev.testepratico.dto.OrderItemDTO;
import com.godev.testepratico.dto.OrderItemResultDTO;
import com.godev.testepratico.enums.ItemType;
import com.godev.testepratico.model.ItemEntity;
import com.godev.testepratico.model.OrderEntity;
import com.godev.testepratico.model.OrderItemEntity;
import com.godev.testepratico.services.ItemService;
import com.godev.testepratico.services.OrderItemService;
import com.godev.testepratico.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderItemsController {

    private final OrderItemService orderItemService;
    private final OrderService orderService;
    private final ItemService itemService;

    @PostMapping("/{orderId}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderItemResultDTO saveOrder(@PathVariable("orderId")UUID orderId, @RequestBody OrderItemDTO orderItemDTO){

        List<OrderItemEntity> itemsList = orderItemService.getOrdersItem(orderId);

        OrderEntity order = orderService.getOrderById(orderId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado!!"));

        UUID idItem = orderItemDTO.getItemId();
        ItemEntity itemEntity = itemService.getItemById(idItem)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado!!"));

        Double percentualDiscount = order.getPercentualDiscount();
        Double Value = itemEntity.getValue() * orderItemDTO.getQuantity();

        OrderItemEntity orderItemEntity = new OrderItemEntity(order, itemEntity, orderItemDTO.getQuantity(), 0D);
        orderItemEntity.setOrder(order);
        orderItemEntity.setTotalValue(Value);

        itemsList.add(orderItemEntity);

        List<OrderItemResultDTO> orderItemResultDTOList = orderItemService.orderItemToDTO(itemsList);

        order.setTotalValue(calculateTotalValue(orderItemResultDTOList, percentualDiscount));


        orderService.saveOrder(order);

        OrderItemEntity orderItem = orderItemService.saveOrderItem(orderItemEntity);
        return new OrderItemResultDTO(orderItem.getId(), itemEntity.getId(), orderItem.getQuantity(), orderItem.getTotalValue());
    }

    @GetMapping("/{orderId}/items/{orderItemId}")
    public OrderItemResultDTO getOrderItemById(@PathVariable UUID orderItemId){
        OrderItemEntity orderItem = orderItemService.getOrderItemById(orderItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de pedido não encontrado!!"));

        ItemEntity item = orderItem.getItem();

        return new OrderItemResultDTO(orderItem.getId(),
                item.getId(),
                orderItem.getQuantity(),
                orderItem.getTotalValue());
    }

    @GetMapping("/{orderId}/items")
    public List<OrderItemResultDTO> getOrderItems(@PathVariable UUID orderId){

        List<OrderItemEntity> itemsList = orderItemService.getOrdersItem(orderId);

        return orderItemService.orderItemToDTO(itemsList);
    }

    @DeleteMapping("/{orderId}/items/{orderItemId}")
    public void deleteOrderItem(@PathVariable("orderId") UUID orderId, @PathVariable("orderItemId") UUID orderItemId){
        OrderEntity order = orderService.getOrderById(orderId).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado!!"));

        orderItemService.deleteOrderItem(orderItemId);

        List<OrderItemEntity> itemsList = orderItemService.getOrdersItem(orderId);
        List<OrderItemResultDTO> orderItemListDTOS = orderItemService.orderItemToDTO(itemsList);

        order.setTotalValue(calculateTotalValue(orderItemListDTOS, order.getPercentualDiscount()));

        orderService.saveOrder(order);
    }

    @PutMapping("/{orderId}/items/{orderItemId}")
    public OrderItemResultDTO updateOrderItem(@PathVariable("orderId") UUID orderId,
                                           @PathVariable("orderItemId") UUID orderItemId,
                                           @RequestBody OrderItemDTO orderItemDTO)
    {
        UUID idItem = orderItemDTO.getItemId();
        ItemEntity itemEntity = itemService.getItemById(idItem).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado!!"));

        OrderEntity order = orderService.getOrderById(orderId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado!!"));

        Double totalValue = itemEntity.getValue() * orderItemDTO.getQuantity();

        OrderItemEntity savedOrderItem = orderItemService.getOrderItemById(orderItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de pedido não encontrado!!"));

        savedOrderItem.setId(orderItemId);
        savedOrderItem.setOrder(order);
        savedOrderItem.setTotalValue(totalValue);

        OrderItemEntity orderItemSaved = orderItemService.saveOrderItem(savedOrderItem);

        List<OrderItemEntity> itemsList = orderItemService.getOrdersItem(orderId);
        List<OrderItemResultDTO> orderItemListDTOS = orderItemService.orderItemToDTO(itemsList);

        order.setTotalValue(calculateTotalValue(orderItemListDTOS, order.getPercentualDiscount()));

        orderService.saveOrder(order);

        return new OrderItemResultDTO(
                orderItemSaved.getId(),
                itemEntity.getId(),
                orderItemSaved.getQuantity(),
                orderItemSaved.getTotalValue());
    }

    @PutMapping("/{orderId}/close")
    public OrderClosedDTO closeOrder(@PathVariable UUID orderId, @RequestBody OrderEntity newOrderPercentual){

        OrderEntity order = orderService.getOrderById(orderId).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado!!"));

        List<OrderItemEntity> itemsList = orderItemService.getOrdersItem(orderId);
        List<OrderItemResultDTO> orderItemListDTOS = orderItemService.orderItemToDTO(itemsList);

        Double newPercentualDiscount = newOrderPercentual.getPercentualDiscount();

        if(itemsList.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum item cadastrado para esse pedido!!");
        }

        order.setPercentualDiscount(newPercentualDiscount);
        order.setTotalValue(calculateTotalValue(orderItemListDTOS, newPercentualDiscount));
        orderService.saveOrder(order);

        return new OrderClosedDTO(order, orderItemListDTOS);
    }

    @GetMapping("/{orderId}/close")
    public OrderClosedDTO closeOrder(@PathVariable UUID orderId){
        OrderEntity order = orderService.getOrderById(orderId).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado!!"));

        List<OrderItemEntity> itemsList = orderItemService.getOrdersItem(orderId);
        List<OrderItemResultDTO> orderItemListDTOS = orderItemService.orderItemToDTO(itemsList);

        return new OrderClosedDTO(order, orderItemListDTOS);
    }

    private Double calculateTotalValue(List<OrderItemResultDTO> itemsList, Double percentualDiscount) {
        Double orderTotalValue = 0.;

        for (OrderItemResultDTO orderItem : itemsList){

            UUID idItem = orderItem.itemId;
            ItemEntity itemEntity = itemService.getItemById(idItem).get();

            Double itemValue = itemEntity.getValue();

            if(itemEntity.getType().equals(ItemType.P) && percentualDiscount > 0){
                itemValue = (itemValue - (itemValue * percentualDiscount / 100)) * orderItem.quantity;
            } else {
                itemValue= itemValue * orderItem.quantity;
            }
            orderTotalValue += itemValue;
        }
        return orderTotalValue;
    }
}
