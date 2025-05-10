package cz.kavka.controller;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import cz.kavka.service.serviceinterface.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    private final CalendarService calendarService;

    @Autowired
    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping("/events")
    public List<Event> showEvents(@RequestParam(required = false, defaultValue = Integer.MAX_VALUE + "") int limit,
                                  @RequestParam(required = false, defaultValue = "") String pageToken)
            throws IOException, GeneralSecurityException {
        return calendarService.getListOfEvents(limit, pageToken);
    }


    @GetMapping("/all-events")
    public Events showAllEvents (@RequestParam(required = false, defaultValue = Integer.MAX_VALUE + "") int limit,
                                 @RequestParam(required = false, defaultValue = "") String nextPageToken)
            throws IOException,GeneralSecurityException{
        return calendarService.getEvents(limit, nextPageToken);
    }
}
