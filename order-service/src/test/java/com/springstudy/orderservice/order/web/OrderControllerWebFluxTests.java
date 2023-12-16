package com.springstudy.orderservice.order.web;


import com.springstudy.orderservice.order.domain.Order;
import com.springstudy.orderservice.order.domain.OrderService;
import com.springstudy.orderservice.order.domain.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;


@WebFluxTest
public class OrderControllerWebFluxTests {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private OrderService orderService;


    @Test
    void whenBookNotAvailableThenRejectOrder() {

        var orderRequest = new OrderRequest("1234567890", 1);
        var expectedOrder = OrderService.buildRejectedOrder(orderRequest.bookIsbn(), orderRequest.quantity());
        given(orderService.submitOrder(orderRequest.bookIsbn(), orderRequest.quantity())).willReturn(Mono.just(expectedOrder));

        webTestClient.post()
                .uri("/orders")
                .bodyValue(orderRequest)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Order.class).value(order -> {
                    assertThat(order).isNotNull();
                    assertThat(order.status()).isEqualTo(OrderStatus.REJECTED);
                });

    }
}
