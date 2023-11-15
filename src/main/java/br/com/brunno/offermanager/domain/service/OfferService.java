package br.com.brunno.offermanager.domain.service;

import br.com.brunno.offermanager.domain.entity.Offer;

import java.util.List;

public interface OfferService {
    Offer getById(Long id);

    void create(Offer offer);

    List<Offer> getRelatedOffersToOffer(String key);

    void createRelation(String offer, List<String> relatedOffers);

    void deleteRelation(String relationId);

    void deleteOffer(Long id);
}
