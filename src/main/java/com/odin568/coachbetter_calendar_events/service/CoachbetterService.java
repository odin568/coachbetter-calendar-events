package com.odin568.coachbetter_calendar_events.service;

import com.odin568.coachbetter_calendar_events.entity.Auth;
import com.odin568.coachbetter_calendar_events.entity.event.Events;
import com.odin568.coachbetter_calendar_events.entity.season.Season;
import com.odin568.coachbetter_calendar_events.entity.team.Team;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class CoachbetterService
{
    private final Logger logger = LoggerFactory.getLogger(CoachbetterService.class);
    private final String host;
    private final String apiKey;
    private final String userAgent;
    private final String userName;
    private final String password;

    @Autowired
    public CoachbetterService(@Value("${coachbetter.host}") String host,
                              @Value("${coachbetter.apiKey}") String apiKey,
                              @Value("${coachbetter.userAgent}") String userAgent,
                              @Value("${coachbetter.username}") String userName,
                              @Value("${coachbetter.password}") String password)
    {
        this.host = host;
        this.apiKey = apiKey;
        this.userAgent = userAgent;
        this.userName = userName;
        this.password = password;
    }

    public Auth Authenticate()
    {
        logger.info("Authenticating");
        final String uri = "https://" + host + "/api/v2/auth/login";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", userName);
        jsonObject.put("password", password);
        String body = jsonObject.toString();

        HttpEntity<String> entity = CreateHttpEntity(body);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Auth> respEntity = restTemplate.exchange(uri, HttpMethod.POST, entity, Auth.class);

        return respEntity.getBody();
    }

    public Auth Refresh(String refreshToken)
    {
        logger.info("Refreshing authentication");
        final String uri = "https://" + host + "/api/v2/auth/refresh-token";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("refresh_token", refreshToken);
        String body = jsonObject.toString();

        HttpEntity<String> entity = CreateHttpEntity(body);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Auth> respEntity = restTemplate.exchange(uri, HttpMethod.POST, entity, Auth.class);

        return respEntity.getBody();
    }

    public Team GetTeams(Auth auth)
    {
        logger.info("Querying Teams");
        final String uri = "https://" + host + "/api/v2/teams";

        HttpEntity<String> entity = CreateHttpEntity(null, auth);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Team> respEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, Team.class);

        return respEntity.getBody();
    }

    public Season GetSeasons(Auth auth, int teamId)
    {
        logger.info("Querying Seasons for Team " + teamId);
        final String uri = "https://" + host + "/api/teams/" + teamId + "/seasons";

        HttpEntity<String> entity = CreateHttpEntity(null, auth);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Season> respEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, Season.class);

        return respEntity.getBody();
    }

    public Events GetSeasonEvents(Auth auth, int seasonId)
    {
        logger.info("Querying Events for Season " + seasonId);

        final String uri = "https://" + host + "/api/seasons/" + seasonId + "/user-events";

        HttpEntity<String> entity = CreateHttpEntity(null, auth);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Events> respEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, Events.class);

        return respEntity.getBody();
    }


    private HttpEntity<String> CreateHttpEntity(String body)
    {
        return CreateHttpEntity(body, null);
    }

    private HttpEntity<String> CreateHttpEntity(String body, Auth auth)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.set("host", host);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        if (body != null)
            headers.setContentType(MediaType.APPLICATION_JSON);
        if (auth != null)
            headers.setBearerAuth(auth.AccessToken);

        headers.add("user-agent", userAgent);
        headers.set("x-api-key", apiKey);

        return new HttpEntity<>(body, headers);
    }


}
