package com.zephiro.artifacts.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.zephiro.artifacts.entity.Survey;

import java.util.List;
import java.util.Optional;

@DataMongoTest
public class SurveyRepositoryTest {

    @Autowired
    private SurveyRepository surveyRepository;

    @Test
    public void saveSurvey_shouldReturnSavedSurvey() {
        // Arrange
        Survey survey = createTestSurvey();

        // Act
        Survey savedSurvey = surveyRepository.save(survey);

        // Assert
        Assertions.assertThat(savedSurvey).isNotNull();
        Assertions.assertThat(savedSurvey.getName()).isEqualTo("Survey testing");
        surveyRepository.deleteById(savedSurvey.getId());
    }

    @Test
    public void findById_shouldReturnSurvey() {
        // Arrange
        Survey survey = createTestSurvey();
        survey.setId("bbb");
        Survey savedSurvey = surveyRepository.save(survey);

        // Act
        Optional<Survey> foundSurvey = surveyRepository.findById(savedSurvey.getId());

        // Assert
        Assertions.assertThat(foundSurvey).isPresent();
        Assertions.assertThat(foundSurvey.get().getName()).isEqualTo("Survey testing");
        surveyRepository.deleteById(savedSurvey.getId());
    }

    @Test
    public void findAll_shouldReturnAllSurveys() {
        // Arrange

        // Act
        List<Survey> surveys = surveyRepository.findAll();

        // Assert
        Assertions.assertThat(surveys).hasSize(6);
    }

    @Test
    public void deleteSurvey_shouldRemoveFromDatabase() {
        // Arrange
        Survey survey = createTestSurvey();
        Survey savedSurvey = surveyRepository.save(survey);

        // Act
        surveyRepository.deleteById(savedSurvey.getId());
        Optional<Survey> deletedSurvey = surveyRepository.findById(savedSurvey.getId());

        // Assert
        Assertions.assertThat(deletedSurvey).isEmpty();
    }

    private Survey createTestSurvey() {
        Survey survey = surveyRepository.findFirstByNameContainingIgnoreCase("Diurno").orElse(null);
        survey.setId("aaa");
        survey.setName("Survey testing");

        return survey;
    }
}