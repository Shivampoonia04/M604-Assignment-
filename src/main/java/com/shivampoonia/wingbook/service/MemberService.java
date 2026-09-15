package com.shivampoonia.wingbook.service;

import com.shivampoonia.wingbook.dto.MemberRequest;
import com.shivampoonia.wingbook.dto.Views.MemberView;
import com.shivampoonia.wingbook.exception.NotFoundException;
import com.shivampoonia.wingbook.exception.RuleBreachException;
import com.shivampoonia.wingbook.model.Member;
import com.shivampoonia.wingbook.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberService {

    private final MemberRepository members;

    public MemberService(MemberRepository members) {
        this.members = members;
    }

    @Transactional(readOnly = true)
    public List<MemberView> list() {
        return members.findAll().stream().map(MemberView::of).toList();
    }

    @Transactional(readOnly = true)
    public MemberView one(Long id) {
        return MemberView.of(entity(id));
    }

    public MemberView add(MemberRequest req) {
        if (members.existsByCampusId(req.getCampusId())) {
            throw new RuleBreachException("Campus id already registered");
        }
        if (members.existsByMail(req.getMail())) {
            throw new RuleBreachException("Mail already registered");
        }
        return MemberView.of(members.save(fill(new Member(), req)));
    }

    public MemberView edit(Long id, MemberRequest req) {
        Member member = entity(id);
        members.findByCampusId(req.getCampusId()).ifPresent(other -> {
            if (!other.getId().equals(id)) {
                throw new RuleBreachException("Campus id already registered");
            }
        });
        if (!member.getMail().equalsIgnoreCase(req.getMail()) && members.existsByMail(req.getMail())) {
            throw new RuleBreachException("Mail already registered");
        }
        return MemberView.of(members.save(fill(member, req)));
    }

    public void remove(Long id) {
        members.delete(entity(id));
    }

    public Member entity(Long id) {
        return members.findById(id).orElseThrow(() -> new NotFoundException("No member with id " + id));
    }

    private Member fill(Member member, MemberRequest req) {
        member.setCampusId(req.getCampusId().trim().toUpperCase());
        member.setDisplayName(req.getDisplayName().trim());
        member.setMail(req.getMail().trim().toLowerCase());
        if (req.getProgramme() != null && !req.getProgramme().isBlank()) {
            member.setProgramme(req.getProgramme().trim());
        }
        return member;
    }
}
