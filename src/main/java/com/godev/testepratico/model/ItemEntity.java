package com.godev.testepratico.model;

import com.godev.testepratico.enums.ItemType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    private String description;
    private Double value;
    @Enumerated(EnumType.STRING)
    private ItemType type;

    public ItemEntity(String description, Double value, ItemType type) {
        this.description = description;
        this.value = value;
        this.type = type;
    }
}
