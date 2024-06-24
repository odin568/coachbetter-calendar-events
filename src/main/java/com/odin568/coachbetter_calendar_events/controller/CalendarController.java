package com.odin568.coachbetter_calendar_events.controller;

import com.odin568.coachbetter_calendar_events.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("calendar")
public class CalendarController {

    private final CalendarService calendarService;

    @Autowired
    public CalendarController(CalendarService calendarService)
    {
        this.calendarService = calendarService;
    }

    @GetMapping(value = "/team", produces = "text/calendar")
    public ResponseEntity<String> getTeamCalendar()
    {
        HttpHeaders header = new HttpHeaders();
        header.add("content-disposition", "attachment; filename=\"team.ics\"");
        return new ResponseEntity<>(calendarService.GetTeamCalendar(), header, HttpStatus.OK);
    }
}
