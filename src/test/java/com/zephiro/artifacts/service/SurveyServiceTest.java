package com.zephiro.artifacts.service;

import com.zephiro.artifacts.entity.Survey;
import com.zephiro.artifacts.repository.SurveyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SurveyServiceTest {

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private SurveyRepository surveyRepository;

    @Test
    public void createArtifact_shouldSaveSurvey() {
        // Arrange
        Survey survey = createTestSurvey();

        // Act
        surveyService.createArtifact(survey);
        List<Survey> surveys = surveyRepository.findAll();

        // Assert
        Assertions.assertThat(surveys).hasSize(7);
        Assertions.assertThat(surveys.get(surveys.size()-1).getName()).isEqualTo("Survey testing");
        surveyRepository.delete(survey);
    }

    @Test
    public void getArtifact_shouldReturnSurvey() {
        // Arrange
        Survey survey = createTestSurvey();
        survey.setName("Test Survey for Search");
        surveyRepository.save(survey);

        // Act
        Survey result = surveyService.getArtifact("search");

        // Assert
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getName()).contains("Search");
        surveyRepository.delete(survey);
    }

    private Survey createTestSurvey() {
        Survey survey = surveyRepository.findFirstByNameContainingIgnoreCase("Diurno").orElse(null);
        survey.setId("aaa");
        survey.setName("Survey testing");

        return survey;
    }
}