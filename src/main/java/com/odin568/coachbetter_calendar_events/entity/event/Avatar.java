package com.odin568.coachbetter_calendar_events.entity.event;

import com.fasterxml.jackson.annotation.JsonProperty;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
public class Avatar{
    @JsonProperty("full_url")
    public String getFull_url() {
        return this.full_url; }
    public void setFull_url(String full_url) {
        this.full_url = full_url; }
    String full_url;
}
