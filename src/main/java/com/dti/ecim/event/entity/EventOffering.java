package com.dti.ecim.event.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
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
    private int price;

    @Positive
    private int capacity;

    @PositiveOrZero
    private int availability;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventOffering that = (EventOffering) o;
        return capacity == that.capacity && availability == that.availability && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(price, that.price) && Objects.equals(event, that.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, capacity, availability, event);
    }
}
