package com.odin568.coachbetter_calendar_events.controller;

import com.odin568.coachbetter_calendar_events.service.CalendarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("calendar")
public class CalendarController {

    private final Logger logger = LoggerFactory.getLogger(CalendarController.class);
    private final CalendarService calendarService;
    private final Optional<String> configuredSecret;

    @Autowired
    public CalendarController(CalendarService calendarService, @Value("${secret:#{null}}") Optional<String> secret)
    {
        this.calendarService = calendarService;
        this.configuredSecret = secret;
    }

    @GetMapping(value = "/team", produces = "text/calendar")
    public ResponseEntity<String> getTeamCalendar(@RequestParam(value = "secret", required = false) Optional<String> currentSecret)
    {
        if (configuredSecret.isPresent()){
            if (currentSecret.isEmpty() || !configuredSecret.get().equals(currentSecret.get()))
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        logger.info("GET /team - START");
        HttpHeaders header = new HttpHeaders();
        header.add("content-disposition", "attachment; filename=\"team.ics\"");
        logger.info("GET /team - END");
        return new ResponseEntity<>(calendarService.GetTeamCalendar(), header, HttpStatus.OK);
    }
}
