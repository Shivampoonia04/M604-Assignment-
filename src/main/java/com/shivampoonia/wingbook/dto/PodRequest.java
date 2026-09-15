package com.shivampoonia.wingbook.dto;

import com.shivampoonia.wingbook.model.PodKind;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PodRequest {
    @NotBlank @Size(max = 40) private String tag;
    @NotBlank @Size(max = 120) private String label;
    @NotBlank @Size(max = 60) private String wingZone;
    @Min(1) private int seats;
    @NotNull private PodKind kind;
    private Boolean bookable = true;

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
    public Boolean getBookable() { return bookable; }
    public void setBookable(Boolean bookable) { this.bookable = bookable; }
}
