package com.odin568.coachbetter_calendar_events.entity.event;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Relationships{
    @JsonProperty("player")
    public Player getPlayer() {
        return this.player; }
    public void setPlayer(Player player) {
        this.player = player; }
    Player player;
    @JsonProperty("avatar")
    public Avatar getAvatar() {
        return this.avatar; }
    public void setAvatar(Avatar avatar) {
        this.avatar = avatar; }
    Avatar avatar;
}
