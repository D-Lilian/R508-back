/* (C)2025 */
package com.course.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

public abstract class ITBase {

    @Container
    static MariaDBContainer<?> mariaDB =
            new MariaDBContainer<>(DockerImageName.parse("mariadb:10.5.8")).withExposedPorts(3306);

    private static final Logger logger = LoggerFactory.getLogger(ITBase.class);

    static {
        mariaDB.setWaitStrategy(Wait.forListeningPort());
        mariaDB.withLogConsumer(new Slf4jLogConsumer(logger));
        mariaDB.start();
    }

    //    @AfterAll
    //    static void afterAll() {
    //        if (mariaDB != null) {
    //            mariaDB.stop();
    //        }
    //    }

    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        mariaDB.start();
        registry.add("spring.datasource.url", mariaDB::getJdbcUrl);
        registry.add("spring.datasource.username", mariaDB::getUsername);
        registry.add("spring.datasource.password", mariaDB::getPassword);
    }
}
