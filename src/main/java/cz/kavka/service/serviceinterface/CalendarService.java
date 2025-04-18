package cz.kavka.service.serviceinterface;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface CalendarService {

    /**
     * Creates credentials from JSON file
     * @return credentials
     * @throws IOException while an error occurs during reading a JSON file
     */
    GoogleCredentials authorize() throws IOException;

    /**
     * Creates instance of Google Calendar
     * @return Instance of specific Google Calendar
     * @throws IOException while an error occurs during reading a JSON file
     * @throws GeneralSecurityException when security problem occurs
     */
    Calendar getCalendar() throws IOException, GeneralSecurityException;

    /**
     * This method chooses Events from Google Calendar
     * @return Events object from Google Calendar
     * @throws IOException while an error occurs during reading a JSON file
     * @throws GeneralSecurityException when security problem occurs
     */
    Events getEvents(int limit, String pageToken) throws IOException, GeneralSecurityException;

    /**
     * This method makes a list from calendar events
     * @return List representation of events from Google Calendar
     * @throws IOException while an error occurs during reading a JSON file
     * @throws GeneralSecurityException when security problem occurs
     */
    List<Event> getListOfEvents(int limit, String pageToken) throws IOException, GeneralSecurityException;

}
