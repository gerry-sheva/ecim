package com.dti.ecim.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.dti.ecim.event.dto.CreateEventDto;
import com.dti.ecim.event.entity.Event;
import com.dti.ecim.event.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EventServiceImplIntegrationTest {
    @Autowired
    private EventService eventService;

    @Test
    public void testCreateEvent() {
        CreateEventDto createEventDto = new CreateEventDto();
        createEventDto.setTitle("test");
        createEventDto.setDescription("description");
        createEventDto.setStartingDate("2020-01-01");
        createEventDto.setEndingDate("2020-01-02");
        createEventDto.setCategoryId(1L);
        createEventDto.setCategoryId(1L);

        Event expectedEvent = eventService.createEvent(createEventDto);
        Event actualEvent = eventService.findEventById(expectedEvent.getId());

        assertEquals(expectedEvent, actualEvent);
    }
}
