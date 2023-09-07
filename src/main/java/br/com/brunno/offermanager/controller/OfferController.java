package br.com.brunno.offermanager.controller;

import br.com.brunno.offermanager.controller.dto.CreateExclusiveRelationOfferDto;
import br.com.brunno.offermanager.domain.entity.OfferExclusiveRelation;
import br.com.brunno.offermanager.controller.dto.OfferExclusiveRelationResponseDto;
import br.com.brunno.offermanager.domain.entity.Offer;
import br.com.brunno.offermanager.domain.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/{key}/exclusive-relation")
    public ResponseEntity<OfferExclusiveRelationResponseDto> getExclusiveOffersRelatedToOffer(@PathVariable String key) {
        OfferExclusiveRelation offerExclusiveRelation = offerService.getRelatedOffersToOffer(key);

        List<OfferResponseDto> offersDto = new ArrayList<>();
        for (Offer offer : offerExclusiveRelation.getOffersRelated()) {
            OfferResponseDto offerDto = new OfferResponseDto();
            BeanUtils.copyProperties(offer, offerDto);
            offersDto.add(offerDto);
        }

        OfferExclusiveRelationResponseDto offerExclusiveRelationResponseDto = new OfferExclusiveRelationResponseDto();
        offerExclusiveRelationResponseDto.setOffersRelated(offersDto);

        return ResponseEntity.ok(offerExclusiveRelationResponseDto);
    }

    @PostMapping("/exclusive-relation")
    public ResponseEntity<?> createRelation(@RequestBody CreateExclusiveRelationOfferDto createRelationsDto) {
        offerService.createRelation(createRelationsDto.getOffer(), createRelationsDto.getRelateWith());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
