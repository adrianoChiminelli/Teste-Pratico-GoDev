package com.godev.testepratico.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    @NotEmpty(message = "Campo Id do item deve ser preenchido!")
    private UUID itemId;
    @NotNull(message = "Campo quantidade deve ser preenchido!")
    private Double quantity;

}
