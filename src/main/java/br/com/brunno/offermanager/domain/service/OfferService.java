package br.com.brunno.offermanager.domain.service;

import br.com.brunno.offermanager.domain.entity.Offer;

public interface OfferService {
    Offer getById(Long id);

    void create(Offer offer);
}
