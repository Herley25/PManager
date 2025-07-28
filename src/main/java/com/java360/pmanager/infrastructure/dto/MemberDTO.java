package com.java360.pmanager.infrastructure.dto;

import com.java360.pmanager.domain.entity.Member;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toSet;

@Data
public class MemberDTO {

    private final UUID id;

    private final String secret;

    private final String name;

    private final String email;

    private final Set<String> projectIds;

    public static MemberDTO create(Member member) {
        return new MemberDTO(
                member.getId(),
                member.getSecret(),
                member.getName(),
                member.getEmail(),
                Optional.ofNullable(member.getProjects()) // Verifica se a lista de projetos é nula
                        .orElse(List.of())
                        .stream() // Verifica se a lista de projetos é nula e, se for, usa uma lista vazia
                        .map(project -> project.getId().toString()) // Converter o UUID para String
                        .collect(toSet()) // Coletar os IDs dos projetos em um Set de Strings
        );
    }
}
