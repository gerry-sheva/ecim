package com.dti.ecim.discount.entity;

import com.dti.ecim.user.entity.Organizer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "discount")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "discount_id_gen")
    @SequenceGenerator(name = "discount_id_gen", sequenceName = "discount_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "type", insertable = false, updatable = false)
    private String type;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "description")
    private String description;

    @Column(name = "amountFlat")
    private int amountFlat;

    @Column(name = "amountPercent")
    private int amountPercent;

    @NotBlank
    @Column(name = "code", unique = true)
    private String code;

    @Positive
    @Column(name = "expiresInDays")
    private int expiresInDays;

    @NotNull
    @Column(name = "expiredAt")
    private Instant expiredAt;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;
}
