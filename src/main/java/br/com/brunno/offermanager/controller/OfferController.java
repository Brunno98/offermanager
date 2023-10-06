package br.com.brunno.offermanager.controller;

import br.com.brunno.offermanager.controller.dto.CreateOfferPayload;
import br.com.brunno.offermanager.controller.dto.CreateUnicityOfferRelationDto;
import br.com.brunno.offermanager.controller.dto.OfferResponseDto;
import br.com.brunno.offermanager.controller.dto.OfferUnicityRelationResponse;
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

        List<OfferResponseDto> offersDto = new ArrayList<>();
        for (Offer offer : offers) {
            OfferResponseDto offerDto = new OfferResponseDto();
            BeanUtils.copyProperties(offer, offerDto);
            offersDto.add(offerDto);
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
}
