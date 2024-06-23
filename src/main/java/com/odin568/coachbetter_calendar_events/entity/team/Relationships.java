package com.odin568.coachbetter_calendar_events.entity.team;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Relationships{
    @JsonProperty("logo")
    public Logo getLogo() {
        return this.logo; }
    public void setLogo(Logo logo) {
        this.logo = logo; }
    Logo logo;
}
