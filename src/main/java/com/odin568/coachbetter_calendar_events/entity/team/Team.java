package com.odin568.coachbetter_calendar_events.entity.team;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;

public class Team{
    @JsonProperty("data")
    public ArrayList<Datum> getData() {
        return this.data; }
    public void setData(ArrayList<Datum> data) {
        this.data = data; }
    ArrayList<Datum> data;
}




