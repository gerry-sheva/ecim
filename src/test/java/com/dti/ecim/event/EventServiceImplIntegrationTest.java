package com.dti.ecim.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.dti.ecim.event.dto.CreateEventDto;
import com.dti.ecim.event.dto.UpdateEventDto;
import com.dti.ecim.event.entity.Event;
import com.dti.ecim.event.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
public class EventServiceImplIntegrationTest {
    @Autowired
    private EventService eventService;

    @Test
    public void testCreateEvent() {
        CreateEventDto createEventDto = new CreateEventDto();
        createEventDto.setTitle("test");
        createEventDto.setDescription("description");
        createEventDto.setStartingDate("09:15:30 PM, Sun 06/30/2024");
        createEventDto.setEndingDate("09:15:30 PM, Sun 07/07/2024");
        createEventDto.setCategoryId(1L);
        createEventDto.setInterestId(1L);
        createEventDto.setStreet1("Street 1");
        createEventDto.setStreet2("Street 2");
        createEventDto.setCity("City");
        createEventDto.setState("State");

        Event actualEvent = eventService.createEvent(createEventDto);

        assertEquals("test", actualEvent.getTitle());
        assertEquals("description", actualEvent.getDescription());
        assertEquals(1L, actualEvent.getCategory().getId());
        assertEquals(1L, actualEvent.getInterest().getId());
        assertEquals("Street 1", actualEvent.getEventLocation().getStreet1());
        assertEquals("Street 2", actualEvent.getEventLocation().getStreet2());
        assertEquals("City", actualEvent.getEventLocation().getCity());
        assertEquals("State", actualEvent.getEventLocation().getState());
    }

    @Test
    public void testUpdateEvent() {
        UpdateEventDto updateEventDto = new UpdateEventDto();
        updateEventDto.setTitle("updated");
        updateEventDto.setDescription("updated");
        updateEventDto.setStartingDate("09:15:30 PM, Sun 06/30/2024");
        updateEventDto.setEndingDate("09:15:30 PM, Sun 07/07/2024");
        updateEventDto.setCategoryId(9L);
        updateEventDto.setInterestId(71L);

        Event expected = eventService.updateEvent(1L, updateEventDto);
        Event actual   = eventService.findEventById(1L);

        assertEquals(actual.getTitle(), "updated");
        assertEquals(actual.getDescription(), "updated");
        assertEquals(actual.getCategory().getId(), 9L);
        assertEquals(actual.getInterest().getId(), 71L);
    }

    @Test
    public void testDisplayEvents() {
//        Pageable pageable = PageRequest.of(0,20);
//        Page<Event> eventPage = eventService.displayEvents(pageable);
//        assertEquals(20, eventPage.stream().count());
//
//        Pageable pageable2 = PageRequest.of(0,10);
//        Page<Event> eventPage2 = eventService.displayEvents(pageable2);
//        assertEquals(10, eventPage2.stream().count());
    }
}
