package com.dti.ecim.event.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class RetrieveEventDto {
    private String title;

    private String description;

    private Instant startingDate;

    private Instant endingDate;

    private LocationDto location;

    private CategoryDto category;

    private InterestDto interest;

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

        public InterestDto() {
            this.name = "";
        }
    }
}
