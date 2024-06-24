package com.odin568.coachbetter_calendar_events.helper;

import java.util.Comparator;

public class PersonHelperComparator implements Comparator<PersonHelper>
{
    @Override
    public int compare(PersonHelper o1, PersonHelper o2) {
        // Compare by property1
        int result = CharSequence.compare(o1.getAvailability(), o2.getAvailability());
        if (result != 0) {
            return result;
        }

        // If property1 is equal, compare by property2
        result = o1.getFirstName().compareTo(o2.getFirstName());
        if (result != 0) {
            return result;
        }

        // If property2 is equal, compare by property3
        return CharSequence.compare(o1.getLastName(), o2.getLastName());
    }
}
