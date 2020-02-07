package com.db.sttdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Component
public class ApiClient {

    private WebClient webClient;

    public ApiClient() {
        webClient = WebClient.builder()
                             .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                             .filter(logRequest())
                             .filter(logResponse())
                             .build();
    }

    public ApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public ApiClient setBaseUrl(String baseUrl) {
        Objects.requireNonNull(baseUrl);

        log.info("Setting WebClient baseUrl: {}", baseUrl);

        return new ApiClient(webClient = webClient.mutate()
                                                  .baseUrl(baseUrl)
                                                  .build());
    }

    public ApiClient setJwtAuthorizationHeader(String jwt) {
        Objects.requireNonNull(jwt);

        return new ApiClient(webClient.mutate()
                                      .defaultHeader("Authorization", "Bearer " + jwt)
                                      .build());
    }

    // TODO extract common part
    public <T> WebClient.RequestHeadersSpec<?> post(String baseUrl, String uri, T body) {
        Objects.requireNonNull(baseUrl);
        Objects.requireNonNull(uri);
        Objects.requireNonNull(body);

        log.info("Setting WebClient baseUrl: {}", baseUrl);
        log.info("Calling resource: {}", uri);
        log.info("With body: {}", body.toString());

        webClient = webClient.mutate()
                             .baseUrl(baseUrl)
                             .build();

        // bodyToMono will throw WebClientResponseException on 4xx or 5xx
        return webClient.post()
                        .uri(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(body));
    }

    public <T> WebClient.RequestHeadersSpec<?> post(String baseUrl, String uri, MediaType contentType, T body) {
        Objects.requireNonNull(baseUrl);
        Objects.requireNonNull(uri);
        Objects.requireNonNull(contentType);
        Objects.requireNonNull(body);

        if(!contentType.isCompatibleWith(MediaType.APPLICATION_FORM_URLENCODED) && !contentType.isCompatibleWith(MediaType.APPLICATION_JSON)) {
            // TODO
        }

        log.info("Setting WebClient baseUrl: {}", baseUrl);
        log.info("Calling resource: {}", uri);
        log.info("With body: {}", body.toString());

        webClient = webClient.mutate()
                             .baseUrl(baseUrl)
                             .build();

        // bodyToMono will throw WebClientResponseException on 4xx or 5xx
        return webClient.post()
                        .uri(uri)
                        .contentType(contentType)
                        .body(BodyInserters.fromObject(body));
    }

    public <T> WebClient.RequestHeadersSpec<?> get(String baseUrl, String uriPath, MultiValueMap<String, String> params) {
        Objects.requireNonNull(baseUrl);
        Objects.requireNonNull(uriPath);

        log.info("Setting WebClient baseUrl: {}", baseUrl);
        log.info("Calling resource: {}", uriPath);
        log.info("With query params: {}", params.toString());

        webClient = webClient.mutate()
                             .baseUrl(baseUrl)
                             .build();

        return webClient.get()
                        .uri(builder -> builder.path(uriPath)
                                               .queryParams(params)
                                               .build());
    }

    private static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            /*clientRequest.headers()
                         .forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));*/
            return Mono.just(clientRequest);
        });
    }

    private static ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.info("Response: {}", clientResponse.statusCode());
            return Mono.just(clientResponse);
        });
    }
}
