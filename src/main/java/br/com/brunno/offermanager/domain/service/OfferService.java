package br.com.brunno.offermanager.domain.service;

import br.com.brunno.offermanager.domain.entity.Offer;
import br.com.brunno.offermanager.domain.entity.OfferExclusiveRelation;

import java.util.List;

public interface OfferService {
    Offer getById(Long id);

    void create(Offer offer);

    OfferExclusiveRelation getRelatedOffersToOffer(String key);

    void createRelation(String offer, List<String> relatedOffers);
}
