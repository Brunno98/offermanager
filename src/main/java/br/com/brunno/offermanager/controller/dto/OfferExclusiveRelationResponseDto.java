package br.com.brunno.offermanager.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class OfferExclusiveRelationResponseDto {
    private List<OfferResponseDto> OffersRelated;
}
