package com.springstudy.orderservice.book.order.domain;
import com.springstudy.orderservice.config.DataConfig;
import com.springstudy.orderservice.order.domain.OrderRepository;
import com.springstudy.orderservice.order.domain.OrderService;
import com.springstudy.orderservice.order.domain.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.test.StepVerifier;

@DataR2dbcTest
@Import(DataConfig.class)
@Testcontainers
public class OrderRepositoryR2dbcTests {

    @Container
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:14.4")).waitingFor(Wait.forListeningPort());

    @Autowired
    private OrderRepository orderRepository;

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", OrderRepositoryR2dbcTests::r2dbcUrl);
        registry.add("spring.r2dbc.username", container::getUsername);
        registry.add("spring.r2dbc.password", container::getPassword);
        registry.add("spring.flyway.url", container::getJdbcUrl);
    }

    static String r2dbcUrl() {
        return String.format("r2dbc:postgresql://%s:%s/%s",
                container.getHost(),
                container.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT),
                container.getDatabaseName());
    }

    @Test
    void createRejectedOrder() {
        var rejectedOrder = OrderService.buildRejectedOrder("1234567890", 3);
        StepVerifier.create(orderRepository.save(rejectedOrder))
                .expectNextMatches(order -> order.status().equals(OrderStatus.REJECTED))
                .verifyComplete();

    }

}
