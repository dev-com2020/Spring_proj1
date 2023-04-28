package com.example.demo;

import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class VideoService {

    public VideoService(VideoRepository repository) {
        this.repository = repository;
    }

    private final VideoRepository repository;

    public List<VideoEntity> getVideos() {
        return repository.findAll();
    }

    public VideoEntity create(NewVideo newVideo, String username){
        return repository.saveAndFlush(new VideoEntity(username, newVideo.name(), newVideo.description()));
    }

    public List<VideoEntity> search(VideoSearch videoSearch) {
        if (StringUtils.hasText(videoSearch.name()) && StringUtils.hasText(videoSearch.description())) {
            return repository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(videoSearch.name(), videoSearch.description());
        }
        if (StringUtils.hasText(videoSearch.name())) {
            return repository.findByNameContainingIgnoreCase(videoSearch.name());
        }
        if (StringUtils.hasText(videoSearch.description())) {
            return repository.findByDescriptionContainingIgnoreCase(videoSearch.description());
        }
        return Collections.emptyList();
    }

    public List<VideoEntity> search(UniversalSearch search){
        VideoEntity probe = new VideoEntity();
        if (StringUtils.hasText(search.value())) {
            probe.setName(search.value());
            probe.setDescription(search.value());
        }
        Example<VideoEntity> example = Example.of(probe,
                ExampleMatcher.matchingAll().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repository.findAll(example);

    }

    public void delete(Long videoId){
        repository.findById(videoId).map(videoEntity -> {
            repository.delete(videoEntity);
            return true;
        }).orElseThrow(() -> new RuntimeException("Video not found with id " + videoId));
    }

    @PostConstruct
    void initDatabase(){
        repository.save(new VideoEntity("alice","Spring Boot", "Spring Boot in 10 steps"));
        repository.save(new VideoEntity("bob","Spring MVC", "Spring MVC in 10 steps"));
        repository.save(new VideoEntity("admin","Spring Data JPA", "Spring Data JPA in 10 steps"));
    }
}



