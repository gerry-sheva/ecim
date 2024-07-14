package com.dti.ecim.discount.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@EqualsAndHashCode(callSuper = false)
@Setter
@Data
public class InvalidDiscountException extends RuntimeException {
    public InvalidDiscountException(String message) {
        super(message);
    }
}
