package com.example.demo;

import static com.example.demo.VideoRepositoryTestcontainersTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = {DataSourceIntializer.class})
public class VideoRepositoryTestcontainersTest {
    @Autowired
    VideoRepository repository;
    @Container
    static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:13.2")
            .withUsername("postgres");

    static class DataSourceIntializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(applicationContext,
                    "spring.datasource.url=" + database.getJdbcUrl(),
                    "spring.datasource.username=" + database.getUsername(),
                    "spring.datasource.password=" + database.getPassword(),
                    "spring.jpa.hibernate.ddl-auto=create-drop");

        }
    }
    @BeforeEach
    void setUp() {
        repository.saveAll(List.of(
                new VideoEntity("bob","Video 1", "https://www.youtube.com/watch?v=1"),
                new VideoEntity("bop","Video 2", "https://www.youtube.com/watch?v=2"),
                new VideoEntity("alice","Video 3", "https://www.youtube.com/watch?v=3")
        ));
    }

    @Test
    void findAllByOrderByCreatedAtDesc() {
        List<VideoEntity> videos = repository.findByDescriptionContainingIgnoreCase("Video");
        assertThat(videos).hasSize(3);
    }
}
