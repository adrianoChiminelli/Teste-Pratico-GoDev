package com.godev.testepratico.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "order_item")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    private Double quantity;
    private Double totalValue;

    public OrderItemEntity(OrderEntity order, ItemEntity item, Double quantity, Double totalValue) {
        this.order = order;
        this.item = item;
        this.quantity = quantity;
        this.totalValue = totalValue;
    }
}
