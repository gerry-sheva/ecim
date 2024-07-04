package com.dti.ecim.trx.entity;

import com.dti.ecim.event.entity.EventOffering;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

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
    private Trx trx;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tix tix = (Tix) o;
        return Objects.equals(id, tix.id) && Objects.equals(code, tix.code) && Objects.equals(eventOffering, tix.eventOffering);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, eventOffering);
    }
}
