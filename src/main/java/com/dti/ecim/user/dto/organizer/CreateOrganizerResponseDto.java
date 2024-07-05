package com.dti.ecim.user.dto.organizer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrganizerResponseDto {
    private String name;
    private String role;
}
