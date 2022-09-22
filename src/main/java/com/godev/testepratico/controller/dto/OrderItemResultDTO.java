package com.godev.testepratico.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResultDTO {

    public UUID id;
    public UUID itemId;
    public Double quantity;
    public Double totalValue;

}
