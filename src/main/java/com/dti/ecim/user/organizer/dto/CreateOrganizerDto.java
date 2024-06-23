package com.dti.ecim.user.organizer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateOrganizerDto {
    @NotBlank
    private String name;

    private String avatar;
}
