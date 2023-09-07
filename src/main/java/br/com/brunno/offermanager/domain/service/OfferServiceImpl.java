package br.com.brunno.offermanager.domain.service;


import br.com.brunno.offermanager.domain.entity.Offer;
import br.com.brunno.offermanager.domain.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService{

    private final OfferRepository offerRepository;

    @Override
    public Offer getById(Long id) {
        return offerRepository.findById(id).orElseThrow(() -> new RuntimeException("Offer not found"));
    }

    @Override
    public void create(Offer offer) {
        offerRepository.save(offer);
    }
}
