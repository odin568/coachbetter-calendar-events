package com.odin568.coachbetter_calendar_events.entity.team;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;

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
    @JsonProperty("name")
    public String getName() {
        return this.name; }
    public void setName(String name) {
        this.name = name; }
    String name;
    @JsonProperty("created_at")
    public Date getCreated_at() {
        return this.created_at; }
    public void setCreated_at(Date created_at) {
        this.created_at = created_at; }
    Date created_at;
    @JsonProperty("updated_at")
    public Date getUpdated_at() {
        return this.updated_at; }
    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at; }
    Date updated_at;
    @JsonProperty("share_player_information")
    public boolean getShare_player_information() {
        return this.share_player_information; }
    public void setShare_player_information(boolean share_player_information) {
        this.share_player_information = share_player_information; }
    boolean share_player_information;
    @JsonProperty("share_player_statistics")
    public boolean getShare_player_statistics() {
        return this.share_player_statistics; }
    public void setShare_player_statistics(boolean share_player_statistics) {
        this.share_player_statistics = share_player_statistics; }
    boolean share_player_statistics;
    @JsonProperty("share_player_skills_with_team")
    public boolean getShare_player_skills_with_team() {
        return this.share_player_skills_with_team; }
    public void setShare_player_skills_with_team(boolean share_player_skills_with_team) {
        this.share_player_skills_with_team = share_player_skills_with_team; }
    boolean share_player_skills_with_team;
    @JsonProperty("share_player_skills_with_player")
    public boolean getShare_player_skills_with_player() {
        return this.share_player_skills_with_player; }
    public void setShare_player_skills_with_player(boolean share_player_skills_with_player) {
        this.share_player_skills_with_player = share_player_skills_with_player; }
    boolean share_player_skills_with_player;
    @JsonProperty("default_player_attendance_status")
    public String getDefault_player_attendance_status() {
        return this.default_player_attendance_status; }
    public void setDefault_player_attendance_status(String default_player_attendance_status) {
        this.default_player_attendance_status = default_player_attendance_status; }
    String default_player_attendance_status;
    @JsonProperty("availability_cutoff_time")
    public int getAvailability_cutoff_time() {
        return this.availability_cutoff_time; }
    public void setAvailability_cutoff_time(int availability_cutoff_time) {
        this.availability_cutoff_time = availability_cutoff_time; }
    int availability_cutoff_time;
    @JsonProperty("relationships")
    public Relationships getRelationships() {
        return this.relationships; }
    public void setRelationships(Relationships relationships) {
        this.relationships = relationships; }
    Relationships relationships;
    @JsonProperty("can")
    public ArrayList<Object> getCan() {
        return this.can; }
    public void setCan(ArrayList<Object> can) {
        this.can = can; }
    ArrayList<Object> can;
}
