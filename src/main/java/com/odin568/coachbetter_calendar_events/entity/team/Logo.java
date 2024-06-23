package com.odin568.coachbetter_calendar_events.entity.team;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Logo{
    @JsonProperty("uuid")
    public String getUuid() {
        return this.uuid; }
    public void setUuid(String uuid) {
        this.uuid = uuid; }
    String uuid;
    @JsonProperty("full_url")
    public String getFull_url() {
        return this.full_url; }
    public void setFull_url(String full_url) {
        this.full_url = full_url; }
    String full_url;
    @JsonProperty("relationships")
    public ArrayList<Object> getRelationships() {
        return this.relationships; }
    public void setRelationships(ArrayList<Object> relationships) {
        this.relationships = relationships; }
    ArrayList<Object> relationships;
    @JsonProperty("can")
    public ArrayList<Object> getCan() {
        return this.can; }
    public void setCan(ArrayList<Object> can) {
        this.can = can; }
    ArrayList<Object> can;
}
