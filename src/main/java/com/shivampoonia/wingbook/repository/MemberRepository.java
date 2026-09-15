package com.shivampoonia.wingbook.repository;

import com.shivampoonia.wingbook.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByCampusId(String campusId);
    boolean existsByMail(String mail);
    Optional<Member> findByCampusId(String campusId);
}
