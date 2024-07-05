package com.dti.ecim.discount.entity;

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
    @Column(name = "claimed_discount_id")
    private Long discountId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "claimed_discount_id")
    private ClaimedDiscount claimedDiscount;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;
}
