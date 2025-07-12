package com.java360.pmanager.infrastructure.exception;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data // Anotação do Lombok para gerar getters, setters, toString, equals e hashCode
@Builder // Anotação do Lombok para permitir o uso do padrão Builder na criação de instâncias
public class RestError {

    private final String errorCode;
    private final String errorMessage;
    private final List<String> details;
    private final int status;
    private final String path;
}
