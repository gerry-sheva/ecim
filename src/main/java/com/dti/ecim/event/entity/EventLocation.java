package com.dti.ecim.event.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "event_location")
public class EventLocation {
    @Id
    @Column(name = "event_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "event_id")
    private Event event;

    @NotBlank
    @Column(name = "street1")
    private String street1;

    @NotBlank
    @Column(name = "street2")
    private String street2;

    @NotBlank
    @Column(name = "city")
    private String city;

    @NotBlank
    @Column(name = "state")
    private String state;
}
