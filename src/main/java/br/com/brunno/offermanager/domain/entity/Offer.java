package br.com.brunno.offermanager.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String offerKey;

    @JsonIgnore
    @ManyToMany(mappedBy = "offers")
    private List<OfferUnicityRelation> unicityRelations;

    public Offer(Long id, String key) {
        this.id = id;
        this.offerKey = key;
        this.unicityRelations = new ArrayList<>();
    }

    public boolean hasUnicityRelationWith(String offerKey) {
        return unicityRelations.stream().anyMatch(r -> r.hasOfferKey(offerKey));
    }

    public String getIdFromRelationWith(String key) {
        return unicityRelations.stream()
                .filter(r -> r.hasOfferKey(key))
                .findFirst()
                .orElseThrow(
                        () -> new RuntimeException(format("Offer %s dosn't has relation with %s", this.offerKey, key))
                )
                .getId();
    }

    public void addUnicityRelation(OfferUnicityRelation unicityRelation) {
        //TODO: Write unity tests
        if (!unicityRelation.hasOfferKey(this.offerKey)) {
            String offersRelationsStr = unicityRelation.getOffers().stream()
                    .map(Offer::getOfferKey).collect(Collectors.joining(", "));
            throw new RuntimeException(
                    format("Cannot add unicityRelation in an Offer not related! Offer %s, relationOffers: %s",
                            this.offerKey, offersRelationsStr));
        }

        if (this.unicityRelations ==  null)
            this.unicityRelations = new ArrayList<>();

        this.unicityRelations.add(unicityRelation);
    }
}
