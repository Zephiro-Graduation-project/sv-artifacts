package com.zephiro.artifacts.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.zephiro.artifacts.entity.Questionnaire;

@Repository
public interface QuestionnaireRepository extends MongoRepository<Questionnaire, String> {

    Optional<Questionnaire> findByUserIdAndSurveyIdAndCompletionDate(String userId, String surveyId, LocalDate completionDate);
}
