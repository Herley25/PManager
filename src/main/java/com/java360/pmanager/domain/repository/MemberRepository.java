package com.java360.pmanager.domain.repository;

import com.java360.pmanager.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {

    Optional<Member> findByIdAndDeleted(UUID id, boolean deleted);

    Optional<Member> findByEmailAndDeleted(String email, boolean deleted);

    default List<Member> findAllNotDeleted() { // Metodo para buscar todos os membros que não estão deletados
        return findAll().stream()
                .filter(member -> !member.getDeleted())
                .toList();
    }


}
