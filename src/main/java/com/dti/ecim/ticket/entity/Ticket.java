package com.dti.ecim.ticket.entity;

import com.dti.ecim.event.entity.Event;
import com.dti.ecim.event.entity.EventOffering;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_id_gen")
    @SequenceGenerator(name = "ticket_id_gen", sequenceName = "ticket_id_seq")
    private Long id;

    @ManyToOne
    private Event event;

    @ManyToOne
    private EventOffering eventOffering;
}
