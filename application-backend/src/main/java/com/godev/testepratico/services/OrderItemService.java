package com.godev.testepratico.services;

import com.godev.testepratico.controller.dto.OrderClosedDTO;
import com.godev.testepratico.controller.dto.OrderItemDTO;
import com.godev.testepratico.controller.dto.OrderItemResultDTO;
import com.godev.testepratico.controller.dto.OrderWithNewPercentualDTO;
import com.godev.testepratico.enums.ItemType;
import com.godev.testepratico.exception.ItemNotFoundException;
import com.godev.testepratico.exception.NoOrderItemFoundException;
import com.godev.testepratico.exception.OrderItemNotFoundException;
import com.godev.testepratico.exception.OrderNotFoundException;
import com.godev.testepratico.model.ItemEntity;
import com.godev.testepratico.model.OrderEntity;
import com.godev.testepratico.model.OrderItemEntity;
import com.godev.testepratico.repositories.ItemRepository;
import com.godev.testepratico.repositories.OrderItemRepository;
import com.godev.testepratico.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public OrderItemResultDTO saveOrderItem(UUID orderId, OrderItemDTO orderItemDTO){

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(()-> new OrderNotFoundException("Pedido não foi encontrado!!!"));

        UUID idItem = orderItemDTO.getItemId();
        ItemEntity itemEntity = itemRepository.findById(idItem)
                .orElseThrow(() -> new ItemNotFoundException("Item não foi encontrado!!!"));

        Double percentualDiscount = order.getPercentualDiscount();
        Double Value = itemEntity.getValue() * orderItemDTO.getQuantity();

        OrderItemEntity orderItem = new OrderItemEntity(order, itemEntity, orderItemDTO.getQuantity(), 0D);
        orderItem.setOrder(order);
        orderItem.setTotalValue(Value);

        List<OrderItemEntity> itemsList = orderItemRepository.findByOrderId(orderId);
        itemsList.add(orderItem);

        List<OrderItemResultDTO> orderItemResultDTOList = orderItemListToDTO(itemsList);

        order.setTotalValue(calculateTotalValue(orderItemResultDTOList, percentualDiscount));

        orderRepository.save(order);

        OrderItemEntity savedOrderItem =  orderItemRepository.save(orderItem);

        return new OrderItemResultDTO(
                savedOrderItem.getId(),
                savedOrderItem.getItem().getId(),
                savedOrderItem.getQuantity(),
                savedOrderItem.getTotalValue());
    }

    public OrderItemResultDTO getOrderItemById(UUID id){
        return orderItemRepository.findById(id)
                .map(orderItem -> {
                    return new OrderItemResultDTO(orderItem.getId(),
                            orderItem.getItem().getId(),
                            orderItem.getQuantity(),
                            orderItem.getTotalValue());
                })
                .orElseThrow(() -> new OrderItemNotFoundException("Item de pedido não encontrado!!"));
    }

    @Transactional
    public void deleteOrderItem(UUID orderItemId, UUID orderId){
        orderRepository.findById(orderId).ifPresentOrElse(order -> {
                    orderItemRepository.deleteById(orderItemId);

                    List<OrderItemResultDTO> itemsList = orderItemListToDTO(this.getOrdersItem(orderId));
                    order.setTotalValue(calculateTotalValue(itemsList, order.getPercentualDiscount()));
                    orderRepository.save(order);
                }, () -> new OrderItemNotFoundException("Item de pedido não encontrado!!"));
    }

    public List<OrderItemEntity> getOrdersItem(UUID orderId){
        return orderItemRepository.findByOrderId(orderId);
    }

    @Transactional
    public OrderItemResultDTO updateOrderItem(UUID orderId, UUID orderItemId, OrderItemDTO orderItemDTO){

        return orderItemRepository.findById(orderItemId)
                .map(orderItem ->{
                    ItemEntity itemEntity = itemRepository.findById(orderItemDTO.getItemId())
                            .orElseThrow(() -> new ItemNotFoundException("Item não foi encontrado!!!"));

                    OrderEntity order = orderRepository.findById(orderId)
                            .orElseThrow(()-> new OrderItemNotFoundException("Item de pedido não encontrado!!"));

                    orderItem.setId(orderItemId);
                    orderItem.setOrder(order);
                    orderItem.setTotalValue(itemEntity.getValue() * orderItemDTO.getQuantity());

                    OrderItemEntity orderItemSaved = orderItemRepository.save(orderItem);

                    List<OrderItemResultDTO> orderItemListDTOS = orderItemListToDTO(this.getOrdersItem(orderId));

                    order.setTotalValue(calculateTotalValue(orderItemListDTOS, order.getPercentualDiscount()));
                    orderRepository.save(order);

                    return new OrderItemResultDTO(
                            orderItemSaved.getId(),
                            itemEntity.getId(),
                            orderItemSaved.getQuantity(),
                            orderItemSaved.getTotalValue());
                })
                .orElseThrow(() -> new OrderItemNotFoundException("Item de pedido não encontrado!!"));
    }

    @Transactional
    public OrderClosedDTO closeOrderWithNewPercentual(UUID orderId, OrderWithNewPercentualDTO newOrderPercentual){
        return orderRepository.findById(orderId)
                .map(order -> {
                    List<OrderItemResultDTO> orderItemList = orderItemListToDTO(this.getOrdersItem(orderId));
                    if (orderItemList.isEmpty()){
                        throw new NoOrderItemFoundException("O Pedido não pode ser fechado sem nenhum item de pedido vinculado a ele!");
                    }

                    Double newPercentualDiscount = newOrderPercentual.percentualDiscount;

                    order.setPercentualDiscount(newPercentualDiscount);
                    order.setTotalValue(calculateTotalValue(orderItemList, newPercentualDiscount));
                    orderRepository.save(order);

                    return new OrderClosedDTO(order, orderItemList);
                })
                .orElseThrow(()-> new OrderItemNotFoundException("Item de pedido não encontrado!!"));
    }

    public OrderClosedDTO closeOrder(UUID orderId){
        return orderRepository.findById(orderId)
                .map(order -> {
                    List<OrderItemResultDTO> orderItemsList = orderItemListToDTO(this.getOrdersItem(orderId));
                    if (orderItemsList.isEmpty()){
                        throw new NoOrderItemFoundException("O Pedido não pode ser fechado sem nenhum item de pedido vinculado a ele!");
                    }
                    return new OrderClosedDTO(order, orderItemsList);
                })
                .orElseThrow(()-> new OrderItemNotFoundException("Item de pedido não encontrado!!"));
    }

    public List<OrderItemEntity> saveOrderItemBatch(List<OrderItemEntity> toSave) {
        return orderItemRepository.saveAll(toSave);
    }

    public List<OrderItemEntity> findByOrderId(UUID orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    private List<OrderItemResultDTO> orderItemListToDTO(List<OrderItemEntity> orderItemList) {
        return orderItemList
                .stream()
                .map(orderItem -> {
                    ItemEntity item = orderItem.getItem();

                    return new OrderItemResultDTO(orderItem.getId(),
                            item.getId(),
                            orderItem.getQuantity(),
                            orderItem.getTotalValue());
                }).collect(Collectors.toList());
    }

    private Double calculateTotalValue(List<OrderItemResultDTO> itemsList, Double percentualDiscount) {
        double orderTotalValue = 0.;

        for (OrderItemResultDTO orderItem : itemsList){

            UUID idItem = orderItem.itemId;
            ItemEntity itemEntity = itemRepository.findById(idItem).get();

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
