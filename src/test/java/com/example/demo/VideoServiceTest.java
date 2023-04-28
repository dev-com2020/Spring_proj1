package com.example.demo;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

@ExtendWith(MockitoExtension.class)
public class VideoServiceTest {

    VideoService videoService;
    @Mock VideoRepository repository;

    @BeforeEach
    void setUp() {
        videoService = new VideoService(repository);
    }

    @Test
    void getVideosShouldReturnAll(){
        VideoEntity video1 = new VideoEntity("alice","Spring Boot 3","Learn the basics of Spring Boot 3");
        VideoEntity video2 = new VideoEntity("alice","Spring Boot 4","Learn the basics of Spring Boot 4");
        when(repository.findAll()).thenReturn(List.of(video1, video2));
        List<VideoEntity> videos = videoService.getVideos();
        assert videos.size() == 2;
    }

    @Test
    void searchShouldReturnASubset(){
        VideoEntity video1 = new VideoEntity("alice","Spring Boot 3","Learn the basics of Spring Boot 3");
        when(repository.findAll(any(Example.class))).thenReturn(List.of(video1));
        List<VideoEntity> videos = videoService.search(new UniversalSearch("Spring"));
        assertThat(videos).containsExactly(video1);

    }
}
