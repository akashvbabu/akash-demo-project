package com.example.akashdemo;

import com.example.akashdemo.entity.MessageResponseEntity;
import com.example.akashdemo.entity.PhoneNumberCombinationResponseEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PhoneNumberResourceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void phoneCombinationCreatedSuccessfully() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl("http://localhost:" + port + "/phoneNumber/process")
                .queryParam("phone", "111-111-1111");
        ResponseEntity<MessageResponseEntity> response =
                this.restTemplate.exchange(builder.toUriString(), HttpMethod.POST, null, MessageResponseEntity.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getMessage()).isEqualTo("generated combinations");
    }

    @Test
    public void phoneCombinationsRetrievedSuccessfully() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl("http://localhost:" + port + "/phoneNumber/process")
                .queryParam("phone", "222-222-2222");
        this.restTemplate.exchange(builder.toUriString(), HttpMethod.POST, null, MessageResponseEntity.class);
        UriComponentsBuilder getEndpointBuilder = UriComponentsBuilder
                .fromHttpUrl("http://localhost:" + port + "/phoneNumber/combinations")
                .queryParam("phone", "222-222-2222")
                .queryParam("pageNumber", 0)
                .queryParam("pageSize", 2);
        ResponseEntity<PhoneNumberCombinationResponseEntity> getResponse =
                this.restTemplate.exchange(getEndpointBuilder.toUriString(), HttpMethod.GET, null, PhoneNumberCombinationResponseEntity.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().getTotalHits()).isEqualTo(3);
        assertThat(getResponse.getBody().getPhoneNumberCombinations().get(0).getAlphaNumericCombination())
                .isEqualTo("222-222-2222 A");
    }
}
