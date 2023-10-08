package br.com.brunno.offermanager.controller;

import br.com.brunno.offermanager.controller.dto.*;
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

    @GetMapping("/{key}/unicity-relation")
    public ResponseEntity<OfferUnicityRelationResponse> getOffersUnicityRelatedToOffer(@PathVariable String key) {
        List<Offer> offers = offerService.getRelatedOffersToOffer(key);

        List<UnicityRelationResponse> offersDto = new ArrayList<>();
        for (Offer offer : offers) {
            UnicityRelationResponse  unicityRelationResponse = new UnicityRelationResponse();
            unicityRelationResponse.setOfferId(offer.getId());
            unicityRelationResponse.setOfferKey(offer.getOfferKey());
            unicityRelationResponse.setRelationId(offer.getIdFromRelationWith(key));
            offersDto.add(unicityRelationResponse);
        }

        OfferUnicityRelationResponse offerUnicityRelationResponse = new OfferUnicityRelationResponse();
        offerUnicityRelationResponse.setOffersRelated(offersDto);

        return ResponseEntity.ok(offerUnicityRelationResponse);
    }

    @PostMapping("/unicity-relation")
    public ResponseEntity<?> createRelation(@RequestBody CreateUnicityOfferRelationDto createRelationsDto) {
        offerService.createRelation(createRelationsDto.getOffer(), createRelationsDto.getRelateWith());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/unicity-relation/{id}")
    public ResponseEntity<Void> deleteRelation(@PathVariable(name = "id") String relationId) {
        offerService.deleteRelation(relationId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
