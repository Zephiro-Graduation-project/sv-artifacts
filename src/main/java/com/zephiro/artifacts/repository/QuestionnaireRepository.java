package com.zephiro.artifacts.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.zephiro.artifacts.entity.Questionnaire;

@Repository
public interface QuestionnaireRepository extends MongoRepository<Questionnaire, String> {

    List<Questionnaire> findByUserIdAndCompletionDate(String userId, LocalDate completionDate);
    List<Questionnaire> findByUserIdAndType(String userId, String type);
    List<Questionnaire> findByUserId(String userId);
}
