package br.com.brunno.offermanager.domain.service;

import br.com.brunno.offermanager.domain.entity.Offer;
import br.com.brunno.offermanager.domain.entity.OfferUnicityRelation;
import br.com.brunno.offermanager.domain.repository.OfferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OfferServiceImplTest {

    @InjectMocks
    OfferServiceImpl service;

    @Mock
    OfferRepository repository;

    @Test
    void whenRelationAlreadyExistsShouldNotCreateNew() {
        Offer offer1 = new Offer(1L, "first");
        Offer offer2 = new Offer(2L, "second");
        String relationId = "relation_id";
        OfferUnicityRelation relationLeft = new OfferUnicityRelation();
        relationLeft.setOfferId(offer1.getId());
        relationLeft.setId(relationId);
        OfferUnicityRelation relationRight = new OfferUnicityRelation();
        relationRight.setOfferId(offer2.getId());
        relationRight.setId(relationId);
        List<OfferUnicityRelation> relation = List.of(relationLeft, relationRight);
//        doReturn(relation).when(relationRepository).findRelationBetweenOffers(offer1.getOfferKey(), offer2.getOfferKey());
        doReturn(Optional.of(offer1)).when(repository).findByOfferKey(offer1.getOfferKey());

        service.createRelation(offer1.getOfferKey(), List.of(offer2.getOfferKey()));

//        verify(relationRepository, times(0)).createRelationForOffers(eq(offer1.getId()), eq(offer2.getId()), anyString());
    }

}