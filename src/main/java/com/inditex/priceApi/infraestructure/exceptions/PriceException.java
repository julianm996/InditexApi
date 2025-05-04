package com.inditex.priceApi.infraestructure.exceptions;

public class PriceException extends RuntimeException {
    public PriceException(String message) {
        super(message);
    }
}
