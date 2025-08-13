package service;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.auth.oauth2.GoogleCredentials;
import cz.kavka.service.CalendarServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class CalendarServiceImplTest {

    @Mock
    HttpTransport httpTransport;

    @InjectMocks
    CalendarServiceImpl service;

    // ---------- getCalendar ----------

    @Test
    void getCalendar_buildsClient_andCallsAuthorize() throws Exception {
        // Spy the service to stub authorize() (avoid touching real resource)
        CalendarServiceImpl spy = Mockito.spy(new CalendarServiceImpl(httpTransport));
        GoogleCredentials creds = mock(GoogleCredentials.class);
        doReturn(creds).when(spy).authorize();

        var calendar = spy.getCalendar();

        assertNotNull(calendar);
        verify(spy).authorize();
        // No execute() here, so no network call is made.
    }

    // ---------- getEvents ----------

    @Test
    void getEvents_buildsRequestWithLimit_andPageToken_andExecutes() throws Exception {
        int limit = 25;
        String token = "nextPage123";

        // Spy the service so getEvents uses our mocked Calendar
        CalendarServiceImpl spy = Mockito.spy(new CalendarServiceImpl(httpTransport));
        Calendar calendarMock = mock(Calendar.class);
        Calendar.Events eventsMock = mock(Calendar.Events.class);
        Calendar.Events.List listMock = mock(Calendar.Events.List.class);

        // Make getCalendar() return our mock
        doReturn(calendarMock).when(spy).getCalendar();

        // Chain: calendar.events().list("akce@zusdh.cz") -> listMock
        when(calendarMock.events()).thenReturn(eventsMock);
        when(eventsMock.list("akce@zusdh.cz")).thenReturn(listMock);

        // Chain the fluent setters to return the same list mock
        when(listMock.setOrderBy("startTime")).thenReturn(listMock);
        when(listMock.setMaxResults(limit)).thenReturn(listMock);
        when(listMock.setSingleEvents(true)).thenReturn(listMock);
        when(listMock.setPageToken(token)).thenReturn(listMock);
        when(listMock.setTimeMin(any(DateTime.class))).thenReturn(listMock);

        // Final execute() result
        Events expected = new Events();
        when(listMock.execute()).thenReturn(expected);

        // Call
        Events result = spy.getEvents(limit, token);

        assertSame(expected, result);
        verify(spy).getCalendar();
        verify(calendarMock).events();
        verify(eventsMock).list("akce@zusdh.cz");
        verify(listMock).setOrderBy("startTime");
        verify(listMock).setMaxResults(limit);
        verify(listMock).setSingleEvents(true);
        verify(listMock).setPageToken(token);
        verify(listMock).setTimeMin(any(DateTime.class));
        verify(listMock).execute();
    }

    @Test
    void getEvents_withNullToken_stillExecutes() throws Exception {
        int limit = 10;
        String token = null;

        CalendarServiceImpl spy = Mockito.spy(new CalendarServiceImpl(httpTransport));
        Calendar calendarMock = mock(Calendar.class);
        Calendar.Events eventsMock = mock(Calendar.Events.class);
        Calendar.Events.List listMock = mock(Calendar.Events.List.class);

        doReturn(calendarMock).when(spy).getCalendar();
        when(calendarMock.events()).thenReturn(eventsMock);
        when(eventsMock.list("akce@zusdh.cz")).thenReturn(listMock);

        when(listMock.setOrderBy("startTime")).thenReturn(listMock);
        when(listMock.setMaxResults(limit)).thenReturn(listMock);
        when(listMock.setSingleEvents(true)).thenReturn(listMock);
        // In your current code you call setPageToken(pageToken) even if null; Mockito will accept it:
        when(listMock.setPageToken(null)).thenReturn(listMock);
        when(listMock.setTimeMin(any(DateTime.class))).thenReturn(listMock);

        Events expected = new Events();
        when(listMock.execute()).thenReturn(expected);

        Events result = spy.getEvents(limit, token);

        assertSame(expected, result);
        verify(listMock).setPageToken(null);
        verify(listMock).execute();
    }

    // ---------- getListOfEvents ----------

    @Test
    void getListOfEvents_returnsItems_whenPresent() throws IOException, GeneralSecurityException {
        CalendarServiceImpl spy = Mockito.spy(new CalendarServiceImpl(httpTransport));
        Events events = new Events()
                .setItems(List.of(new Event().setId("1"), new Event().setId("2")));
        doReturn(events).when(spy).getEvents(5, "t");

        List<Event> result = spy.getListOfEvents(5, "t");

        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("2", result.get(1).getId());
        verify(spy).getEvents(5, "t");
    }

    @Test
    void getListOfEvents_returnsEmptyList_whenItemsNull() throws IOException, GeneralSecurityException {
        CalendarServiceImpl spy = Mockito.spy(new CalendarServiceImpl(httpTransport));
        Events events = new Events(); // items == null by default
        doReturn(events).when(spy).getEvents(5, null);

        List<Event> result = spy.getListOfEvents(5, null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(spy).getEvents(5, null);
    }

}
