package com.odin568.coachbetter_calendar_events.helper;

import java.util.Comparator;

public class PersonHelper {

    private final String FirstName;
    private final String LastName;
    private final String Availability;

    public PersonHelper(String firstName, String lastName, String availability) {
        FirstName = firstName;
        LastName = lastName;
        Availability = availability;
    }

    @Override
    public String toString()
    {
        return FirstName + " " + LastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getAvailability()
    {
        return switch (Availability) {
            case "AVAILABLE" -> "Available";
            case "NOT_AVAILABLE" -> "Not available";
            case "NOT_RESPONDED" -> "Not responded";
            default -> Availability;
        };
    }
}


