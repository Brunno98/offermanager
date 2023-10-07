package br.com.brunno.offermanager.integration;

import br.com.brunno.offermanager.controller.dto.CreateUnicityOfferRelationDto;
import br.com.brunno.offermanager.controller.dto.CreateOfferPayload;
import br.com.brunno.offermanager.controller.dto.OfferUnicityRelationResponse;
import br.com.brunno.offermanager.controller.dto.OfferResponseDto;
import br.com.brunno.offermanager.domain.entity.Offer;
import br.com.brunno.offermanager.domain.repository.OfferRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class OfferIntegrationTest {
    public static final String UNICITY_RELATION_URI = "/offer/unicity-relation";
    public static final String CREATE_UNICIITY_RELATION_URI = "/offer/{key}/unicity-relation";
    public static final String OFFER_KEY = "aaa";
    public static final String OTHER_OFFER_KEY = "bbb";

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    OfferRepository offerRepository;

    @Test
    void CreateOffer() {
        CreateOfferPayload createPayload = new CreateOfferPayload();
        createPayload.setOfferKey(OFFER_KEY);

        ResponseEntity<CreateOfferPayload> createResponse = restTemplate.postForEntity("/offer", createPayload, CreateOfferPayload.class);

        assertThat(createResponse.getStatusCode(), equalTo(HttpStatus.CREATED));


        ResponseEntity<OfferResponseDto> getResponse = restTemplate.getForEntity("/offer/{id}", OfferResponseDto.class, Map.of("id", 1));

        assertThat(getResponse.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(getResponse.getBody().getId(), equalTo(1L));
        assertThat(getResponse.getBody().getOfferKey(), equalTo(OFFER_KEY));
    }

    @Test
    void getUnicityRelationFromOffer() {
        populateBaseWithOffers(OFFER_KEY, OTHER_OFFER_KEY);

        CreateUnicityOfferRelationDto createUnicityOfferRelationDto = new CreateUnicityOfferRelationDto();
        createUnicityOfferRelationDto.setOffer(OFFER_KEY);
        createUnicityOfferRelationDto.setRelateWith(List.of(OTHER_OFFER_KEY));

        ResponseEntity<CreateUnicityOfferRelationDto> postReponse = restTemplate
                .postForEntity(UNICITY_RELATION_URI, createUnicityOfferRelationDto, CreateUnicityOfferRelationDto.class);
        assertThat(postReponse.getStatusCode(), equalTo(HttpStatus.CREATED));

        ResponseEntity<OfferUnicityRelationResponse> getResponse = restTemplate
                .getForEntity(CREATE_UNICIITY_RELATION_URI, OfferUnicityRelationResponse.class, Map.of("key", OFFER_KEY));

        assertThat(getResponse.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(getResponse.getBody().getOffersRelated().get(0).getOfferKey(), equalTo(OTHER_OFFER_KEY));
    }

    private void populateBaseWithOffers(String... offerKeys) {
        for (String key : offerKeys) {
            offerRepository.save(new Offer(null, key));
        }
    }

}
