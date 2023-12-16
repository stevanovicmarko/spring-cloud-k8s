package com.springstudy.orderservice;

import com.springstudy.orderservice.order.domain.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class OrderServiceApplicationTests {
    @Container
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:14.4")).waitingFor(Wait.forListeningPort());

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.username", container::getUsername);
        registry.add("spring.r2dbc.password", container::getPassword);
        registry.add("spring.flyway.url", container::getJdbcUrl);
    }

    @Autowired
    private OrderService orderService;

    @Test
    void contextLoads() {
        assertThat(orderService).isNotNull();
    }

}
