package com.godev.testepratico.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.godev.testepratico.model.OrderEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderClosedDTO {

    private UUID id;
    private Integer number;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date date;
    private Double percentualDiscount;
    private Double totalValue;
    private List<OrderItemResultDTO> items;

    public OrderClosedDTO(OrderEntity order, List<OrderItemResultDTO> items) {
        this.id = order.getId();
        this.number = order.getNumber();
        this.date = order.getDate();
        this.percentualDiscount = order.getPercentualDiscount();
        this.totalValue = order.getTotalValue();
        this.items = items;
    }
}
