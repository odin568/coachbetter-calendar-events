package com.odin568.coachbetter_calendar_events.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Auth {

    @JsonProperty("access_token")
    public String AccessToken;

    @JsonProperty("refresh_token")
    public String RefreshToken;
}
