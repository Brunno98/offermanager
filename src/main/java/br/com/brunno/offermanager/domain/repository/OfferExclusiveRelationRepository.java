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

    @Query(value = "select r1 from OfferExclusiveRelationORM r1 " +
            "join OfferExclusiveRelationORM r2 on r1.id = r2.id " +
            "join Offer o1 on r1.offerId = o1.id " +
            "join Offer o2 on r2.offerId = o2.id " +
            "where o1.offerKey = :key1 and o2.offerKey = :key2 or o1.offerKey = :key2 and o2.offerKey = :key1 ")
    List<OfferExclusiveRelationORM> findRelationBetweenOffers(@Param("key1") String key1,
                                                              @Param("key2") String key2);
}