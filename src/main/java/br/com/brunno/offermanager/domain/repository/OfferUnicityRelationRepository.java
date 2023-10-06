package br.com.brunno.offermanager.domain.repository;

import br.com.brunno.offermanager.domain.entity.OfferUnicityRelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferUnicityRelationRepository extends JpaRepository<OfferUnicityRelation, String> {
}
