package com.shivampoonia.wingbook.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pods")
public class Pod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 40)
    private String tag;

    @Column(nullable = false, length = 120)
    private String label;

    @Column(nullable = false, length = 60)
    private String wingZone;

    @Column(nullable = false)
    private int seats;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PodKind kind;

    @Column(nullable = false)
    private boolean bookable = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public String getWingZone() { return wingZone; }
    public void setWingZone(String wingZone) { this.wingZone = wingZone; }
    public int getSeats() { return seats; }
    public void setSeats(int seats) { this.seats = seats; }
    public PodKind getKind() { return kind; }
    public void setKind(PodKind kind) { this.kind = kind; }
    public boolean isBookable() { return bookable; }
    public void setBookable(boolean bookable) { this.bookable = bookable; }
}
