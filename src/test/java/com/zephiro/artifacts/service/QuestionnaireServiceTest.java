package com.zephiro.artifacts.service;

import com.zephiro.artifacts.DTO.Graphic;
import com.zephiro.artifacts.DTO.Quest;
import com.zephiro.artifacts.entity.Questionnaire;
import com.zephiro.artifacts.repository.QuestionnaireRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class QuestionnaireServiceTest {

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Test
    public void saveQuestionnaire_shouldCalculateScore() {
        // Arrange
        Questionnaire questionnaire = createTestQuestionnaire();
        
        // Act
        questionnaireService.saveQuestionnaire(questionnaire);
        Questionnaire saved = questionnaireRepository.findAll().get(0);

        // Assert
        Assertions.assertThat(saved).isNotNull();
        questionnaireRepository.delete(saved);
    }

    @Test
    public void saveQuestionnaire_shouldNotCalculateScoreForSociodemographic() {
        // Arrange
        Questionnaire questionnaire = createTestQuestionnaire();
        questionnaire.setType("Sociodemographic");

        // Act
        questionnaireService.saveQuestionnaire(questionnaire);
        Questionnaire saved = questionnaireRepository.findById("aaa").orElse(null);

        // Assert
        Assertions.assertThat(saved.getScore()).isEqualTo(0);
        questionnaireRepository.delete(saved);
    }

    @Test
    public void getQuestionnairesOnDate_shouldReturnQuests() {
        // Arrange
        Questionnaire questionnaire1 = createTestQuestionnaire();
        questionnaireRepository.save(questionnaire1);

        Questionnaire questionnaire2 = createTestQuestionnaire();
        questionnaire2.setId("bbb");
        questionnaireRepository.save(questionnaire2);

        // Act
        List<Quest> result = questionnaireService.getQuestionnairesOnDate(questionnaire1.getUserId(), LocalDate.now().toString());

        // Assert
        Assertions.assertThat(result).hasSize(2);
        questionnaireRepository.delete(questionnaire1);
        questionnaireRepository.delete(questionnaire2);
    }

    @Test
    public void getSpecificQuestionnaire_shouldReturnQuestionnaire() {
        // Arrange
        Questionnaire questionnaire = createTestQuestionnaire();
        questionnaireRepository.save(questionnaire);

        // Act
        Questionnaire result = questionnaireService.getSpecificQuestionnaire(questionnaire.getId());

        // Assert
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(questionnaire.getId());
        questionnaireRepository.delete(questionnaire); 
    }

    @Test
    public void getGraphicData_shouldReturnLast7Days() {
        // Arrange
        for(int i = 0; i < 7; i++) {
            Questionnaire questionnaire = createTestQuestionnaire();
            questionnaire.setId("aaa" + i);
            questionnaire.setCompletionDate(LocalDate.now().minusDays(i));
            questionnaireRepository.save(questionnaire);
        }

        // Act
        List<Graphic> result = questionnaireService.getGraphicData("67fdcb6afa5d893f602f9b2d");

        // Assert
        Assertions.assertThat(result).hasSize(7);
        Assertions.assertThat(result.get(0).getDate()).isBeforeOrEqualTo(LocalDate.now());
        for(int i = 0; i < 7; i++) {
            Questionnaire questionnaire = questionnaireRepository.findById("aaa" + i).orElse(null);
            questionnaireRepository.delete(questionnaire);
        } 
    }

    @Test
    public void getStreak_shouldCalculateCorrectStreak() {
        // Arrange
        Questionnaire questionnaire = createTestQuestionnaire();
        questionnaireRepository.save(questionnaire);

        // Act
        int streak = questionnaireService.getStreak("67fdcb6afa5d893f602f9b2d");

        // Assert
        Assertions.assertThat(streak).isGreaterThanOrEqualTo(1);
        questionnaireRepository.delete(questionnaire);
    }

    private Questionnaire createTestQuestionnaire() {
        Questionnaire q = questionnaireRepository.findByUserId("67fdcb6afa5d893f602f9b2d").get(0);
        Questionnaire questionnaire = new Questionnaire(q.getUserId(), q.getSurveyId(), q.getSurveyName(), q.getType(), LocalDate.now(), q.getResponses());
        questionnaire.setId("aaa");
        
        return questionnaire;
    }
}