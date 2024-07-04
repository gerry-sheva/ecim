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
@Table(name = "redeemed_discount")
public class RedeemedDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "redeemed_discount_id_gen")
    @SequenceGenerator(name = "redeemed_discount_id_gen", sequenceName = "redeemed_discount_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendee_id")
    private Attendee attendee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;
}
