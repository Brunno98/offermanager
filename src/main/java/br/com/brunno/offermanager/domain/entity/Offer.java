package br.com.brunno.offermanager.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
        //TODO: verifica se key informada está na lista de relacoes
        return false;
    }
}
