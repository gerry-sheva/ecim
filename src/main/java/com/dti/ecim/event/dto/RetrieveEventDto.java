package com.dti.ecim.event.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
public class RetrieveEventDto {
    private String title;

    private String description;

    private Instant startingDate;

    private Instant endingDate;

    private LocationDto location;

    private InterestDto interest;

    private Set<OfferingDto> offerings;

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
        private String name;

        public CategoryDto() {
            this.name = "";
        }
    }

    @Data
    public static class InterestDto {
        private String name;
        private CategoryDto category;

        public InterestDto() {
            this.name = "";
            this.category = new CategoryDto();
        }
    }

    @Data
    public static class OfferingDto {
        private String name;
        private String description;
        private Long price;
        private int capacity;
        private int availability;

        public OfferingDto() {
            this.name = "";
            this.description = "";
            this.price = 0L;
            this.capacity = 0;
            this.availability = 0;
        }
    }
}
