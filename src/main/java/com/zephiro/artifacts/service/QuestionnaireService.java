package com.zephiro.artifacts.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zephiro.artifacts.DTO.Quest;
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

    public List<Quest> getQuestionnairesOnDate(String userId, String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<Questionnaire> list = questionnaireRepository.findByUserIdAndCompletionDate(userId, localDate);
        List<Quest> questList = new ArrayList<>();

        if(list.isEmpty()) {
            throw new RuntimeException("No questionnaires found for the given ID and user");
        }
        else {
            for (int i=0; i<list.size(); i++) {
                Quest quest = new Quest(list.get(i).getSurveyId(), list.get(i).getSurveyName());
                questList.add(quest);
            }
            return questList;
        }
    }

    public Questionnaire getSpecificQuestionnaire(String userId, String surveyId) {
        return questionnaireRepository.findByUserIdAndSurveyId(userId, surveyId)
                .orElseThrow(() -> new RuntimeException("Questionnaire not found for the given date or user ID"));
    }
}
