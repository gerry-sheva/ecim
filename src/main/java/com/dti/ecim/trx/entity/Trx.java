package com.dti.ecim.trx.entity;

import com.dti.ecim.user.entity.Attendee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trx")
public class Trx {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trx_id_gen")
    @SequenceGenerator(name = "trx_id_gen", sequenceName = "trx_id_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendee_id")
    private Attendee attendee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private Status status;

}
