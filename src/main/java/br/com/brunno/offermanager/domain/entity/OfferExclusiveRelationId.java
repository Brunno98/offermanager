package br.com.brunno.offermanager.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferExclusiveRelationId  implements Serializable {
    private String id;
    private Long offerId;
}
