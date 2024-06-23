package com.odin568.coachbetter_calendar_events.entity.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class MatchPlayer{
    @JsonProperty("uuid")
    public String getUuid() {
        return this.uuid; }
    public void setUuid(String uuid) {
        this.uuid = uuid; }
    String uuid;
    @JsonProperty("in_roster")
    public String getIn_roster() {
        return this.in_roster; }
    public void setIn_roster(String in_roster) {
        this.in_roster = in_roster; }
    String in_roster;
    @JsonProperty("reason")
    public String getReason() {
        return this.reason; }
    public void setReason(String reason) {
        this.reason = reason; }
    String reason;
    @JsonProperty("relationships")
    public Relationships getRelationships() {
        return this.relationships; }
    public void setRelationships(Relationships relationships) {
        this.relationships = relationships; }
    Relationships relationships;
    @JsonProperty("can")
    public ArrayList<String> getCan() {
        return this.can; }
    public void setCan(ArrayList<String> can) {
        this.can = can; }
    ArrayList<String> can;
}
