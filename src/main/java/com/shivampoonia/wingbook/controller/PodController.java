package com.shivampoonia.wingbook.controller;

import com.shivampoonia.wingbook.dto.PodRequest;
import com.shivampoonia.wingbook.dto.Views.PodView;
import com.shivampoonia.wingbook.service.PodService;
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
@RequestMapping("/api/v1/pods")
public class PodController {

    private final PodService podService;

    public PodController(PodService podService) {
        this.podService = podService;
    }

    @GetMapping
    public List<PodView> list() { return podService.list(); }

    @GetMapping("/{id}")
    public PodView one(@PathVariable Long id) { return podService.one(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PodView add(@Valid @RequestBody PodRequest request) { return podService.add(request); }

    @PutMapping("/{id}")
    public PodView edit(@PathVariable Long id, @Valid @RequestBody PodRequest request) {
        return podService.edit(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) { podService.remove(id); }
}
