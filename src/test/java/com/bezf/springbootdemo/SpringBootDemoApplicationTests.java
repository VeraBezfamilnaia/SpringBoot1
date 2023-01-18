package com.bezf.springbootdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.testcontainers.containers.GenericContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootDemoApplicationTests {
    @Autowired
    private TestRestTemplate restTemplate;
    private static final int FIRST_APP_PORT = 8080;
    private static final int SECOND_APP_PORT = 8081;
    private final static String START_URL = "http://localhost:";
    private final static String DEV_EXPECTED = "Current profile is dev";
    private final static String PRODUCTION_EXPECTED = "Current profile is production";
    private final GenericContainer<?> firstApp = new GenericContainer<>("devapp:latest")
            .withExposedPorts(FIRST_APP_PORT);
    private final GenericContainer<?> secondApp = new GenericContainer<>("prodapp:latest")
            .withExposedPorts(SECOND_APP_PORT);

    @BeforeEach
    void setUp() {
        firstApp.start();
        secondApp.start();
    }

    @Test
    @DisplayName("when the current profile is dev then \"Current profile is dev\" should be returned")
    void returnStringWhenCurrentProfileIsDev() {
        var expected = DEV_EXPECTED;
        var mappedPort = firstApp.getMappedPort(FIRST_APP_PORT);
        var forEntity = restTemplate.getForEntity(START_URL + mappedPort, String.class);

        var actual = forEntity.getBody();

        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("when the current profile is production then \"Current profile is production\" should be returned")
    void returnStringWhenCurrentProfileIsProduction() {
        var expected = PRODUCTION_EXPECTED;
        var mappedPort = firstApp.getMappedPort(SECOND_APP_PORT);
        var forEntity = restTemplate.getForEntity(START_URL + mappedPort, String.class);

        var actual = forEntity.getBody();

        assertEquals(expected, actual);
    }
}
