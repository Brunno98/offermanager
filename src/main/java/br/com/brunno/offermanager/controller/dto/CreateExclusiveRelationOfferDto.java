package br.com.brunno.offermanager.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateExclusiveRelationOfferDto {
    private String offer;
    private List<String> relateWith;
}
