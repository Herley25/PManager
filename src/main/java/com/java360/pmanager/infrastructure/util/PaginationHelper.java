package com.java360.pmanager.infrastructure.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public final class PaginationHelper {

    private static final int DEFAULT_PAGE_SIZE = 10;

    private PaginationHelper() {
    }

    public static Pageable createPageable(
            Integer page,
            Integer pageSize,
            String direction,
            List<String> properties
    ) {
        return PageRequest.of(
                Optional.ofNullable(page).orElse(0),
                Optional.ofNullable(pageSize).orElse(DEFAULT_PAGE_SIZE),
                createSort(direction, properties)
        );
    }

    private static Sort createSort(String direction, List<String> properties) {
        if (properties.isEmpty()) { // Se a lista de propriedades estiver vazia, retorna Sort.unsorted()
            return Sort.unsorted();
        }

        // Converte a string de direção para o enum Sort.Direction, padrão é DESC
        Sort.Direction sortDirection = Sort.Direction.fromOptionalString(direction)
                .orElse(Sort.Direction.ASC);

        return Sort.by(sortDirection, properties.toArray(new String[0]));
    }
}
