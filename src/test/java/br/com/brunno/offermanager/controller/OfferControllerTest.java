package br.com.brunno.offermanager.controller;

import br.com.brunno.offermanager.controller.dto.CreateOfferPayload;
import br.com.brunno.offermanager.domain.entity.Offer;
import br.com.brunno.offermanager.domain.entity.OfferUnicityRelation;
import br.com.brunno.offermanager.domain.service.OfferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class OfferControllerTest {
    public static final ObjectMapper OM = new ObjectMapper();
    public static final Long OFFER_ID = 1L;
    public static final Long OTHER_OFFER_ID = 2L;
    public static final String OFFER_KEY = "aaa";
    public static final String OTHER_OFFER_KEY = "bbb";
    public static final String RELATION_ID = "relationId";



    @Autowired
    MockMvc mockMvc;

    @MockBean
    OfferService offerService;

    Offer.OfferBuilder aOffer = Offer.builder()
            .id(OFFER_ID)
            .offerKey(OFFER_KEY);

    @Test
    void getOfferShouldReturnOkAndOffer() throws Exception {
        Offer offer = aOffer.build();
        doReturn(offer).when(offerService).getById(OFFER_ID);

        mockMvc.perform(get("/offer/{id}", OFFER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(OFFER_ID))
                .andExpect(jsonPath("offerKey").value(OFFER_KEY));
    }

    @Test
    void postOfferShouldReturn201() throws Exception {
        CreateOfferPayload createOfferPayload = new CreateOfferPayload();
        createOfferPayload.setOfferKey(OFFER_KEY);

        mockMvc.perform(post("/offer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OM.writeValueAsString(createOfferPayload)))
                .andExpect(status().isCreated());
    }

    @Test
    void getOfferUnicityRelationFromOfferKeyShouldReturn200() throws Exception {
        Offer offer  = aOffer.id(OFFER_ID).offerKey(OFFER_KEY).build();
        Offer other_offer  = aOffer.id(OTHER_OFFER_ID).offerKey(OTHER_OFFER_KEY).build();
        OfferUnicityRelation unicityRelation = OfferUnicityRelation.createRelationBetween(offer, other_offer);
        unicityRelation.setId(RELATION_ID);
        doReturn(List.of(other_offer)).when(offerService).getRelatedOffersToOffer(OFFER_KEY);

        mockMvc.perform(get("/offer/{key}/unicity-relation", OFFER_KEY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("offersRelated").isArray())
                .andExpect(jsonPath("offersRelated[0].offerKey").value(OTHER_OFFER_KEY))
                .andExpect(jsonPath("offersRelated[0].relationId").exists());
    }

    @Test
    void deleteUnicityRelationShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/offer/unicity-relation/{id}", RELATION_ID))
                .andExpect(status().isNoContent());
    }
}
