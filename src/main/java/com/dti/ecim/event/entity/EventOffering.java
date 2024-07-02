package com.dti.ecim.event.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "event_offering")
public class EventOffering {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offering_id_gen")
    @SequenceGenerator(name = "offering_id_gen", sequenceName = "offering_id_seq", allocationSize = 1)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @PositiveOrZero
    private Long price;

    @Positive
    private int capacity;

    @PositiveOrZero
    private int availability;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

}
