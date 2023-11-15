package br.com.brunno.offermanager.domain.service;


import br.com.brunno.offermanager.domain.entity.Offer;
import br.com.brunno.offermanager.domain.entity.OfferUnicityRelation;
import br.com.brunno.offermanager.domain.exceptions.OfferNotFoundException;
import br.com.brunno.offermanager.domain.repository.OfferRepository;
import br.com.brunno.offermanager.domain.repository.OfferUnicityRelationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService{

    private final OfferRepository offerRepository;
    private final OfferUnicityRelationRepository offerUnicityRelationRepository;

    @Override
    public Offer getById(Long id) {
        return offerRepository.findById(id).orElseThrow(() -> new OfferNotFoundException("Offer not found"));
    }

    @Override
    public void create(Offer offer) {
        offerRepository.save(offer);
    }

    @Override
    public List<Offer> getRelatedOffersToOffer(String key) {
        Offer offer = offerRepository.findByOfferKey(key).orElseThrow(() -> new RuntimeException("Offer not found"));
        List<OfferUnicityRelation> unicityRelations = offer.getUnicityRelations();

        List<Offer> relatedOffers = new ArrayList<>();
        for (OfferUnicityRelation relation : unicityRelations) {
            Offer relatedOffer = relation.getOfferRelatedWith(key);
            relatedOffers.add(relatedOffer);
        }
        return relatedOffers;
    }

    @Transactional
    @Override
    public void createRelation(String offerKey, List<String> relatedOffers) {
        Offer offer = offerRepository.findByOfferKey(offerKey).orElseThrow(() -> new RuntimeException("Offer Not Found"));
        for (String otherOfferKey : relatedOffers) {
            if (offer.hasUnicityRelationWith(otherOfferKey)) {
                log.warn("Relation between {} and {} already exists!", offerKey, otherOfferKey);
                continue;
            }
            Optional<Offer> possibleOffer = offerRepository.findByOfferKey(otherOfferKey);
            if (possibleOffer.isEmpty())
                continue;
            OfferUnicityRelation unicityRelation = new OfferUnicityRelation();
            unicityRelation.addOffer(offer);
            unicityRelation.addOffer(possibleOffer.get());

            offerUnicityRelationRepository.save(unicityRelation);
        }
    }

    @Override
    public void deleteRelation(String relationId) {
        offerUnicityRelationRepository.deleteById(relationId);
    }

    @Override
    public void deleteOffer(Long id) {
        offerRepository.deleteById(id);
    }
}
