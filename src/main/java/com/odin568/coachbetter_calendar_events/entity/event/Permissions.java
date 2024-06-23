package com.odin568.coachbetter_calendar_events.entity.event;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Permissions{
    @JsonProperty("update-availability")
    public boolean getUpdate_availability() {
        return this.update_availability; }
    public void setUpdate_availability(boolean update_availability) {
        this.update_availability = update_availability; }
    boolean update_availability;
}
