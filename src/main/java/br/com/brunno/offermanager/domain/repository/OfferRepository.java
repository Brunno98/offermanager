package br.com.brunno.offermanager.domain.repository;

import br.com.brunno.offermanager.domain.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {
}
