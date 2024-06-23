package com.odin568.coachbetter_calendar_events.entity.event;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Player{
    @JsonProperty("uuid")
    public String getUuid() {
        return this.uuid; }
    public void setUuid(String uuid) {
        this.uuid = uuid; }
    String uuid;
    @JsonProperty("first_name")
    public String getFirst_name() {
        return this.first_name; }
    public void setFirst_name(String first_name) {
        this.first_name = first_name; }
    String first_name;
    @JsonProperty("last_name")
    public String getLast_name() {
        return this.last_name; }
    public void setLast_name(String last_name) {
        this.last_name = last_name; }
    String last_name;
    @JsonProperty("position")
    public String getPosition() {
        return this.position; }
    public void setPosition(String position) {
        this.position = position; }
    String position;
    @JsonProperty("relationships")
    public Relationships getRelationships() {
        return this.relationships; }
    public void setRelationships(Relationships relationships) {
        this.relationships = relationships; }
    Relationships relationships;
}
