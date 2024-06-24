package com.odin568.coachbetter_calendar_events.controller;

import com.odin568.coachbetter_calendar_events.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping(value = "/team", produces = "text/plain")
    public String getTeamCalendar() {

        return calendarService.GetTeamCalendar();
    }
}
