package br.com.brunno.offermanager.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Data
@Entity
@Table(name = "offer_exclusive_relation")
public class OfferUnicityRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToMany
    @JoinTable(
            name = "offer_unicity_relation_offer",
            joinColumns = @JoinColumn(name = "offer_exclusive_relation_id"),
            inverseJoinColumns = @JoinColumn(name = "offer_id")
    )
    private List<Offer> offers;

    public OfferUnicityRelation() {
        this.offers = new ArrayList<>();
    }

    public Offer getOfferRelatedWith(String key) {
        return offers.stream().filter(o -> !o.getOfferKey().equals(key)).findFirst()
                .orElseThrow(() -> new RuntimeException(
                        format("Relation %s retrieved with corrupted offers relation", id)
                ));
    }

    public void addOffer(Offer offer) {
        if (offers.size() >= 2)
            throw new RuntimeException("cannot add more than 2 offers in some unicity relation");

        offers.add(offer);
    }

    public boolean hasOfferKey(String offerKey) {
        if (offerKey == null) return false;

        return offers.stream().anyMatch(o -> offerKey.equals(o.getOfferKey()));
    }
}