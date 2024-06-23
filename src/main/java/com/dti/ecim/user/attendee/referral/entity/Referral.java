package com.dti.ecim.user.attendee.referral.entity;

import com.dti.ecim.user.attendee.entity.Attendee;
import com.dti.ecim.user.attendee.referral.entity.key.ReferralCompositeKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "referral")
@IdClass(ReferralCompositeKey.class)
public class Referral {
    @Id
    @Column(name = "referral_id")
    private Long referralId;

    @Id
    @Column(name = "referree_id")
    private Long referreeId;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referral_id", referencedColumnName = "userId", insertable = false, updatable = false)
    private Attendee referral;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referree_id", referencedColumnName = "userId", insertable = false, updatable = false)
    private Attendee referree;
}
