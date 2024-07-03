package com.dti.ecim.event;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dti.ecim.event.dto.RetrieveEventDto;
import com.dti.ecim.event.entity.EventOffering;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.dti.ecim.event.dto.CreateEventDto;
import com.dti.ecim.event.dto.CreateEventOfferingDto;
import com.dti.ecim.event.dto.UpdateEventDto;
import com.dti.ecim.event.entity.Event;
import com.dti.ecim.event.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

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

        List<CreateEventOfferingDto> offeringDtos = new ArrayList<>();
        CreateEventOfferingDto createVIPOfferingDto = new CreateEventOfferingDto();
        createVIPOfferingDto.setName("VIP");
        createVIPOfferingDto.setDescription("description");
        createVIPOfferingDto.setPrice(1000000L);
        createVIPOfferingDto.setCapacity(100);
        offeringDtos.add(createVIPOfferingDto);

        CreateEventOfferingDto createRegOfferingDto = new CreateEventOfferingDto();
        createRegOfferingDto.setName("Reg");
        createRegOfferingDto.setDescription("description");
        createRegOfferingDto.setPrice(1000L);
        createRegOfferingDto.setCapacity(1000);
        offeringDtos.add(createRegOfferingDto);

        createEventDto.setOfferings(offeringDtos);

        Event event = eventService.createEvent(createEventDto);
        RetrieveEventDto actualEvent = eventService.findEventById(event.getId());

        assertEquals("test", actualEvent.getTitle());
        assertEquals("description", actualEvent.getDescription());
        assertEquals("Street 1", actualEvent.getLocation().getStreet1());
        assertEquals("Street 2", actualEvent.getLocation().getStreet2());
        assertEquals("City", actualEvent.getLocation().getCity());
        assertEquals("State", actualEvent.getLocation().getState());
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
//        Event actual   = eventService.findEventById(1L);
//
//        assertEquals(actual.getTitle(), "updated");
//        assertEquals(actual.getDescription(), "updated");
//        assertEquals(actual.getCategory().getId(), 9L);
//        assertEquals(actual.getInterest().getId(), 71L);
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
