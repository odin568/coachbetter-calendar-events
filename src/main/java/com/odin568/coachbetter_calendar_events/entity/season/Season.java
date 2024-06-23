package com.odin568.coachbetter_calendar_events.entity.season;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Season{
    @JsonProperty("data")
    public ArrayList<Datum> getData() {
        return this.data; }
    public void setData(ArrayList<Datum> data) {
        this.data = data; }
    ArrayList<Datum> data;
}


