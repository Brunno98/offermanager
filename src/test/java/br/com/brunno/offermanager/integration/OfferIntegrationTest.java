package br.com.brunno.offermanager.integration;

import br.com.brunno.offermanager.controller.CreateOfferPayload;
import br.com.brunno.offermanager.controller.OfferResponseDto;
import br.com.brunno.offermanager.controller.dto.CreateExclusiveRelationOfferDto;
import br.com.brunno.offermanager.controller.dto.OfferExclusiveRelationResponseDto;
import br.com.brunno.offermanager.domain.entity.Offer;
import br.com.brunno.offermanager.domain.repository.OfferRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Map;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class OfferIntegrationTest {
    public static final  ObjectMapper OM = new ObjectMapper();

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    OfferRepository offerRepository;

    @Test
    void CreateOffer() {
        CreateOfferPayload createPayload = new CreateOfferPayload();
        createPayload.setOfferKey("aaa");

        ResponseEntity<CreateOfferPayload> createResponse = restTemplate.postForEntity("/offer", createPayload, CreateOfferPayload.class);

        assertThat(createResponse.getStatusCode(), equalTo(HttpStatus.CREATED));


        ResponseEntity<OfferResponseDto> getResponse = restTemplate.getForEntity("/offer/{id}", OfferResponseDto.class, Map.of("id", 1));

        assertThat(getResponse.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(getResponse.getBody().getId(), equalTo(1L));
        assertThat(getResponse.getBody().getOfferKey(), equalTo("aaa"));
    }

    @Test
    void getExclusiveRelatedOffersFromOffer() {
        for (Offer offer : new Offer[]{
                new Offer(null, "aaa"),
                new Offer(null, "bbb"),
                new Offer(null, "ccc"),
                new Offer(null, "ddd")
        }) {
            offerRepository.save(offer);
        }

        CreateExclusiveRelationOfferDto createExclusiveRelationOfferDto = new CreateExclusiveRelationOfferDto();
        createExclusiveRelationOfferDto.setOffer("aaa");
        createExclusiveRelationOfferDto.setRelateWith(List.of("bbb"));
        ResponseEntity<CreateExclusiveRelationOfferDto> postReponse = restTemplate.postForEntity("/offer/exclusive-relation", createExclusiveRelationOfferDto, CreateExclusiveRelationOfferDto.class);
        assertThat(postReponse.getStatusCode(), equalTo(HttpStatus.CREATED));

        ResponseEntity<OfferExclusiveRelationResponseDto> getResponse = restTemplate.getForEntity("/offer/{key}/exclusive-relation", OfferExclusiveRelationResponseDto.class, Map.of("key", "aaa"));
        assertThat(getResponse.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(getResponse.getBody().getOffersRelated().get(0).getOfferKey(), equalTo("bbb"));
    }
}
