package com.dti.ecim.discount.entity;

import com.dti.ecim.user.entity.Attendee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "point")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "point_id_gen")
    @SequenceGenerator(name = "point_id_gen", sequenceName = "point_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "attendee_id")
    private Long attendeeId;

    private int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendee_id", insertable = false, updatable = false)
    private Attendee attendee;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "expired_at")
    private Instant expiredAt;
}
