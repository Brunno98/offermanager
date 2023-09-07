package br.com.brunno.offermanager.domain.entity;

import lombok.Data;

import java.util.List;

@Data
public class OfferExclusiveRelation {
    private List<Offer> offersRelated;
}
