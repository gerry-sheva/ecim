package com.dti.ecim.tix.entity;

import com.dti.ecim.event.entity.EventOffering;
import com.dti.ecim.trx.entity.Trx;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tix")
public class Tix {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tix_id_gen")
    @SequenceGenerator(name = "tix_id_gen", sequenceName = "tix_id_seq")
    private Long id;

    @NotBlank
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_offering_id")
    private EventOffering eventOffering;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trx_id")
    private Trx trx;
}
