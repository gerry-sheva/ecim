package com.dti.ecim.event.entity;

import com.dti.ecim.user.entity.Organizer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_id_gen")
    @SequenceGenerator(name = "event_id_gen", sequenceName = "event_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "organizer_id")
    private Long organizerId;

    @NotBlank
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "starting_date", nullable = false)
    private Instant startingDate;

    @NotNull
    @Column(name = "ending_date", nullable = false)
    private Instant endingDate;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id", insertable = false, updatable = false)
    private Organizer organizer;

    @OneToOne(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private EventLocation location;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "interest_id")
    private Interest interest;

    @OneToMany(mappedBy = "event",cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<EventOffering> offerings = new HashSet<>();

    public void addOffering(EventOffering offering) {
        offerings.add(offering);
        offering.setEvent(this);
    }

    public void removeOffering(EventOffering offering) {
        offerings.remove(offering);
        offering.setEvent(null);
    }

    public void addLocation(EventLocation location) {
        this.location = location;
        location.setEvent(this);
    }

    public void removeLocation(EventLocation location) {
        this.location = null;
        location.setEvent(null);
    }
}
