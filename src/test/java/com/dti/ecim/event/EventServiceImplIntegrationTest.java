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
        createEventDto.setStartingDate("09:15:30 PM, Sun 06/30/2024");
        createEventDto.setEndingDate("09:15:30 PM, Sun 07/07/2024");
        createEventDto.setCategoryId(1L);
        createEventDto.setInterestId(1L);

        Event expectedEvent = eventService.createEvent(createEventDto);
        Event actualEvent = eventService.findEventById(expectedEvent.getId());

        assertEquals(expectedEvent.getId(), actualEvent.getId());
        assertEquals(expectedEvent.getTitle(), actualEvent.getTitle());
        assertEquals(expectedEvent.getDescription(), actualEvent.getDescription());
    }


}
