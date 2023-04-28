package com.godev.testepratico.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotNull(message = "Campo numero deve ser preenchido!")
    private Integer number;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "Campo data deve ser preenchido!")
    private Date date;
    @NotNull(message = "Campo precentual de desconto deve ser preenchido!")
    private Double percentualDiscount;
    private Double totalValue;

    public OrderEntity(Integer number, Date date, Double percentualDiscount, Double totalValue) {
        this.number = number;
        this.date = date;
        this.percentualDiscount = percentualDiscount;
        this.totalValue = totalValue;
    }
}
