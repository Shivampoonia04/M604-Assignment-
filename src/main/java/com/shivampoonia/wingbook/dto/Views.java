package com.shivampoonia.wingbook.dto;

import com.shivampoonia.wingbook.model.Member;
import com.shivampoonia.wingbook.model.Pod;
import com.shivampoonia.wingbook.model.PodKind;
import com.shivampoonia.wingbook.model.SlotHold;
import com.shivampoonia.wingbook.model.SlotStatus;

import java.time.LocalDateTime;

public final class Views {
    private Views() {}

    public record PodView(Long id, String tag, String label, String wingZone, int seats, PodKind kind, boolean bookable) {
        public static PodView of(Pod p) {
            return new PodView(p.getId(), p.getTag(), p.getLabel(), p.getWingZone(), p.getSeats(), p.getKind(), p.isBookable());
        }
    }

    public record MemberView(Long id, String campusId, String displayName, String mail, String programme) {
        public static MemberView of(Member m) {
            return new MemberView(m.getId(), m.getCampusId(), m.getDisplayName(), m.getMail(), m.getProgramme());
        }
    }

    public record SlotView(
            Long id, Long podId, String podTag, Long memberId, String campusId,
            LocalDateTime beginsAt, LocalDateTime endsAt, String intent, String doorPin, SlotStatus status
    ) {
        public static SlotView of(SlotHold s) {
            return new SlotView(
                    s.getId(), s.getPod().getId(), s.getPod().getTag(),
                    s.getMember().getId(), s.getMember().getCampusId(),
                    s.getBeginsAt(), s.getEndsAt(), s.getIntent(), s.getDoorPin(), s.getStatus()
            );
        }
    }

    public record FreeCheck(Long podId, LocalDateTime beginsAt, LocalDateTime endsAt, boolean free, String note) {}
}
