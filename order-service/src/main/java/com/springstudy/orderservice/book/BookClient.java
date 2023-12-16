package com.springstudy.orderservice.book;


import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

/**
 * This class represents a client for interacting with a book service.
 */
@Component
public class BookClient {
    private static final String BOOKS_ROOT_PATH = "/books/";
    private final WebClient webClient;

    /**
     * Constructs a new BookClient with the provided WebClient.
     *
     * @param webClient the WebClient to use for making requests
     */
    public BookClient(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Fetches a book from the book service by its ISBN.
     *
     * This method makes a GET request to the book service, using the provided ISBN to construct the request URI.
     * The response is retrieved and converted to a Mono<Book>.
     * If the request does not complete within 3 seconds, an empty Mono is returned.
     * If a WebClientResponseException.NotFound exception occurs (i.e., the server responds with a 404 status code),
     * an empty Mono is returned.
     * If any other exception occurs, an empty Mono is returned.
     * The request is retried three times in case of an error, with a backoff delay that increases exponentially.
     *
     * @param isbn the ISBN of the book to fetch
     * @return a Mono<Book> containing the fetched book, or an empty Mono if the book could not be fetched
     */
    public Mono<Book> getBookByIsbn(String isbn) {
        return webClient.get()
                .uri(BOOKS_ROOT_PATH + isbn)
                .retrieve()
                .bodyToMono(Book.class)
                .timeout(Duration.ofSeconds(3), Mono.empty())
                .onErrorResume(WebClientResponseException.NotFound.class, exception -> Mono.empty())
                .retryWhen(Retry.backoff(3, Duration.ofMillis(100)))
                .onErrorResume(Exception.class, exception -> Mono.empty());
    }
}
