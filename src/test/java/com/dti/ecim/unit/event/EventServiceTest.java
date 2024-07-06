package com.dti.ecim.unit.event;

import com.dti.ecim.event.dto.CreateEventRequestDto;
import com.dti.ecim.event.dto.RetrieveEventResponseDto;
import com.dti.ecim.event.repository.EventOfferingRepository;
import com.dti.ecim.event.repository.EventRepository;
import com.dti.ecim.event.repository.InterestRepository;
import com.dti.ecim.event.service.EventService;
import com.dti.ecim.event.service.impl.EventServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

import java.time.format.DateTimeParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class EventServiceTest {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventOfferingRepository eventOfferingRepository;

    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EventService eventService;

    CreateEventRequestDto createEventRequestDto = new CreateEventRequestDto();

    @BeforeEach
    void setUp() {
        createEventRequestDto.setTitle("Sample Event");
        createEventRequestDto.setDescription("Sample Description");
        createEventRequestDto.setStartingDate("07:00:00 PM, Wed 07/10/2024");
        createEventRequestDto.setEndingDate("10:00:00 PM, Wed 07/10/2024");
        createEventRequestDto.setInterestId(1L);

        CreateEventRequestDto.CreateEventLocationDto locationDto = new CreateEventRequestDto.CreateEventLocationDto();
        locationDto.setStreet1("123 Main St");
        locationDto.setStreet2("Apt 4B");
        locationDto.setCity("Sample City");
        locationDto.setState("Sample State");
        createEventRequestDto.setLocation(locationDto);

        CreateEventRequestDto.CreateEventOfferingDto offeringDto = new CreateEventRequestDto.CreateEventOfferingDto();
        offeringDto.setName("Sample Offering");
        offeringDto.setDescription("Sample Offering Description");
        offeringDto.setPrice(100);
        offeringDto.setCapacity(50);
        createEventRequestDto.setOfferings(List.of(offeringDto));
    }

    @Test
    public void test_create_event_with_valid_data() {
        RetrieveEventResponseDto response = eventService.createEvent(createEventRequestDto);

        assertNotNull(response);
        assertEquals("Sample Event", response.getTitle());
    }

    @Test
    public void test_create_event_with_invalid_date() {
        createEventRequestDto.setStartingDate("10:00:00 AM, Mon 1/1/2023");
        createEventRequestDto.setEndingDate("12:00:00 PM, Mon 1/1/2023");

        Assertions.assertThrows(DateTimeParseException.class, () -> eventService.createEvent(createEventRequestDto));
    }

    @Test
    public void test_create_event_that_ends_before_starting_date() {
        createEventRequestDto.setEndingDate("07:00:00 PM, Wed 07/10/2024");
        createEventRequestDto.setStartingDate("10:00:00 PM, Wed 07/10/2024");

        Assertions.assertThrows(DateTimeParseException.class, () -> eventService.createEvent(createEventRequestDto));
    }

    @Test
    public void test_create_event_with_invalid_location() {
        CreateEventRequestDto.CreateEventLocationDto invalidLocation  = new CreateEventRequestDto.CreateEventLocationDto();
        invalidLocation.setStreet1("123 Main St");
        createEventRequestDto.setLocation(invalidLocation);

        Assertions.assertThrows(TransactionSystemException.class, () -> eventService.createEvent(createEventRequestDto));
    }
}