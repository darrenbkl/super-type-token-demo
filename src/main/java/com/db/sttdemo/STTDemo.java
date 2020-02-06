package com.db.sttdemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class STTDemo implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(STTDemo.class, args);
    }

    private final WebClient webClient;

    public STTDemo(WebClient webClient) {
        this.webClient = webClient;
    }


    @Override
    public void run(String... args) {

//        webClient.get()
//                 .uri("/users/1")
//                 .retrieve()
//                 .bodyToMono(User.class)
//                 .doOnNext(System.out::println)
//                 .subscribe();

//        webClient.get()
//                 .uri("/users")
//                 .retrieve()
//                 .bodyToMono(new ParameterizedTypeReference<List<User>>() {
//                 })
//                 .doOnNext(System.out::println)
//                 .subscribe();

//        webClient.get()
//                 .uri("/users")
//                 .retrieve()
//                 .bodyToFlux(User.class)
//                 .doOnNext(System.out::println)
//                 .subscribe();

        ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<>() {};

        Flux<User> userFlux = Flux.fromArray(new User[]{new User("D", "B", 11), new User("E", "F", 22)});

//        List<User> users = Arrays.asList(

        webClient.post()
                 .uri("/users")
                 .body(BodyInserters.fromPublisher(userFlux, User.class))
                 .retrieve()
                 .toBodilessEntity()
                 .subscribe();




//        };
//        Mono<List<User>> users = webClient.get()
//                                          .uri("/users")
//                                          .retrieve()
//                                          .bodyToMono(typeRef);

    }

}
