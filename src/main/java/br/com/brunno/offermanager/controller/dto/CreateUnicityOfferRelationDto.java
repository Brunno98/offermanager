package br.com.brunno.offermanager.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateUnicityOfferRelationDto {
    private String offer;
    private List<String> relateWith;
}
