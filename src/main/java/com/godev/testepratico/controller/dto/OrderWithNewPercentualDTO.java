package com.godev.testepratico.controller.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class OrderWithNewPercentualDTO {

    @NotEmpty(message = "Campo pedido deve conter o Id do pedido!")
    public UUID order;
    @NotNull(message = "Campo percentual de desconto deve ser preenchido!")
    public Double percentualDiscount;
}
