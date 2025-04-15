package com.zephiro.artifacts.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zephiro.artifacts.DTO.Search;
import com.zephiro.artifacts.entity.Questionnaire;
import com.zephiro.artifacts.repository.QuestionnaireRepository;

@Service
public class QuestionnaireService {
    
    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    public void saveQuestionnaire(Questionnaire questionnaire) {
        // Calculate the score based on the answers
        // The sociodemographic questionnaire does not have a score
        if(questionnaire.getType() != "Sociodemographic") {
            int score = 0;
            for (int i = 0; i < questionnaire.getResponses().size(); i++) {
                score += questionnaire.getResponses().get(i).getNumericalValue();
            }
            questionnaire.setScore(score);
        }

        questionnaireRepository.save(questionnaire);
    }

    public Questionnaire getQuestionnaireByDate(Search search) {
        String userId = search.getUserId();
        String surveyId = search.getSurveyId();
        LocalDate date = search.getCompletionDate();
        return questionnaireRepository.findByUserIdAndSurveyIdAndCompletionDate(userId, surveyId, date)
                .orElseThrow(() -> new RuntimeException("Questionnaire not found for the given date or user ID"));
    }
}
