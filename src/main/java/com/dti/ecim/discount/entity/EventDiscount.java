package com.dti.ecim.discount.entity;

import com.dti.ecim.event.entity.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("EVENT")
public class EventDiscount extends Discount {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;
}
