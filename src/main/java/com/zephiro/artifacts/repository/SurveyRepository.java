package com.zephiro.artifacts.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.zephiro.artifacts.entity.Survey;

@Repository
public interface SurveyRepository extends MongoRepository<Survey, String> {

    Optional<Survey> findFirstByNameContainingIgnoreCase(String keyword);
}
