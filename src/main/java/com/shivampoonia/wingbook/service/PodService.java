package com.shivampoonia.wingbook.service;

import com.shivampoonia.wingbook.dto.PodRequest;
import com.shivampoonia.wingbook.dto.Views.PodView;
import com.shivampoonia.wingbook.exception.NotFoundException;
import com.shivampoonia.wingbook.exception.RuleBreachException;
import com.shivampoonia.wingbook.model.Pod;
import com.shivampoonia.wingbook.repository.PodRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PodService {

    private final PodRepository pods;

    public PodService(PodRepository pods) {
        this.pods = pods;
    }

    @Transactional(readOnly = true)
    public List<PodView> list() {
        return pods.findAll().stream().map(PodView::of).toList();
    }

    @Transactional(readOnly = true)
    public PodView one(Long id) {
        return PodView.of(entity(id));
    }

    public PodView add(PodRequest req) {
        if (pods.existsByTag(req.getTag())) {
            throw new RuleBreachException("Pod tag already taken: " + req.getTag());
        }
        return PodView.of(pods.save(fill(new Pod(), req)));
    }

    public PodView edit(Long id, PodRequest req) {
        Pod pod = entity(id);
        pods.findByTag(req.getTag()).ifPresent(other -> {
            if (!other.getId().equals(id)) {
                throw new RuleBreachException("Pod tag already taken: " + req.getTag());
            }
        });
        return PodView.of(pods.save(fill(pod, req)));
    }

    public void remove(Long id) {
        pods.delete(entity(id));
    }

    public Pod entity(Long id) {
        return pods.findById(id).orElseThrow(() -> new NotFoundException("No pod with id " + id));
    }

    private Pod fill(Pod pod, PodRequest req) {
        pod.setTag(req.getTag().trim().toUpperCase());
        pod.setLabel(req.getLabel().trim());
        pod.setWingZone(req.getWingZone().trim());
        pod.setSeats(req.getSeats());
        pod.setKind(req.getKind());
        pod.setBookable(req.getBookable() == null || req.getBookable());
        return pod;
    }
}
