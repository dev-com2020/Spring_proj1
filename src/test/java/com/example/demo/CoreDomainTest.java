package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


public class CoreDomainTest {
    @Test
    void newVideoEntityShouldHaveIdNull() {
        VideoEntity videoEntity = new VideoEntity("alice", "title", "description");
        assertThat(videoEntity.getId()).isNull();
        assertThat(videoEntity.getUsername()).isEqualTo("alice");
        assertThat(videoEntity.getName()).isEqualTo("title");
        assertThat(videoEntity.getDescription()).isEqualTo("description");
    }

    @Test
    void settersShouldMutateState(){
        VideoEntity videoEntity = new VideoEntity("alice", "title", "description");
        videoEntity.setId(1L);
        videoEntity.setUsername("bob");
        videoEntity.setName("new title");
        videoEntity.setDescription("new description");
        assertThat(videoEntity.getId()).isEqualTo(1L);
        assertThat(videoEntity.getUsername()).isEqualTo("bob");
        assertThat(videoEntity.getName()).isEqualTo("new title");
        assertThat(videoEntity.getDescription()).isEqualTo("new description");}
}
