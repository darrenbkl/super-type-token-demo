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

        List<User> users = Arrays.asList(new User("D", "B", 13), new User("E", "F", 22));
        Mono<List<User>> userMono = Mono.just(users);

//        List<User> users = Arrays.asList(

        webClient.post()
                 .uri("/users")
//                 .bodyValue(users)
//                 .body(BodyInserters.fromValue(users))
                 .body(BodyInserters.fromPublisher(userMono, typeRef))
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
