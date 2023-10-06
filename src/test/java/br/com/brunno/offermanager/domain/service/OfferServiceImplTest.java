package br.com.brunno.offermanager.domain.service;

import br.com.brunno.offermanager.domain.entity.Offer;
import br.com.brunno.offermanager.domain.entity.OfferUnicityRelation;
import br.com.brunno.offermanager.domain.repository.OfferRepository;
import br.com.brunno.offermanager.domain.repository.OfferUnicityRelationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OfferServiceImplTest {

    @InjectMocks
    OfferServiceImpl service;

    @Mock
    OfferRepository offerRepository;

    @Mock
    OfferUnicityRelationRepository unicityRelationRepository;

    @Test
    void whenRelationAlreadyExistsShouldNotCreateNew() {
        Offer offer1 = new Offer(1L, "first");
        Offer offer2 = new Offer(2L, "second");
        OfferUnicityRelation unicityRelation = new OfferUnicityRelation();
        unicityRelation.addOffer(offer1);
        unicityRelation.addOffer(offer2);
        offer1.setUnicityRelations(List.of(unicityRelation));
        offer2.setUnicityRelations(List.of(unicityRelation));


        doReturn(Optional.of(offer1)).when(offerRepository).findByOfferKey(offer1.getOfferKey());

        service.createRelation(offer1.getOfferKey(), List.of(offer2.getOfferKey()));

        verify(unicityRelationRepository, times(0)).save(any());
    }

}