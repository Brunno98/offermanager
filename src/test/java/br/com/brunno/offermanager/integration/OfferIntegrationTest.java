package br.com.brunno.offermanager.integration;

import br.com.brunno.offermanager.controller.CreateOfferPayload;
import br.com.brunno.offermanager.controller.OfferResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

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
}
