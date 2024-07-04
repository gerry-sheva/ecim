package com.dti.ecim.trx.entity;

import com.dti.ecim.event.entity.Event;
import com.dti.ecim.user.entity.Attendee;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "event_id")
    private Long eventId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendee_id")
    private Attendee attendee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private Status status;

    @PositiveOrZero
    private Long price;

    @OneToMany(mappedBy = "trx", cascade = CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Tix> tixes = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", insertable = false, updatable = false)
    private Event event;

    public void addTix(Tix t) {
        tixes.add(t);
        t.setTrx(this);
    }
    public void removeTix(Tix t) {
        tixes.remove(t);
        t.setTrx(null);
    }
}
