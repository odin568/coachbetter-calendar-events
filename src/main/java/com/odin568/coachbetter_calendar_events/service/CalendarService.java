package com.odin568.coachbetter_calendar_events.service;

import com.odin568.coachbetter_calendar_events.entity.Auth;
import com.odin568.coachbetter_calendar_events.entity.event.Datum;
import com.odin568.coachbetter_calendar_events.helper.PersonHelper;
import com.odin568.coachbetter_calendar_events.helper.PersonHelperComparator;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.model.property.immutable.ImmutableCalScale;
import net.fortuna.ical4j.model.property.immutable.ImmutableVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CalendarService implements HealthIndicator
{
    private final Logger logger = LoggerFactory.getLogger(CalendarService.class);
    private final CoachbetterService coachbetterService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final VTimeZone timeZone;
    private final String timeZoneId;
    private Auth authCache = null;

    @Autowired
    public CalendarService(CoachbetterService coachbetterService)
    {
        this.coachbetterService = coachbetterService;

        // Create a TimeZone
        timeZoneId = System.getenv("TZ") != null ? System.getenv("TZ") : "Europe/Berlin";
        TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
        TimeZone timezone = registry.getTimeZone(timeZoneId);
        timeZone = timezone.getVTimeZone();
        logger.info("Using timezone {}", timeZone.getProperty("TZID"));
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

    public String GetPersonalCalendar()
    {
        return buildCalendar(true);
    }

    public String GetTeamCalendar()
    {
        return buildCalendar(false);
    }

    private String buildCalendar(boolean isPersonal)
    {
        Calendar calendar = new Calendar();
        calendar.add(new ProdId("-//coachbetter-calendar-events//iCal4j 1.0//EN"));
        calendar.add(new XProperty("X-WR-CALNAME", "coachbetter " + (isPersonal ? "Personal" : "Team")));
        calendar.add(new XProperty("X-WR-TIMEZONE", timeZoneId));
        calendar.add(new Color(new ParameterList(), "#FFFFFF"));
        calendar.add(ImmutableVersion.VERSION_2_0);
        calendar.add(ImmutableCalScale.GREGORIAN);
        calendar.add(timeZone);

        var teams = coachbetterService.GetTeams(GetAuth());
        for (var team : teams.getData())
        {
            var seasons = coachbetterService.GetSeasons(GetAuth(), team.getId());
            for (var season : seasons.getData())
            {
                var events = coachbetterService.GetSeasonEvents(GetAuth(), season.getId());
                for (var event : events.getData())
                {
                    var vEvent = buildEvent(event, isPersonal);
                    calendar.add(vEvent);
                }
            }
        }

        return calendar.toString();
    }

    private VEvent buildEvent(Datum input, boolean personal)
    {
        boolean thisEventIsNotFromInterest = personal && !myPlayerIsAvailable(input);

        // Get times
        ZonedDateTime inputMeetingTime = parseUtcDateTime(input.getMeeting_time_utc());
        ZonedDateTime inputStartTime = parseUtcDateTime(input.getDate_utc());
        ZonedDateTime inputEndTime = parseUtcDateTime(input.getEnd_time_utc());

        // Event-Series provide the end *date* of the first occurrence.
        // Use thereby only the time part and reuse the date part
        if (inputEndTime !=  null && inputEndTime.isBefore(inputStartTime))
        {
            inputEndTime = applyTimePartToDatePart(inputStartTime, inputEndTime);
        }

        // in case meeting time is provided, use this as start date
        if (inputMeetingTime != null)
        {
            inputStartTime = inputMeetingTime;
        }

        String title, description;
        description = switch (input.getRelation()) {
            case "training" -> {
                title = "Training";
                yield buildTrainingDescription(input);
            }
            case "event" -> {
                title = input.getDescription();
                yield buildEventDescription(input);
            }
            case "match" -> {
                title = input.getOpponent();
                yield buildMatchDescription(input);
            }
            default -> {
                title = input.getRelation();
                yield "";
            }
        };

        // Prefix not relevant events
        if (thisEventIsNotFromInterest) {
            title = "X: " + title;
        }

        Duration alarmDuration = Duration.ofHours(-1);
        VEvent event;
        if (inputEndTime == null)
        {
            assert inputStartTime != null;
            event = new VEvent(inputStartTime.withZoneSameInstant(ZoneId.of(timeZoneId)), title);
        }
        else if (isFullDayAppointment(inputStartTime, inputEndTime))
        {
            alarmDuration = Duration.ofDays(-1);
            // Attention to take the correct date due to timezone UTC and localtime
            var onlyDate = inputStartTime.withZoneSameInstant(ZoneId.of(timeZoneId)).toLocalDate();
            event = new VEvent(onlyDate, title);
        }
        else
        {
            event = new VEvent(inputStartTime.withZoneSameInstant(ZoneId.of(timeZoneId)), inputEndTime.withZoneSameInstant(ZoneId.of(timeZoneId)), title);
        }

        // Mark not relevant Events as optional
        if (thisEventIsNotFromInterest) {
            event.add(new Transp(Transp.VALUE_TRANSPARENT));
        }

        // Create alarms for relevant events
        if (!thisEventIsNotFromInterest)
        {
            VAlarm vAlarm = new VAlarm(alarmDuration);
            vAlarm.add(new Action(Action.VALUE_DISPLAY));
            vAlarm.add(new Description(title));
            event.add(vAlarm);
        }

        event.add(new Description(description));
        event.add(new Uid(input.getUuid()));
        if (input.getNotes() != null)
            event.add(new Description(input.getNotes()));

        if (input.getLocation() != null)
            event.add(new Location(input.getLocation()));

        return event;
    }

    private boolean myPlayerIsAvailable(Datum input) {

        for (var myPlayer : input.getMy_players())
        {
            if (!myPlayer.getAvailable())
                return false;
        }
        return true;
    }

    private boolean isFullDayAppointment(ZonedDateTime start, ZonedDateTime end)
    {
        if (start == null || end == null)
            return false;
        var duration = java.time.Duration.between(start, end);
        long durationHours = duration.abs().toHours();
        return durationHours == 23 || durationHours == 24; // Is usually 23hr and 55mins
    }

    private ZonedDateTime applyTimePartToDatePart(ZonedDateTime datePart, ZonedDateTime timePart)
    {
        // If start is on 0:00, it might be day before in UTC
        var correctlyZonedDate = datePart.withZoneSameInstant(ZoneId.of(timeZoneId));

        int year = correctlyZonedDate.getYear();
        int month = correctlyZonedDate.getMonth().getValue();
        int day = correctlyZonedDate.getDayOfMonth();

        int hour = timePart.getHour();
        int minute = timePart.getMinute();
        int second = timePart.getSecond();
        int nano = timePart.getNano();

        return ZonedDateTime.of(year, month, day, hour, minute, second, nano, ZoneId.of("UTC"));
    }

    private String buildMatchDescription(Datum input) {
        var persons = new ArrayList<PersonHelper>();
        for(var player : input.getMatch_players())
        {
            var temp = player.getRelationships().getPlayer();
            persons.add(new PersonHelper(temp.getFirst_name(), temp.getLast_name(), player.getIn_roster()));
        }
        persons.sort(new PersonHelperComparator());

        return buildPersonOverview(persons);
    }

    private String buildEventDescription(Datum input)
    {
        var persons = new ArrayList<PersonHelper>();
        for(var player : input.getEvent_players())
        {
            var temp = player.getRelationships().getPlayer();
            persons.add(new PersonHelper(temp.getFirst_name(), temp.getLast_name(), player.getAvailability()));
        }
        persons.sort(new PersonHelperComparator());

        return buildPersonOverview(persons);
    }

    private String buildTrainingDescription(Datum input)
    {
        var persons = new ArrayList<PersonHelper>();
        for(var player : input.getTraining_players())
        {
            var temp = player.getRelationships().getPlayer();
            persons.add(new PersonHelper(temp.getFirst_name(), temp.getLast_name(), player.getAvailability()));
        }
        persons.sort(new PersonHelperComparator());

        return buildPersonOverview(persons);
    }

    private String buildPersonOverview(ArrayList<PersonHelper> persons)
    {
        Map<String, List<PersonHelper>> groupedByAvailability = persons.stream()
                .collect(Collectors.groupingBy(PersonHelper::getAvailability));

        StringBuilder sb = new StringBuilder();
        for (var entry : groupedByAvailability.entrySet())
        {
            sb.append(System.lineSeparator()).append("*").append(entry.getKey()).append("*").append(System.lineSeparator());
            entry.getValue().forEach(p -> sb.append(p).append(System.lineSeparator()));
        }
        return sb.toString().trim();
    }

    private ZonedDateTime parseUtcDateTime(String datetimeString)
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
