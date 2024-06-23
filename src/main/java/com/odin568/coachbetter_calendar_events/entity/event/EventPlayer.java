package com.odin568.coachbetter_calendar_events.entity.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class EventPlayer{
    @JsonProperty("uuid")
    public String getUuid() {
        return this.uuid; }
    public void setUuid(String uuid) {
        this.uuid = uuid; }
    String uuid;
    @JsonProperty("availability")
    public String getAvailability() {
        return this.availability; }
    public void setAvailability(String availability) {
        this.availability = availability; }
    String availability;
    @JsonProperty("reason")
    public Object getReason() {
        return this.reason; }
    public void setReason(Object reason) {
        this.reason = reason; }
    Object reason;
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
