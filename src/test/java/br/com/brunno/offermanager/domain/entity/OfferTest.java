package br.com.brunno.offermanager.domain.entity;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OfferTest {
    static final String OFFER_KEY = "aaa";
    static final String OTHER_OFFER_KEY = "bbb";
    Offer.OfferBuilder aOffer = Offer.builder()
            .id(1L).offerKey(OFFER_KEY);
    Offer.OfferBuilder anotherOffer = Offer.builder()
            .id(2L).offerKey(OTHER_OFFER_KEY);

    @Test
    void givenAOfferWithRelationThenHasRelationWhithOtherOfferShouldReturnTrue() {
        Offer offer = aOffer.build();
        Offer otherOffer = anotherOffer.build();
        OfferUnicityRelation unicityRelation = new OfferUnicityRelation();
        unicityRelation.addOffer(offer);
        unicityRelation.addOffer(otherOffer);
        offer.setUnicityRelations(List.of(unicityRelation));

        boolean hasUnicityRelation = offer.hasUnicityRelationWith(OTHER_OFFER_KEY);

        assertTrue(hasUnicityRelation);
    }
}