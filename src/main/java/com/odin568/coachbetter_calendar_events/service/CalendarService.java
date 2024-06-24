package com.odin568.coachbetter_calendar_events.service;

import com.odin568.coachbetter_calendar_events.entity.event.Datum;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.model.property.immutable.ImmutableCalScale;
import net.fortuna.ical4j.model.property.immutable.ImmutableVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

@Service
public class CalendarService {

    private final CoachbetterService coachbetterService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public CalendarService(CoachbetterService coachbetterService)
    {
        this.coachbetterService = coachbetterService;
    }

    public String GetTeamCalendar()
    {
        Calendar calendar = new Calendar();
        calendar.add(new ProdId("-//coachbetter-calendar-events//iCal4j 1.0//EN"));
        calendar.add(ImmutableVersion.VERSION_2_0);
        calendar.add(ImmutableCalScale.GREGORIAN);

        // Create a TimeZone
        TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
        TimeZone timezone = registry.getTimeZone("Europe/Berlin");
        VTimeZone tz = timezone.getVTimeZone();

        var auth = coachbetterService.Authenticate();
        var teams = coachbetterService.GetTeams(auth);
        String calendarName = "Coachbetter";
        for (var team : teams.getData())
        {
            calendarName += " - " + team.getName();
            var seasons = coachbetterService.GetSeasons(auth, team.getId());
            for (var season : seasons.getData())
            {
                var events = coachbetterService.GetSeasonEvents(auth, season.getId());
                for (var event : events.getData())
                {
                    calendar.add(BuildIcalEvent(event, tz));
                }
            }
        }

        // TODO
        //calendar.getProperties().add(new XProperty("X-WR-CALNAME", calendarName));
        //calendar.getProperties().add(new XProperty("X-WR-TIMEZONE", "Europe/Berlin"));

        return calendar.toString();
    }

    private VEvent BuildIcalEvent(Datum input, VTimeZone tz)
    {
        Temporal start = parseDate(input.getDate_utc());
        Temporal end = parseDate(input.getEnd_time_utc());

        if (end == null)
            end = start.plus(3, ChronoUnit.HOURS);

        String summary = switch (input.getRelation()) {
            case "training" -> "Training";
            case "event" -> input.getDescription();
            case "match" -> input.getOpponent();
            default -> input.getRelation(); // Whatever
        };

        VEvent event = new VEvent(start, end, summary);
        event.add(tz);
        event.add(new Uid(String.valueOf(input.getUuid())));

        if (input.getNotes() != null)
            event.add(new Description(input.getNotes()));

        if (input.getLocation() != null)
            event.add(new Location(input.getLocation()));

        return event;
    }


    private Temporal parseDate(String datetimeString)
    {
        if (datetimeString == null)
            return null;
        LocalDateTime localDateTime = LocalDateTime.parse(datetimeString, formatter);
        return localDateTime.atZone(ZoneId.of("UTC"));
    }
}
