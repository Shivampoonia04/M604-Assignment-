package com.shivampoonia.wingbook.service;

import com.shivampoonia.wingbook.dto.SlotRequest;
import com.shivampoonia.wingbook.dto.Views.FreeCheck;
import com.shivampoonia.wingbook.dto.Views.SlotView;
import com.shivampoonia.wingbook.exception.NotFoundException;
import com.shivampoonia.wingbook.exception.RuleBreachException;
import com.shivampoonia.wingbook.model.Member;
import com.shivampoonia.wingbook.model.Pod;
import com.shivampoonia.wingbook.model.SlotHold;
import com.shivampoonia.wingbook.model.SlotStatus;
import com.shivampoonia.wingbook.repository.SlotHoldRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class SlotService {

    private final SlotHoldRepository slots;
    private final PodService podService;
    private final MemberService memberService;
    private final SecureRandom random = new SecureRandom();

    @Value("${wingbook.max-slot-minutes:90}")
    private int maxMinutes;

    @Value("${wingbook.quiet-hours-start:22}")
    private int quietStart;

    @Value("${wingbook.quiet-hours-end:7}")
    private int quietEnd;

    public SlotService(SlotHoldRepository slots, PodService podService, MemberService memberService) {
        this.slots = slots;
        this.podService = podService;
        this.memberService = memberService;
    }

    @Transactional(readOnly = true)
    public List<SlotView> list() {
        return slots.findAll().stream().map(SlotView::of).toList();
    }

    @Transactional(readOnly = true)
    public SlotView one(Long id) {
        return SlotView.of(entity(id));
    }

    @Transactional(readOnly = true)
    public List<SlotView> byPod(Long podId) {
        podService.entity(podId);
        return slots.findByPodId(podId).stream().map(SlotView::of).toList();
    }

    @Transactional(readOnly = true)
    public List<SlotView> byMember(Long memberId) {
        memberService.entity(memberId);
        return slots.findByMemberId(memberId).stream().map(SlotView::of).toList();
    }

    @Transactional(readOnly = true)
    public FreeCheck freeCheck(Long podId, LocalDateTime beginsAt, LocalDateTime endsAt) {
        guardWindow(beginsAt, endsAt);
        podService.entity(podId);
        boolean clash = slots.hasClash(podId, beginsAt, endsAt, SlotStatus.HELD, null);
        return new FreeCheck(podId, beginsAt, endsAt, !clash,
                clash ? "That pod is already held for this window" : "Pod is free for this window");
    }

    public SlotView hold(SlotRequest req) {
        guardWindow(req.getBeginsAt(), req.getEndsAt());

        Pod pod = podService.entity(req.getPodId());
        if (!pod.isBookable()) {
            throw new RuleBreachException("Pod " + pod.getTag() + " is offline for booking");
        }

        Member member = memberService.entity(req.getMemberId());
        if (slots.countByMemberIdAndStatus(member.getId(), SlotStatus.HELD) > 0) {
            throw new RuleBreachException("You already have an active hold — release it before taking another");
        }

        if (slots.hasClash(pod.getId(), req.getBeginsAt(), req.getEndsAt(), SlotStatus.HELD, null)) {
            throw new RuleBreachException("Clash with an existing hold on " + pod.getTag());
        }

        SlotHold hold = new SlotHold();
        hold.setPod(pod);
        hold.setMember(member);
        hold.setBeginsAt(req.getBeginsAt());
        hold.setEndsAt(req.getEndsAt());
        hold.setIntent(req.getIntent().trim());
        hold.setDoorPin(makePin());
        hold.setStatus(SlotStatus.HELD);
        return SlotView.of(slots.save(hold));
    }

    public SlotView rewrite(Long id, SlotRequest req) {
        SlotHold hold = entity(id);
        if (hold.getStatus() == SlotStatus.RELEASED) {
            throw new RuleBreachException("Released holds cannot be rewritten");
        }
        guardWindow(req.getBeginsAt(), req.getEndsAt());

        Pod pod = podService.entity(req.getPodId());
        if (!pod.isBookable()) {
            throw new RuleBreachException("Pod " + pod.getTag() + " is offline for booking");
        }
        Member member = memberService.entity(req.getMemberId());

        if (slots.hasClash(pod.getId(), req.getBeginsAt(), req.getEndsAt(), SlotStatus.HELD, hold.getId())) {
            throw new RuleBreachException("Clash with an existing hold on " + pod.getTag());
        }

        hold.setPod(pod);
        hold.setMember(member);
        hold.setBeginsAt(req.getBeginsAt());
        hold.setEndsAt(req.getEndsAt());
        hold.setIntent(req.getIntent().trim());
        return SlotView.of(slots.save(hold));
    }

    public SlotView release(Long id) {
        SlotHold hold = entity(id);
        if (hold.getStatus() == SlotStatus.RELEASED) {
            throw new RuleBreachException("Hold already released");
        }
        hold.setStatus(SlotStatus.RELEASED);
        return SlotView.of(slots.save(hold));
    }

    public void erase(Long id) {
        slots.delete(entity(id));
    }

    private SlotHold entity(Long id) {
        return slots.findById(id).orElseThrow(() -> new NotFoundException("No slot hold with id " + id));
    }

    private void guardWindow(LocalDateTime beginsAt, LocalDateTime endsAt) {
        if (beginsAt == null || endsAt == null) {
            throw new RuleBreachException("Begin and end times are required");
        }
        if (!endsAt.isAfter(beginsAt)) {
            throw new RuleBreachException("End must be after begin");
        }
        if (beginsAt.isBefore(LocalDateTime.now().minusMinutes(1))) {
            throw new RuleBreachException("Begin time must be in the future");
        }
        long minutes = Duration.between(beginsAt, endsAt).toMinutes();
        if (minutes > maxMinutes) {
            throw new RuleBreachException("Slot too long — max is " + maxMinutes + " minutes");
        }
        int hour = beginsAt.getHour();
        boolean inQuiet = quietStart > quietEnd
                ? (hour >= quietStart || hour < quietEnd)
                : (hour >= quietStart && hour < quietEnd);
        if (inQuiet) {
            throw new RuleBreachException("Quiet hours " + quietStart + ":00–" + quietEnd + ":00 — pick another start");
        }
    }

    private String makePin() {
        int n = 100000 + random.nextInt(900000);
        return Integer.toString(n);
    }
}
