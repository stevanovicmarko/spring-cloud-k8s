package com.springstudy.edgeservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Testcontainers
class EdgeServiceApplicationTests {

	private static final int REDIS_PORT = 6379;

	@Container
	private static final GenericContainer<?> redis = new GenericContainer<>("redis:7.0.3-alpine")
			.withExposedPorts(REDIS_PORT).waitingFor(Wait.forListeningPort());

	@DynamicPropertySource
	static void redisProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.redis.host", redis::getHost);
		registry.add("spring.redis.port", () -> redis.getMappedPort(REDIS_PORT));
	}

	@Test
	void contextLoads() {
	}

}
