package com.shivampoonia.wingbook.repository;

import com.shivampoonia.wingbook.model.Pod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PodRepository extends JpaRepository<Pod, Long> {
    boolean existsByTag(String tag);
    Optional<Pod> findByTag(String tag);
}
