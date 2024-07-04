package com.dti.ecim.discount.entity;

import com.dti.ecim.user.entity.Organizer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("GLOBAL")
public class GlobalDiscount extends Discount {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;
}
