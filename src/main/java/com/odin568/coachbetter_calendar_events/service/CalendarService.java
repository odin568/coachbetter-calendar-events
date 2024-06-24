package com.odin568.coachbetter_calendar_events.service;

import com.odin568.coachbetter_calendar_events.entity.Auth;
import com.odin568.coachbetter_calendar_events.entity.event.Datum;
import jakarta.annotation.PostConstruct;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.model.property.immutable.ImmutableCalScale;
import net.fortuna.ical4j.model.property.immutable.ImmutableVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.concurrent.TimeUnit;

@Service
public class CalendarService implements HealthIndicator
{
    private final CoachbetterService coachbetterService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final String targetTimezone = System.getenv("TZ") != null ? System.getenv("TZ") : "Europe/Berlin";
    private Auth authCache = null;

    @Autowired
    public CalendarService(CoachbetterService coachbetterService)
    {
        this.coachbetterService = coachbetterService;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    public synchronized Auth GetAuth()
    {
        if (authCache != null)
        {
            if (!authCache.isValid())
                authCache = coachbetterService.Refresh(authCache.RefreshToken);
        }
        else
        {
            authCache = coachbetterService.Authenticate();
        }
        return authCache;
    }

    public String GetTeamCalendar()
    {
        Calendar calendar = new Calendar();
        calendar.add(new ProdId("-//coachbetter-calendar-events//iCal4j 1.0//EN"));
        calendar.add(new XProperty("X-WR-CALNAME", "coachbetter"));
        calendar.add(new XProperty("X-WR-TIMEZONE", targetTimezone));
        calendar.add(ImmutableVersion.VERSION_2_0);
        calendar.add(ImmutableCalScale.GREGORIAN);

        // Create a TimeZone
        TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
        TimeZone timezone = registry.getTimeZone(targetTimezone);
        VTimeZone tz = timezone.getVTimeZone();

        var teams = coachbetterService.GetTeams(GetAuth());
        String calendarName = "Coachbetter";
        for (var team : teams.getData())
        {
            var seasons = coachbetterService.GetSeasons(GetAuth(), team.getId());
            for (var season : seasons.getData())
            {
                var events = coachbetterService.GetSeasonEvents(GetAuth(), season.getId());
                for (var event : events.getData())
                {
                    calendar.add(BuildIcalEvent(event, tz));
                }
            }
        }

        // TODO
        //calendar.getProperties().add(new XProperty("X-WR-CALNAME", "Coachbetter"));
        //calendar.getProperties().add(new XProperty("X-WR-TIMEZONE", targetTimezone));

        return calendar.toString();
    }

    private VEvent BuildIcalEvent(Datum input, VTimeZone tz)
    {
        Temporal start = parseDate(input.getDate_utc());
        Temporal meeting = parseDate(input.getMeeting_time_utc());
        Temporal end = parseDate(input.getEnd_time_utc());
        if (end == null)
            end = start.plus(3, ChronoUnit.HOURS);

        String title, description;

        switch (input.getRelation()) {
            case "training":
                title = "Training";
                description = BuildTrainingDescription(input);
                break;
            case "event":
                title = input.getDescription();
                description = BuildEventDescription(input);
                break;
            case "match":
                title = input.getOpponent();
                description = BuildMatchDescription(input);
                break;
            default:
                title = input.getRelation();
                description = "";
        }

        VEvent event = new VEvent(meeting != null ? meeting : start, end, title);
        event.add(new Description(description));
        event.add(tz);
        event.add(new Uid(String.valueOf(input.getUuid())));

        if (input.getNotes() != null)
            event.add(new Description(input.getNotes()));

        if (input.getLocation() != null)
            event.add(new Location(input.getLocation()));

        return event;
    }

    private String BuildMatchDescription(Datum input) {
        StringBuilder sb = new StringBuilder();
        for(var player : input.getMatch_players())
        {
            String firstname = player.getRelationships().getPlayer().getFirst_name();
            String lastname = player.getRelationships().getPlayer().getLast_name();
            String available = ToReadableAvailabilityState(player.getIn_roster());
            sb.append(lastname).append(", ").append(firstname).append(": ").append(available).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

    private String BuildEventDescription(Datum input) {
        StringBuilder sb = new StringBuilder();
        for(var player : input.getEvent_players())
        {
            String firstname = player.getRelationships().getPlayer().getFirst_name();
            String lastname = player.getRelationships().getPlayer().getLast_name();
            String available = ToReadableAvailabilityState(player.getAvailability());
            sb.append(lastname).append(", ").append(firstname).append(": ").append(available).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

    private String BuildTrainingDescription(Datum input) {
        StringBuilder sb = new StringBuilder();
        for(var player : input.getTraining_players())
        {
            String firstname = player.getRelationships().getPlayer().getFirst_name();
            String lastname = player.getRelationships().getPlayer().getLast_name();
            String available = ToReadableAvailabilityState(player.getAvailability());
            sb.append(lastname).append(", ").append(firstname).append(": ").append(available).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

    private String ToReadableAvailabilityState(String input)
    {
        return switch (input) {
            case "AVAILABLE" -> "YES";
            case "NOT_AVAILABLE" -> "NO";
            case "NOT_RESPONDED" -> "??";
            default -> input;
        };
    }


    private Temporal parseDate(String datetimeString)
    {
        if (datetimeString == null)
            return null;
        LocalDateTime localDateTime = LocalDateTime.parse(datetimeString, formatter);
        return localDateTime.atZone(ZoneId.of("UTC"));
    }

    @Override
    public Health health() {
        var auth = GetAuth();
        if (auth == null)
            return Health.outOfService().build();
        if (!auth.isValid())
            return Health.outOfService().build();

        return Health.up().build();
    }
}
