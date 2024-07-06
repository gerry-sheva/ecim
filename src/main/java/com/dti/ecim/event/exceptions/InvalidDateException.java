package com.dti.ecim.event.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@EqualsAndHashCode(callSuper = false)
@Setter
@Data
public class InvalidDateException extends RuntimeException{
    public InvalidDateException(String message) {
        super(message);
    }
}
