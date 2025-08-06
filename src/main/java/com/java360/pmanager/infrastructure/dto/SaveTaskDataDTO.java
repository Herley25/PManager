package com.java360.pmanager.infrastructure.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data // Anotação Lombok para gerar getters, setters, toString, equals e hashCode
public class SaveTaskDataDTO {
    @NotNull(message = "Title cannot be empty") // Validação para garantir que o título não seja nulo
    private final String title; // Título da tarefa

    @NotNull(message = "Description cannot be empty") // Validação para garantir que a descrição não seja nula
    @Size(min = 1, max = 150, message = "Invalid description length") // Validação para garantir que a descrição tenha um tamanho válido
    private final String description; // Descrição da tarefa

    @NotNull
    @Positive(message = "Number of days must be a positive integer") // Validação para garantir que o número de dias seja um inteiro positivo
    private final Integer numberOfDays; // Número de dias estimados para a tarefa

    private final String status; // Status da tarefa, representado como uma string (pode ser convertido para um enum posteriormente)
}
