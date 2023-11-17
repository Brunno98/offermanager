package br.com.brunno.offermanager.controller;

import br.com.brunno.offermanager.controller.dto.ErrorResponse;
import br.com.brunno.offermanager.domain.exceptions.OfferAlreadyExists;
import br.com.brunno.offermanager.domain.exceptions.OfferNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(OfferAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleOfferAlreadyExists(OfferAlreadyExists ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("offer already exists"));
    }

    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleOfferNotFound(OfferNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("offer not found"));
    }

}
