package br.com.brunno.offermanager.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OfferResponseDto {
    private Long id;
    private String offerKey;
}
