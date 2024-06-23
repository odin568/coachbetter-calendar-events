package com.odin568.coachbetter_calendar_events.entity.season;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Datum{
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
    @JsonProperty("team_id")
    public int getTeam_id() {
        return this.team_id; }
    public void setTeam_id(int team_id) {
        this.team_id = team_id; }
    int team_id;
    @JsonProperty("start_date")
    public String getStart_date() {
        return this.start_date; }
    public void setStart_date(String start_date) {
        this.start_date = start_date; }
    String start_date;
    @JsonProperty("end_date")
    public String getEnd_date() {
        return this.end_date; }
    public void setEnd_date(String end_date) {
        this.end_date = end_date; }
    String end_date;
    @JsonProperty("user_id")
    public int getUser_id() {
        return this.user_id; }
    public void setUser_id(int user_id) {
        this.user_id = user_id; }
    int user_id;
    @JsonProperty("can")
    public ArrayList<Object> getCan() {
        return this.can; }
    public void setCan(ArrayList<Object> can) {
        this.can = can; }
    ArrayList<Object> can;
}
