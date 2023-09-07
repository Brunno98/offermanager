package br.com.brunno.offermanager.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@IdClass(OfferExclusiveRelationId.class)
@Table(name = "offer_exclusive_relation")
public class OfferExclusiveRelationORM {
    @Id
    private String id;
    @Id
    private Long offerId;
}