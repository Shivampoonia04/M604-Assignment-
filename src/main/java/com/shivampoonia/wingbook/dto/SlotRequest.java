package com.shivampoonia.wingbook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class SlotRequest {
    @NotNull private Long podId;
    @NotNull private Long memberId;
    @NotNull private LocalDateTime beginsAt;
    @NotNull private LocalDateTime endsAt;
    @NotBlank @Size(max = 200) private String intent;

    public Long getPodId() { return podId; }
    public void setPodId(Long podId) { this.podId = podId; }
    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }
    public LocalDateTime getBeginsAt() { return beginsAt; }
    public void setBeginsAt(LocalDateTime beginsAt) { this.beginsAt = beginsAt; }
    public LocalDateTime getEndsAt() { return endsAt; }
    public void setEndsAt(LocalDateTime endsAt) { this.endsAt = endsAt; }
    public String getIntent() { return intent; }
    public void setIntent(String intent) { this.intent = intent; }
}
