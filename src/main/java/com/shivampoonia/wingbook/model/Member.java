package com.shivampoonia.wingbook.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 40)
    private String campusId;

    @Column(nullable = false, length = 120)
    private String displayName;

    @Column(nullable = false, unique = true, length = 160)
    private String mail;

    @Column(nullable = false, length = 40)
    private String programme = "MSc AI";

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCampusId() { return campusId; }
    public void setCampusId(String campusId) { this.campusId = campusId; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }
    public String getProgramme() { return programme; }
    public void setProgramme(String programme) { this.programme = programme; }
}
