package cz.kavka.service;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import cz.kavka.service.serviceinterface.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
public class CalendarServiceImpl implements CalendarService {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    //HttpTransport instance needed for Calendar.Builder constructor
    private final HttpTransport httpTransport;

    @Autowired
    public CalendarServiceImpl(HttpTransport httpTransport){
        this.httpTransport = httpTransport;
    }

    /**
     * Creates credentials form JSON file
     *
     * @return GoogleCredentials representation of credentials for access Google Calendar API
     * @throws IOException while an error occurs during file operations
     */
    @Override
    public GoogleCredentials authorize() throws IOException {

        String resource = "service_account_json/rscalendar-credentials2.json";

        try (InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream(resource)) {

            if (inputStream == null) {
                throw new FileNotFoundException("File not found");
            }
            return GoogleCredentials
                    .fromStream(inputStream)
                    //.fromStream(new FileInputStream(credentialFile))
                    .createScoped(CalendarScopes.all());

        }
    }

    /**
     * This method creates an instance of Calendar using Calendar.Builder using instance of HttpTransport, JsonFactory
     * and HttpRequestInitializer
     *
     * @return An object of Calendar
     * @throws IOException              while an error occurs during file operations
     * @throws GeneralSecurityException when security problem occurs
     */
    @Override
    public Calendar getCalendar() throws IOException, GeneralSecurityException {
        //makes credentials for creating instance of Calendar
        GoogleCredentials credentials = authorize();
        //Makes an instance od HttpRequestInitializer with HttpCredentialAdapter class and credentials as an argument
        HttpRequestInitializer requestInitializer = request -> {
            new HttpCredentialsAdapter(credentials).initialize(request);
            request.setConnectTimeout(20_000);
            request.setReadTimeout(20_000);
            request.setNumberOfRetries(3);
        };
        return new Calendar.Builder(httpTransport, JSON_FACTORY, requestInitializer)
                .setApplicationName("Calendar")
                .build();
    }

    /**
     * This method finds all events of a Google calendar with specific limitations
     *
     * @return Events representation of Google calendar events with specific limitations
     * @throws IOException              while an error occurs during file operations
     * @throws GeneralSecurityException when security problem occurs
     */
    @Override
    public Events getEvents(int limit, String pageToken) throws IOException, GeneralSecurityException {
        //LocalDateTime localDateTime = LocalDateTime.now();
        DateTime timeMin = new DateTime(System.currentTimeMillis());
        Calendar client = getCalendar();
        String calendarId = "akce@zusdh.cz";
        return client.events().list(calendarId)
                .setOrderBy("startTime")
                .setMaxResults(limit)
                .setSingleEvents(true)
                .setPageToken(pageToken)
                .setTimeMin(timeMin)
                //.setTimeMin(DateTime.parseRfc3339(localDateTime.toString()))
                .execute();
    }

    /**
     * This method makes list of listed events
     *
     * @return List representation of listed events
     * @throws IOException              while an error occurs during file operations
     * @throws GeneralSecurityException when security problem occurs
     */
    @Override
    public List<Event> getListOfEvents(int limit, String pageToken) throws IOException, GeneralSecurityException {
        var events = getEvents(limit, pageToken);
        var items = events.getItems();

        return (items != null) ? items : List.of();

    }

}
