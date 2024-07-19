package com.dti.ecim.discount.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("GLOBAL")
public class GlobalDiscount extends Discount {

}
