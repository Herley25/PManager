package com.java360.pmanager.infrastructure.controller;

import com.java360.pmanager.domain.applicationService.MemberService;
import com.java360.pmanager.domain.entity.Member;
import com.java360.pmanager.infrastructure.dto.MemberDTO;
import com.java360.pmanager.infrastructure.dto.SaveMemberDataDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static com.java360.pmanager.infrastructure.controller.RestConstants.PATH_MEMBERS;

@RestController // Anotação para indicar que esta classe é um controlador REST
@RequestMapping(PATH_MEMBERS) // Anotação para mapear a URL base
@RequiredArgsConstructor
@SuppressWarnings("unused") // Suprime avisos de código não utilizado
public class MemberRestResource {

    private final MemberService memberService;

    @PostMapping // Mapeia o método para responder a requisições POST na URL /projects
    public ResponseEntity<MemberDTO> createMember(@RequestBody @Valid SaveMemberDataDTO saveMemberDataDTO) {
        Member member = memberService.createMember(saveMemberDataDTO);

        return ResponseEntity // Cria uma resposta HTTP
                .created(URI.create(PATH_MEMBERS + "/" + member.getId()))
                .body(MemberDTO.create(member)); // Retorna o membro criado como DTO
    }

    @GetMapping("/{id}") // Mapeia o método para responder a requisições GET na URL /members/{id}
    public ResponseEntity<MemberDTO> loadMember(@PathVariable("id") String memberId) {

        UUID uuid = UUID.fromString(memberId); // Conversão segura do ID de String para UUID
        Member member = memberService.loadMemberById(uuid); // Carrega o membro pelo ID
        return ResponseEntity.ok(MemberDTO.create(member)); // Retorna o membro como DTO
    }

    @DeleteMapping("/{id}") // Mapeia o método para responder a requisições DELETE na URL /members/{id}
    public ResponseEntity<Void> deleteMember(@PathVariable("id") String memberId) {
        UUID uuid = UUID.fromString(memberId); // Conversão segura do ID de String para UUID
        memberService.deleteMember(uuid); // Deleta o membro pelo ID
        return ResponseEntity.noContent().build(); // Retorna uma resposta 204 No Content
    }

    @PutMapping("/{id}") // Mapeia o método para responder a requisições PUT na URL /members/{id}
    public ResponseEntity<MemberDTO> updateMember(
            @PathVariable("id") String memberId,
            @RequestBody @Valid SaveMemberDataDTO saveMemberDataDTO) { // Recebe os dados do membro a serem atualizados
        UUID uuid = UUID.fromString(memberId); // Conversão segura do ID de String para UUID
        Member member = memberService.updateMember(uuid, saveMemberDataDTO); // Atualiza o membro
        return ResponseEntity.ok(MemberDTO.create(member)); // Retorna o membro atualizado como DTO
    }

    // -> GET .../members
    // -> GET .../members?email={email}
    @GetMapping // Mapeia o método para responder a requisições GET na URL /members?email={email}
    public ResponseEntity<List<MemberDTO>> findMembers(
            @RequestParam(value = "email", required = false) String email) {
        List<Member> members = memberService.findMembers(email); // Busca membros pelo email, se fornecido
        List<MemberDTO> memberDTOs = members.stream()
                .map(MemberDTO::create) // Converte cada membro para DTO
                .toList(); // Coleta os DTOs em uma lista
        return ResponseEntity.ok(memberDTOs); // Retorna a lista de membros como DTOs
    }
}
