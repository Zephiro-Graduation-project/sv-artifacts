package com.zephiro.artifacts.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zephiro.artifacts.DTO.Graphic;
import com.zephiro.artifacts.DTO.Quest;
import com.zephiro.artifacts.entity.Questionnaire;
import com.zephiro.artifacts.entity.Response;
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
                Quest quest = new Quest(list.get(i).getId(), list.get(i).getSurveyName());
                questList.add(quest);
            }
            return questList;
        }
    }

    public Questionnaire getSpecificQuestionnaire(String responseId) {
        return questionnaireRepository.findById(responseId)
                .orElseThrow(() -> new RuntimeException("Questionnaire " + responseId + " not found"));
    }

    public List<Graphic> getGraphicData(String userId) {
        List<Questionnaire> questionnaires = questionnaireRepository.findByUserIdAndType(userId, "Microsurvey");
        List<Graphic> graphicData = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDate fifteenDaysAgo = today.minusDays(6);
    
        Map<LocalDate, List<Questionnaire>> groupedByDate = questionnaires.stream()
            .filter(q -> (q.getSurveyName().equals("Night microsurvey") || q.getSurveyName().equals("Noon microsurvey")))
            .filter(q -> {
                LocalDate date = q.getCompletionDate();
                return (date != null && !date.isBefore(fifteenDaysAgo) && !date.isAfter(today));
            })
            .collect(Collectors.groupingBy(Questionnaire::getCompletionDate));

        for (Map.Entry<LocalDate, List<Questionnaire>> entry : groupedByDate.entrySet()) {
            int dailyStress = 0;
            int dailyAnxiety = 0;
    
            for (Questionnaire q : entry.getValue()) {
                List<Response> r = q.getResponses();
                if(q.getSurveyName().equals("Night microsurvey")){
                    dailyStress += r.get(0).getNumericalValue() + r.get(2).getNumericalValue() + r.get(5).getNumericalValue();
                    dailyAnxiety += r.get(1).getNumericalValue() + r.get(3).getNumericalValue() + r.get(4).getNumericalValue();
                }
                else if(q.getSurveyName().equals("Noon microsurvey")){
                    dailyStress += r.get(0).getNumericalValue() + r.get(2).getNumericalValue() + r.get(3).getNumericalValue() + r.get(5).getNumericalValue();
                    dailyAnxiety += r.get(1).getNumericalValue() + r.get(2).getNumericalValue() + r.get(4).getNumericalValue();
                }
            }
            Graphic g = new Graphic(entry.getKey(), dailyAnxiety, dailyStress);
            graphicData.add(g);
        }
        graphicData.sort((g1, g2) -> g1.getDate().compareTo(g2.getDate()));
        return graphicData;
    }
}
