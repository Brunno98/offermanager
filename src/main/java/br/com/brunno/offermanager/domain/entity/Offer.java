package br.com.brunno.offermanager.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
}
