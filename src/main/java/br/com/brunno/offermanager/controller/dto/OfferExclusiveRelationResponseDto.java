package br.com.brunno.offermanager.controller.dto;

import br.com.brunno.offermanager.controller.OfferResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class OfferExclusiveRelationResponseDto {
    private List<OfferResponseDto> OffersRelated;
}
