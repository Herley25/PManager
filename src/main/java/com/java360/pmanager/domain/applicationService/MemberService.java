package com.java360.pmanager.domain.applicationService;

import com.java360.pmanager.domain.entity.Member;
import com.java360.pmanager.domain.exception.DuplicateMemberException;
import com.java360.pmanager.domain.exception.MemberNotFoundException;
import com.java360.pmanager.domain.repository.MemberRepository;
import com.java360.pmanager.infrastructure.dto.SaveMemberDataDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor // Anotação Lombok para gerar um construtor com todos os campos finais
public class MemberService {

    private final MemberRepository memberRepository; // Repositório para acessar os dados do membro

    @Transactional // Anotação para indicar que este método deve ser executado dentro de uma transação
    public Member createMember(SaveMemberDataDTO saveMemberDataDTO) {
        // Verifica se já existe um membro com o mesmo email, lançando uma exceção se existir
        if (existsMemberWithEmail(saveMemberDataDTO.getEmail(), null)) {
            throw new DuplicateMemberException(saveMemberDataDTO.getEmail());
        }

        // Cria um novo membro com os dados fornecidos
        Member member = Member
                .builder()
                .name(saveMemberDataDTO.getName())
                .email(saveMemberDataDTO.getEmail())
                .secret(UUID.randomUUID().toString())
                .deleted(false)
                .build();

        memberRepository.save(member);
        // Retorna o membro criado
        return member;
    }

    public Member loadMemberById(UUID memberId) {
        // Carrega o membro pelo ID, considerando se ele foi deletado ou não
        return memberRepository
                .findById(memberId) // Busca o membro pelo ID e verifica se ele não foi deletado
                .orElseThrow(() -> new MemberNotFoundException(memberId)); // Lança uma exceção se o membro não for encontrado
    }

    @Transactional // Anotação para indicar que este método deve ser executado dentro de uma transação
    public void deleteMember(UUID memberId) {
        Member member = loadMemberById(memberId); // Carrega o membro pelo ID, lançando uma exceção se não for encontrado
        member.setDeleted(true); // Marca o membro como deletado, alterando o campo 'deleted' para true

        memberRepository.save(member); // Salva as alterações no repositório
    }

    @Transactional
    public Member updateMember(UUID memberId, SaveMemberDataDTO saveMemberDataDTO) {
        // Verifica se já existe um membro com o mesmo email, ignorando o membro atual (se houver)
        if (existsMemberWithEmail(saveMemberDataDTO.getEmail(), memberId)) {
            throw new DuplicateMemberException(saveMemberDataDTO.getEmail());
        }


        Member member = loadMemberById(memberId); // Carrega o membro pelo ID
        member.setName(saveMemberDataDTO.getName()); // Atualiza o nome do membro
        member.setEmail(saveMemberDataDTO.getEmail()); // Atualiza o email do membro

        memberRepository.save(member); // Salva as alterações no repositório
        return member;
    }

    public List<Member> findMembers(String email) {
        List<Member> members;

        if (Objects.isNull(email)) {
            // Se o email não for fornecido, busca todos os membros que não foram deletados
            members = memberRepository.findAllNotDeleted();
        } else {
            // Se o email for fornecido, busca membros com o email especificado que não foram deletados
            members = memberRepository
                    .findByEmailAndDeleted(email, false)
                    .map(List::of) // Converte o Optional para uma lista, retornando uma lista vazia se não houver membros encontrados
                    .orElse(List.of()); // Retorna uma lista vazia se não houver membros encontrados
        }

        return members; // Retorna a lista de membros encontrados
    }

    private boolean existsMemberWithEmail(String email, UUID idToExclude) {
        // Verifica se já existe um membro com o mesmo email, ignorando o membro atual (se houver)
        return memberRepository
                .findByEmailAndDeleted(email, false)
                .filter(m -> !Objects.equals(m.getId(), idToExclude)) // Verifica se o ID do membro encontrado é diferente do ID a ser excluído
                .isPresent();  // Retorna true se um membro com o mesmo email for encontrado, false caso contrário
    }
}
