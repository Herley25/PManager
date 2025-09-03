package com.java360.pmanager.infrastructure.util;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter // Gera os getters para os campos da classe
public class SortProperties {

    // Lista de propriedades permitidas para ordenação
    private static final List<String> ALLOWED_PROPERTIES = List.of(
            "title",
            "status",
            "numberOfDays"
    );

    private final List<String> sortPropertiesList;

    public SortProperties(String commaSeparatedString) {
        sortPropertiesList = Arrays
                .stream(commaSeparatedString.split(","))
                .filter(ALLOWED_PROPERTIES::contains)
                .toList();
    }
}
