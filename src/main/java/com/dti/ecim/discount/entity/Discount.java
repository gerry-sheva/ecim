package com.dti.ecim.discount.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private Long amount_flat;

    private int amount_percent;

    @NotBlank
    private String code;

}
