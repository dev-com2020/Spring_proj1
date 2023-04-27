package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public  interface VideoRepository  extends JpaRepository<VideoEntity, Long> {
    List<VideoEntity> findByName(String name);
    List<VideoEntity> findByNameContainingIgnoreCase(String partialName);
    List<VideoEntity> findByDescriptionContainingIgnoreCase(String partialName);
    List<VideoEntity> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String partialName, String partialDescription);

}
