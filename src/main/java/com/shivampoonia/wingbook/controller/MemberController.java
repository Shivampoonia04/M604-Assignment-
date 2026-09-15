package com.shivampoonia.wingbook.controller;

import com.shivampoonia.wingbook.dto.MemberRequest;
import com.shivampoonia.wingbook.dto.Views.MemberView;
import com.shivampoonia.wingbook.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public List<MemberView> list() { return memberService.list(); }

    @GetMapping("/{id}")
    public MemberView one(@PathVariable Long id) { return memberService.one(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberView add(@Valid @RequestBody MemberRequest request) { return memberService.add(request); }

    @PutMapping("/{id}")
    public MemberView edit(@PathVariable Long id, @Valid @RequestBody MemberRequest request) {
        return memberService.edit(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) { memberService.remove(id); }
}
