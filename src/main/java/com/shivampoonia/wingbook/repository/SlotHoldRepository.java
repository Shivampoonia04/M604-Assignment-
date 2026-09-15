package com.shivampoonia.wingbook.repository;

import com.shivampoonia.wingbook.model.SlotHold;
import com.shivampoonia.wingbook.model.SlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SlotHoldRepository extends JpaRepository<SlotHold, Long> {

    List<SlotHold> findByPodId(Long podId);

    List<SlotHold> findByMemberId(Long memberId);

    long countByMemberIdAndStatus(Long memberId, SlotStatus status);

    @Query("""
            SELECT COUNT(s) > 0 FROM SlotHold s
            WHERE s.pod.id = :podId
              AND s.status = :status
              AND s.beginsAt < :endsAt
              AND s.endsAt > :beginsAt
              AND (:skipId IS NULL OR s.id <> :skipId)
            """)
    boolean hasClash(
            @Param("podId") Long podId,
            @Param("beginsAt") LocalDateTime beginsAt,
            @Param("endsAt") LocalDateTime endsAt,
            @Param("status") SlotStatus status,
            @Param("skipId") Long skipId
    );
}
