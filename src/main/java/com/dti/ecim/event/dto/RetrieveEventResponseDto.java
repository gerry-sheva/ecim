package com.dti.ecim.event.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
public class RetrieveEventResponseDto {
    private String title;

    private String description;

    private Instant startingDate;

    private Instant endingDate;

    private int price;

    private OrganizerDto organizer;

    private LocationDto location;

    private CategoryDto category;

    private InterestDto interest;

    private Set<OfferingDto> offerings;

    @Data
    public static class OrganizerDto {
        private Long id;
        private String name;
        private String avatar;

        public OrganizerDto() {
            this.id = null;
            this.name = "";
            this.avatar = "";
        }
    }

    @Data
    public static class LocationDto {
        private String street1;
        private String street2;
        private String city;
        private String state;

        public LocationDto() {
            this.street1 = "";
            this.street2 = "";
            this.city = "";
            this.state = "";
        }
    }

    @Data
    public static class CategoryDto {
        private Long id;
        private String name;

        public CategoryDto() {
            this.id = null;
            this.name = "";
        }
    }

    @Data
    public static class InterestDto {
        private Long id;
        private String name;

        public InterestDto() {
            this.id = null;
            this.name = "";
        }
    }

    @Data
    public static class OfferingDto {
        private String name;
        private String description;
        private int price;
        private int capacity;
        private int availability;

        public OfferingDto() {
            this.name = "";
            this.description = "";
            this.price = 0;
            this.capacity = 0;
            this.availability = 0;
        }
    }
}
