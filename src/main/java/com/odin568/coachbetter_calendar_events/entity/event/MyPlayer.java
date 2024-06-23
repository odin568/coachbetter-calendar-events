package com.odin568.coachbetter_calendar_events.entity.event;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MyPlayer{
    @JsonProperty("id")
    public int getId() {
        return this.id; }
    public void setId(int id) {
        this.id = id; }
    int id;
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
    @JsonProperty("available")
    public boolean getAvailable() {
        return this.available; }
    public void setAvailable(boolean available) {
        this.available = available; }
    boolean available;
    @JsonProperty("not_available_reason")
    public String getNot_available_reason() {
        return this.not_available_reason; }
    public void setNot_available_reason(String not_available_reason) {
        this.not_available_reason = not_available_reason; }
    String not_available_reason;
    @JsonProperty("permissions")
    public Permissions getPermissions() {
        return this.permissions; }
    public void setPermissions(Permissions permissions) {
        this.permissions = permissions; }
    Permissions permissions;
}
