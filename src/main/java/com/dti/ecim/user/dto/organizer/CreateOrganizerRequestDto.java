package com.dti.ecim.user.dto.organizer;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateOrganizerRequestDto {
    @NotBlank
    private String name;

    private String avatar;
}
