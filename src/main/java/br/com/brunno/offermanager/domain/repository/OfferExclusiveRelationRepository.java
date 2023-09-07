package br.com.brunno.offermanager.domain.repository;

import br.com.brunno.offermanager.domain.entity.Offer;
import br.com.brunno.offermanager.domain.entity.OfferExclusiveRelationId;
import br.com.brunno.offermanager.domain.entity.OfferExclusiveRelationORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfferExclusiveRelationRepository extends JpaRepository<OfferExclusiveRelationORM, OfferExclusiveRelationId> {
//    @Query(value = "select o.id, o.offer_key from offer_exclusive_relation r " +
//            "join offer o on r.offer_id = o.id " +
//            "where o.offer_key != :key and " +
//            "r.id in (" +
//                "select r.id from offer_exclusive_relation r " +
//                "join offer o on r.offer_id = o.id " +
//                "where o.offer_key = :key" +
//            ");")
//    List<Offer> getRelatedOffersFromOfferKey(@Param("key") String key);

    @Query(value = "select o from OfferExclusiveRelationORM r " +
            "join Offer o on r.offerId = o.id " +
            "where o.offerKey != :key and " +
            "r.id in (" +
            "select r.id from OfferExclusiveRelationORM r " +
            "join Offer o on r.offerId = o.id " +
            "where o.offerKey = :key" +
            ")")
    List<Offer> getRelatedOffersFromOfferKey(@Param("key") String key);

    @Modifying
    @Query(value = "INSERT INTO offer_exclusive_relation (id, offer_id) values " +
            "(:relationId, :offer1), (:relationId, :offer2)", nativeQuery = true)
    void createRelationForOffers(@Param("offer1") Long offerId1, @Param("offer2") Long offerId2,
                                 @Param("relationId") String relationId);
}