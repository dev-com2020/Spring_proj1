package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class VideoRepositoryHsqlTest {
    @Autowired VideoRepository repository;
    @BeforeEach
    void setUp(){
        repository.saveAll(
                List.of(
                        new VideoEntity(
                                "alice",
                                "Spring Boot 3",
                                "Learn the basics of Spring Boot 3"
                        ),
                        new VideoEntity(
                                "alice",
                                "Spring Boot 4",
                                "Learn the basics of Spring Boot 4"
                        )
                        ));
    }
    @Test
    void findAllShouldReturnAll(){
        List<VideoEntity> videos = repository.findAll();
        assert videos.size() == 2;
    }

}
