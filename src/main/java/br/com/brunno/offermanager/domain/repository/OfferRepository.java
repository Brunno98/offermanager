package br.com.brunno.offermanager.domain.repository;

import br.com.brunno.offermanager.domain.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    Optional<Offer> findByOfferKey(String offerKey);
}
