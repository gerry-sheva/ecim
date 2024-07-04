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
@Table(name = "claimed_discount")
public class ClaimedDiscount {
    @Id
    @Column(name = "redeemed_discount_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "redeemed_discount_id")
    private RedeemedDiscount redeemedDiscount;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;
}
