package com.godev.testepratico.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ItemType {
    P("Produto"),
    S("Serviço");

    public final String description;
}
