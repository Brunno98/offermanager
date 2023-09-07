package br.com.brunno.offermanager.controller;

import br.com.brunno.offermanager.domain.entity.Offer;
import br.com.brunno.offermanager.domain.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("offer")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @GetMapping("{id}")
    public ResponseEntity<OfferResponseDto> getOfferById(@PathVariable Long id) {
        Offer offer = offerService.getById(id);
        OfferResponseDto dto = new OfferResponseDto();
        BeanUtils.copyProperties(offer, dto);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<Void> createOffer(@RequestBody CreateOfferPayload createOffer) {
        Offer offer = new Offer();
        BeanUtils.copyProperties(createOffer, offer);
        offerService.create(offer);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
