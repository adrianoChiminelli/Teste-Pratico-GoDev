package com.godev.testepratico.model;

import com.godev.testepratico.enums.ItemType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "item")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotEmpty(message = "Campo descrição deve ser preenchido!")
    private String description;
    @NotNull(message = "Campo valor deve ser preenchido!")
    private Double value;
    @Enumerated(EnumType.STRING)
    @NotEmpty(message = "Campo Tipo deve ser preenchido!")
    private ItemType type;

    public ItemEntity(String description, Double value, ItemType type) {
        this.description = description;
        this.value = value;
        this.type = type;
    }
}
