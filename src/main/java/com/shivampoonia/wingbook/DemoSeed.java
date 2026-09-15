package com.shivampoonia.wingbook;

import com.shivampoonia.wingbook.model.Member;
import com.shivampoonia.wingbook.model.Pod;
import com.shivampoonia.wingbook.model.PodKind;
import com.shivampoonia.wingbook.repository.MemberRepository;
import com.shivampoonia.wingbook.repository.PodRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DemoSeed implements CommandLineRunner {

    private final PodRepository pods;
    private final MemberRepository members;

    public DemoSeed(PodRepository pods, MemberRepository members) {
        this.pods = pods;
        this.members = members;
    }

    @Override
    public void run(String... args) {
        if (pods.count() == 0) {
            pods.save(pod("MW-FOCUS-01", "Deep Work Bubble", "Media Wing L2", 1, PodKind.FOCUS_POD));
            pods.save(pod("MW-EDIT-A", "Colour Grade Bay", "Post Suite", 2, PodKind.EDIT_BAY));
            pods.save(pod("MW-NOOK-7", "Standup Nook", "Innovation Deck", 4, PodKind.COLLAB_NOOK));
            pods.save(pod("MW-VR-02", "Headset Corner", "XR Lab", 2, PodKind.VR_CORNER));
        }
        if (members.count() == 0) {
            members.save(member("GH1061529", "Shivam", "Shivam2026.3@gisma-student.com", "MSc Management AI"));
            members.save(member("GH2044188", "Mira Okonkwo", "mira.okonkwo@student.gisma.edu", "MSc Digital"));
            members.save(member("GH1988331", "Jonas Keller", "jonas.keller@student.gisma.edu", "MSc Data"));
        }
    }

    private Pod pod(String tag, String label, String zone, int seats, PodKind kind) {
        Pod p = new Pod();
        p.setTag(tag);
        p.setLabel(label);
        p.setWingZone(zone);
        p.setSeats(seats);
        p.setKind(kind);
        p.setBookable(true);
        return p;
    }

    private Member member(String campusId, String name, String mail, String programme) {
        Member m = new Member();
        m.setCampusId(campusId);
        m.setDisplayName(name);
        m.setMail(mail);
        m.setProgramme(programme);
        return m;
    }
}
