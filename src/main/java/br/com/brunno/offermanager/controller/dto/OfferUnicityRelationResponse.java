package br.com.brunno.offermanager.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class OfferUnicityRelationResponse {
    private List<UnicityRelationResponse> OffersRelated;
}
