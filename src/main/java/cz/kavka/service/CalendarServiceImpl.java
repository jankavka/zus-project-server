package cz.kavka.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
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
import org.springframework.stereotype.Service;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CalendarServiceImpl implements CalendarService {

    //private final File credentialFile = new File("src/main/resources/service_account_json/rscalendar-credentials.json");


    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private final String calendarId = "akce@zusdh.cz";

    /**
     * Creates credentials form JSON file
     *
     * @return GoogleCredentials representation of credentials for access Google Calendar API
     * @throws IOException while an error occurs during file operations
     */
    @Override
    public GoogleCredentials authorize() throws IOException {

        try (InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("service_account_json/rscalendar-credentials.json")) {

            if (inputStream == null) {
                throw new FileNotFoundException("File not found");
            }
            return GoogleCredentials
                    .fromStream(inputStream)
                    //.fromStream(new FileInputStream(credentialFile))
                    .createScoped(CalendarScopes.all());

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found");
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
        //HttpTransport instance needed for Calendar.Builder constructor
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        //makes credentials for creating instance of Calendar
        GoogleCredentials credentials = authorize();
        //refreshes credentials. Not sure if it is important.
        credentials.refresh();
        //Makes an instance od HttpRequestInitializer with HttpCredentialAdapter class and credentials as an argument
        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);
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
        LocalDateTime localDateTime = LocalDateTime.now();
        Calendar client = getCalendar();
        return client.events().list(calendarId)
                .setOrderBy("startTime")
                .setMaxResults(limit)
                .setSingleEvents(true)
                .setPageToken(pageToken)
                .setTimeMin(DateTime.parseRfc3339(localDateTime.toString()))
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
        return getEvents(limit, pageToken).getItems();
    }

}
