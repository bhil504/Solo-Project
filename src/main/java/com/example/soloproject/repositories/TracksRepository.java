package com.example.soloproject.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.soloproject.models.Tracks;

@Repository
public interface TracksRepository extends CrudRepository<Tracks, Long> {

    List<Tracks> findAll();
}