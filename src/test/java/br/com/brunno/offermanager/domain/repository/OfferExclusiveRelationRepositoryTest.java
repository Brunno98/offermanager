package br.com.brunno.offermanager.domain.repository;

import br.com.brunno.offermanager.domain.entity.Offer;
import br.com.brunno.offermanager.domain.entity.OfferExclusiveRelationORM;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@DataJpaTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class OfferExclusiveRelationRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    OfferExclusiveRelationRepository offerExclusiveRelationRepository;

    @Test
    void getOffersRelatedToKeyShouldReturnAListOfOffers() {
        Offer[] offers = new Offer[]{
                new Offer(null, "aaa"),
                new Offer(null, "bbb"),
                new Offer(null, "ccc"),
                new Offer(null, "ddd"),
        };
        for (Offer offer : offers)
            entityManager.persist(offer);

        String uuid = "ac7ecdfb-66bf-473a-ab52-dba293985b7b";
        OfferExclusiveRelationORM relation1 = new OfferExclusiveRelationORM();
        relation1.setOfferId(1L);
        relation1.setId(uuid);

        OfferExclusiveRelationORM relation2 = new OfferExclusiveRelationORM();
        relation2.setOfferId(2L);
        relation2.setId(uuid);

        entityManager.persist(relation1);
        entityManager.persist(relation2);

        List<Offer> relatedOffers = offerExclusiveRelationRepository.getRelatedOffersFromOfferKey("aaa");

        assertThat(relatedOffers.get(0).getOfferKey(), equalTo("bbb"));

    }

    @Test
    void given2OffersShoudCreateRelationForThem() {
        Offer[] offers = new Offer[]{
                new Offer(null, "aaa"),
                new Offer(null, "bbb"),
        };
        for (Offer offer : offers)
            entityManager.persist(offer);

        String uuid = "ac7ecdfb-66bf-473a-ab52-dba293985b7b";
        offerExclusiveRelationRepository.createRelationForOffers(offers[0].getId(), offers[1].getId(), uuid);

        List<Offer> relateds = offerExclusiveRelationRepository.getRelatedOffersFromOfferKey("aaa");
        Offer relatedOffer = relateds.get(0);

        assertThat(relatedOffer.getOfferKey(), equalTo("bbb"));
    }
}
