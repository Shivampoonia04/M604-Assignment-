package com.shivampoonia.wingbook.controller;

import com.shivampoonia.wingbook.dto.SlotRequest;
import com.shivampoonia.wingbook.dto.Views.FreeCheck;
import com.shivampoonia.wingbook.dto.Views.SlotView;
import com.shivampoonia.wingbook.service.SlotService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/slots")
public class SlotController {

    private final SlotService slotService;

    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    @GetMapping
    public List<SlotView> list() { return slotService.list(); }

    @GetMapping("/{id}")
    public SlotView one(@PathVariable Long id) { return slotService.one(id); }

    @GetMapping("/pod/{podId}")
    public List<SlotView> byPod(@PathVariable Long podId) { return slotService.byPod(podId); }

    @GetMapping("/member/{memberId}")
    public List<SlotView> byMember(@PathVariable Long memberId) { return slotService.byMember(memberId); }

    @GetMapping("/free")
    public FreeCheck free(
            @RequestParam Long podId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime beginsAt,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endsAt
    ) {
        return slotService.freeCheck(podId, beginsAt, endsAt);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SlotView hold(@Valid @RequestBody SlotRequest request) { return slotService.hold(request); }

    @PutMapping("/{id}")
    public SlotView rewrite(@PathVariable Long id, @Valid @RequestBody SlotRequest request) {
        return slotService.rewrite(id, request);
    }

    @PostMapping("/{id}/release")
    public SlotView release(@PathVariable Long id) { return slotService.release(id); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void erase(@PathVariable Long id) { slotService.erase(id); }
}
