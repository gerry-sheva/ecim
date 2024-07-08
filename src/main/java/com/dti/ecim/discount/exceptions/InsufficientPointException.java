package com.dti.ecim.discount.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@EqualsAndHashCode(callSuper = false)
@Setter
@Data
public class InsufficientPointException extends RuntimeException{
    public InsufficientPointException(String message) {
        super(message);
    }
}
