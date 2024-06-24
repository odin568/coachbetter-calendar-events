package com.odin568.coachbetter_calendar_events.entity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Calendar;
import java.util.Date;

public class Auth {

    @JsonProperty("access_token")
    public String AccessToken;

    @JsonProperty("refresh_token")
    public String RefreshToken;


    @JsonIgnore
    public boolean isValid()
    {
        DecodedJWT jwt = JWT.decode(AccessToken);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, 5);
        Date nowPlus5Minutes = cal.getTime();

        return !jwt.getExpiresAt().before(nowPlus5Minutes);
    }
}
