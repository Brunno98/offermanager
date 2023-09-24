package br.com.brunno.offermanager.domain.exceptions;

public class OfferNotFoundException extends RuntimeException {

    public OfferNotFoundException(String message) {
        super(message);
    }
}
