package com.db.sttdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

public class HttpClientTest {

    private WebClient webClient;
    private WebTestClient webTestClient;
    private ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<>() {
    };
    private RestTemplate restTemplate;


    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();

        webClient = WebClient.builder()
                             .baseUrl("http://localhost:8088")
                             .build();

        webTestClient = WebTestClient.bindToServer()
                                 .baseUrl("http://localhost:8088")
                                 .build();
    }

//    @Test
//    void testWebTestClient() {
//        List<User> users = Arrays.asList(new User("D", "B", 11), new User("E", "F", 22));
//        Mono<List<User>> userMono = Mono.just(users);
//
//        webTestClient.get()
//                 .uri("/users")
//                 .accept(MediaType.APPLICATION_JSON)
//                 .exchange()
//                 .expectBody(typeRef)
////                 .expectBodyList(User.class)
//                 .isEqualTo(users);
////                 .consumeWith(usersResponse -> System.out.println(usersResponse));
//
//    }

//    @Test
//    void testWebClient() {
//        webClient.get()
//                 .uri("/users")
//                 .retrieve()
//                 .bodyToMono(typeRef)
////                 .bodyToFlux(User.class)
//                 .doOnNext(System.out::println)
//                 .subscribe();
//    }

    @Test
    void testRestTemplate() {
        List<User> response = restTemplate.exchange("http://localhost:8088/users",
                                                    HttpMethod.GET,
                                                    null,
                                                    typeRef)
                                          .getBody();
        System.out.println(response);
    }
}