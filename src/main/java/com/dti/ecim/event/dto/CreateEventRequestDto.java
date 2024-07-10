package com.dti.ecim.event.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateEventRequestDto {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String startingDate;

    @NotBlank
    private String endingDate;

    @NotBlank
    private Long categoryId;

    @NotBlank
    private Long interestId;

    @NotNull
    private CreateEventLocationDto location;

    @NotNull
    private List<CreateEventOfferingDto> offerings;

    @Data
    public static class CreateEventLocationDto {
        @NotBlank
        private String street1;

        @NotBlank
        private String street2;

        @NotBlank
        private String city;

        @NotBlank
        private String state;

        public CreateEventLocationDto() {
            this.street1 = "";
            this.street2 = "";
            this.city = "";
            this.state = "";
        }
    }

    @Data
    @NoArgsConstructor
    public static class CreateEventOfferingDto {
        @NotBlank
        private String name;

        @NotBlank
        private String description;

        @PositiveOrZero
        private int price;

        @Positive
        private int capacity;
    }
}
