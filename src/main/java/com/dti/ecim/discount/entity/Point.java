package com.dti.ecim.discount.entity;

import com.dti.ecim.trx.entity.Trx;
import com.dti.ecim.user.entity.Attendee;
import com.dti.ecim.user.entity.Referral;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigInteger;
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

    private BigInteger amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendee_id")
    private Attendee attendee;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "expired_at")
    private Instant expiredAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trx_id")
    private Trx trx;
}
