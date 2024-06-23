package com.odin568.coachbetter_calendar_events.entity.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Datum{
    @JsonProperty("relation")
    public String getRelation() {
        return this.relation; }
    public void setRelation(String relation) {
        this.relation = relation; }
    String relation;
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
    @JsonProperty("season_id")
    public int getSeason_id() {
        return this.season_id; }
    public void setSeason_id(int season_id) {
        this.season_id = season_id; }
    int season_id;
    @JsonProperty("date")
    public String getDate() {
        return this.date; }
    public void setDate(String date) {
        this.date = date; }
    String date;
    @JsonProperty("date_utc")
    public String getDate_utc() {
        return this.date_utc; }
    public void setDate_utc(String date_utc) {
        this.date_utc = date_utc; }
    String date_utc;
    @JsonProperty("training_focus")
    public Object getTraining_focus() {
        return this.training_focus; }
    public void setTraining_focus(Object training_focus) {
        this.training_focus = training_focus; }
    Object training_focus;
    @JsonProperty("location")
    public String getLocation() {
        return this.location; }
    public void setLocation(String location) {
        this.location = location; }
    String location;
    @JsonProperty("lat")
    public String getLat() {
        return this.lat; }
    public void setLat(String lat) {
        this.lat = lat; }
    String lat;
    @JsonProperty("lng")
    public String getLng() {
        return this.lng; }
    public void setLng(String lng) {
        this.lng = lng; }
    String lng;
    @JsonProperty("end_time")
    public String getEnd_time() {
        return this.end_time; }
    public void setEnd_time(String end_time) {
        this.end_time = end_time; }
    String end_time;
    @JsonProperty("end_time_utc")
    public String getEnd_time_utc() {
        return this.end_time_utc; }
    public void setEnd_time_utc(String end_time_utc) {
        this.end_time_utc = end_time_utc; }
    String end_time_utc;
    @JsonProperty("intensity")
    public int getIntensity() {
        return this.intensity; }
    public void setIntensity(int intensity) {
        this.intensity = intensity; }
    int intensity;
    @JsonProperty("additional_info")
    public String getAdditional_info() {
        return this.additional_info; }
    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info; }
    String additional_info;
    @JsonProperty("total_players")
    public int getTotal_players() {
        return this.total_players; }
    public void setTotal_players(int total_players) {
        this.total_players = total_players; }
    int total_players;
    @JsonProperty("team_name")
    public String getTeam_name() {
        return this.team_name; }
    public void setTeam_name(String team_name) {
        this.team_name = team_name; }
    String team_name;
    @JsonProperty("available_players")
    public int getAvailable_players() {
        return this.available_players; }
    public void setAvailable_players(int available_players) {
        this.available_players = available_players; }
    int available_players;
    @JsonProperty("my_players")
    public ArrayList<MyPlayer> getMy_players() {
        return this.my_players; }
    public void setMy_players(ArrayList<MyPlayer> my_players) {
        this.my_players = my_players; }
    ArrayList<MyPlayer> my_players;
    @JsonProperty("training_players")
    public ArrayList<TrainingPlayer> getTraining_players() {
        return this.training_players; }
    public void setTraining_players(ArrayList<TrainingPlayer> training_players) {
        this.training_players = training_players; }
    ArrayList<TrainingPlayer> training_players;
    @JsonProperty("can")
    public ArrayList<Object> getCan() {
        return this.can; }
    public void setCan(ArrayList<Object> can) {
        this.can = can; }
    ArrayList<Object> can;
    @JsonProperty("home")
    public int getHome() {
        return this.home; }
    public void setHome(int home) {
        this.home = home; }
    int home;
    @JsonProperty("opponent")
    public String getOpponent() {
        return this.opponent; }
    public void setOpponent(String opponent) {
        this.opponent = opponent; }
    String opponent;
    @JsonProperty("meeting_time")
    public String getMeeting_time() {
        return this.meeting_time; }
    public void setMeeting_time(String meeting_time) {
        this.meeting_time = meeting_time; }
    String meeting_time;
    @JsonProperty("meeting_time_utc")
    public String getMeeting_time_utc() {
        return this.meeting_time_utc; }
    public void setMeeting_time_utc(String meeting_time_utc) {
        this.meeting_time_utc = meeting_time_utc; }
    String meeting_time_utc;
    @JsonProperty("notes")
    public Object getNotes() {
        return this.notes; }
    public void setNotes(Object notes) {
        this.notes = notes; }
    Object notes;
    @JsonProperty("match_players")
    public ArrayList<MatchPlayer> getMatch_players() {
        return this.match_players; }
    public void setMatch_players(ArrayList<MatchPlayer> match_players) {
        this.match_players = match_players; }
    ArrayList<MatchPlayer> match_players;
    @JsonProperty("description")
    public String getDescription() {
        return this.description; }
    public void setDescription(String description) {
        this.description = description; }
    String description;
    @JsonProperty("event_players")
    public ArrayList<EventPlayer> getEvent_players() {
        return this.event_players; }
    public void setEvent_players(ArrayList<EventPlayer> event_players) {
        this.event_players = event_players; }
    ArrayList<EventPlayer> event_players;
}
