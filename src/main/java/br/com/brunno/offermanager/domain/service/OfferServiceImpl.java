package br.com.brunno.offermanager.domain.service;


import br.com.brunno.offermanager.domain.Exceptions.RelationAlreadyExistsException;
import br.com.brunno.offermanager.domain.entity.Offer;
import br.com.brunno.offermanager.domain.entity.OfferExclusiveRelation;
import br.com.brunno.offermanager.domain.repository.OfferExclusiveRelationRepository;
import br.com.brunno.offermanager.domain.repository.OfferRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService{

    private final OfferRepository offerRepository;
    private final OfferExclusiveRelationRepository offerExclusiveRelationRepository;

    @Override
    public Offer getById(Long id) {
        return offerRepository.findById(id).orElseThrow(() -> new RuntimeException("Offer not found"));
    }

    @Override
    public void create(Offer offer) {
        offerRepository.save(offer);
    }

    @Override
    public OfferExclusiveRelation getRelatedOffersToOffer(String key) {
        List<Offer> relatedOffers = offerExclusiveRelationRepository.getRelatedOffersFromOfferKey(key);
        OfferExclusiveRelation offerExclusiveRelation = new OfferExclusiveRelation();
        offerExclusiveRelation.setOffersRelated(relatedOffers);
        return offerExclusiveRelation;
    }

    @Transactional
    @Override
    public void createRelation(String offerKey, List<String> relatedOffers) {
        Offer offer = offerRepository.findByOfferKey(offerKey).orElseThrow(() -> new RuntimeException("Offer Not Found"));
        for (String otherOfferKey : relatedOffers) {
            var relation = offerExclusiveRelationRepository.findRelationBetweenOffers(offerKey, otherOfferKey);
            if (!relation.isEmpty()) {
                log.warn("Relation between {} and {} already exists!", offerKey, otherOfferKey);
                continue;
            }
            Optional<Offer> possibleOffer = offerRepository.findByOfferKey(otherOfferKey);
            if (possibleOffer.isEmpty())
                continue;
            String uuid = UUID.randomUUID().toString();
            Offer otherOffer = possibleOffer.get();
            log.info("Offer 1 id: {}", offer.getId());
            log.info("Offer 2 id: {}", otherOffer.getId());
            offerExclusiveRelationRepository.createRelationForOffers(offer.getId(), otherOffer.getId(), uuid);
        }
    }
}
