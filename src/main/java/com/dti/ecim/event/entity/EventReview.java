package com.dti.ecim.event.entity;

import com.dti.ecim.user.entity.Attendee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "event_review")
public class EventReview {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_review_id_gen")
    @SequenceGenerator(name = "event_review_id_gen", sequenceName = "event_review_id_seq")
    private Long id;

    private int rating;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendee_id")
    private Attendee attendee;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventReview that = (EventReview) o;
        return rating == that.rating && Objects.equals(id, that.id) && Objects.equals(comment, that.comment) && Objects.equals(event, that.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rating, comment, event);
    }
}
