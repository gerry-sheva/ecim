package com.dti.ecim.user.attendee.entity;

import com.dti.ecim.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "attendee")
public class Attendee {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank
    @Column(name = "fname")
    private String fname;

    @NotBlank
    @Column(name = "lname")
    private String lname;

    @NotNull
    @Column(name = "dob")
    private LocalDate dob;

    @NotBlank
    @Column(name = "contact")
    private String contact;

    @NotBlank
    @Column(name = "ref_code", unique = true)
    private String refCode;

    @PositiveOrZero
    @Column(name = "points")
    private Long points;
}
