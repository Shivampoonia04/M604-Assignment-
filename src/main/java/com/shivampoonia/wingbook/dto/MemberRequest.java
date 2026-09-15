package com.shivampoonia.wingbook.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MemberRequest {
    @NotBlank @Size(max = 40) private String campusId;
    @NotBlank @Size(max = 120) private String displayName;
    @NotBlank @Email @Size(max = 160) private String mail;
    @Size(max = 40) private String programme;

    public String getCampusId() { return campusId; }
    public void setCampusId(String campusId) { this.campusId = campusId; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }
    public String getProgramme() { return programme; }
    public void setProgramme(String programme) { this.programme = programme; }
}
