package com.zephiro.artifacts.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.zephiro.artifacts.entity.Questionnaire;

import java.time.LocalDate;

@DataMongoTest
public class QuestionnaireRepositoryTest {

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Test
    public void saveQuestionnaire_shouldReturnSavedQuestionnaire() {
        // Arrange
        Questionnaire questionnaire = createTestQuestionnaire();

        // Act
        Questionnaire savedQuestionnaire = questionnaireRepository.save(questionnaire);

        // Assert
        Assertions.assertThat(savedQuestionnaire).isNotNull();
        Assertions.assertThat(savedQuestionnaire.getId()).isEqualTo("aaa");
        questionnaireRepository.deleteById(savedQuestionnaire.getId());
    }

    @Test
    public void findById_shouldReturnQuestionnaire() {
        // Arrange
        Questionnaire questionnaire = createTestQuestionnaire();
        Questionnaire savedQuestionnaire = questionnaireRepository.save(questionnaire);

        // Act
        Questionnaire foundQuestionnaire = questionnaireRepository.findById(savedQuestionnaire.getId()).orElse(null);

        // Assert
        Assertions.assertThat(foundQuestionnaire).isNotNull();
        Assertions.assertThat(savedQuestionnaire.getId()).isEqualTo("aaa");
        Assertions.assertThat(savedQuestionnaire.getCompletionDate()).isEqualTo(LocalDate.now());
        questionnaireRepository.deleteById(savedQuestionnaire.getId());
    }

    private Questionnaire createTestQuestionnaire() {
        Questionnaire questionnaire = questionnaireRepository.findAll().get(0);
        questionnaire.setId("aaa");
        questionnaire.setCompletionDate(LocalDate.now());

        return questionnaire;
    }
}