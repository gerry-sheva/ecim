package com.dti.ecim.discount.entity;

import com.dti.ecim.user.entity.Attendee;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "claimed_discount")
public class ClaimedDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "claimed_discount_id_gen")
    @SequenceGenerator(name = "claimed_discount_id_gen", sequenceName = "claimed_discount_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "attendee_id")
    private Long attendeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendee_id", insertable = false, updatable = false)
    private Attendee attendee;

    @Column(name = "discount_id")
    private Long discountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id", insertable = false, updatable = false)
    private Discount discount;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @NotNull
    private Instant expiredAt;
}
