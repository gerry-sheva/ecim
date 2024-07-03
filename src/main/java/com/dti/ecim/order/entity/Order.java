package com.dti.ecim.order.entity;

import com.dti.ecim.ticket.entity.Ticket;
import com.dti.ecim.user.entity.Attendee;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_gen")
    @SequenceGenerator(name = "order_id_gen", sequenceName = "order_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "attendee_id")
    private Attendee attendee;

    @NotBlank
    private String status;

    @OneToMany
    @JoinColumn(name = "order_id")
    private Set<Ticket> tickets;
}
