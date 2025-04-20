package com.zephiro.artifacts.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

        // Create a list with the ID and name of the survey answered on that date
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
        // Get the questionnaire by ID
        return questionnaireRepository.findById(responseId)
                .orElseThrow(() -> new RuntimeException("Questionnaire " + responseId + " not found"));
    }

    public List<Graphic> getGraphicData(String userId) {
        List<Questionnaire> questionnaires = questionnaireRepository.findByUserIdAndType(userId, "Microsurvey");
        Map<LocalDate, Graphic> graphicsMap = new HashMap<>();

        // Create a map with the last 7 days
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(6);

        // Fill the map with the last 7 days and initialize the graphics
        for (int i = 0; i < 7; i++) {
            LocalDate date = startDate.plusDays(i);
            graphicsMap.put(date, new Graphic(date, 0, 0));
        }
    
        // Join the questionnaires by date and calculate the daily stress and anxiety
        Map<LocalDate, List<Questionnaire>> groupedByDate = questionnaires.stream()
            .filter(q -> (q.getSurveyName().equals("Microsurvey Diurno") || q.getSurveyName().equals("Microsurvey Nocturno")))
            .filter(q -> {
                LocalDate date = q.getCompletionDate();
                return (date != null && !date.isBefore(startDate) && !date.isAfter(today));
            })
            .collect(Collectors.groupingBy(Questionnaire::getCompletionDate));

        for (Map.Entry<LocalDate, List<Questionnaire>> entry : groupedByDate.entrySet()) {
            int dailyStress = 0;
            int dailyAnxiety = 0;
    
            for (Questionnaire q : entry.getValue()) {
                List<Response> r = q.getResponses();
                if(q.getSurveyName().equals("Microsurvey Nocturno")){
                    dailyStress += r.get(0).getNumericalValue() + r.get(2).getNumericalValue() + r.get(5).getNumericalValue();
                    dailyAnxiety += r.get(1).getNumericalValue() + r.get(3).getNumericalValue() + r.get(4).getNumericalValue();
                }
                else if(q.getSurveyName().equals("Microsurvey Diurno")){
                    dailyStress += r.get(0).getNumericalValue() + r.get(2).getNumericalValue() + r.get(3).getNumericalValue() + r.get(5).getNumericalValue();
                    dailyAnxiety += r.get(1).getNumericalValue() + r.get(2).getNumericalValue() + r.get(4).getNumericalValue();
                }
            }
            graphicsMap.put(entry.getKey(), new Graphic(entry.getKey(), dailyAnxiety, dailyStress));
        }

        // Fill the graphics with the information in the map
        List<Graphic> graphicData = graphicsMap.values().stream()
            .sorted(Comparator.comparing(Graphic::getDate))
            .collect(Collectors.toList());

        return graphicData;
    }

    public int getStreak(String userId) {
        int streak = 0;
        LocalDate today = LocalDate.now();

        List<Questionnaire> questionnaires = questionnaireRepository.findByUserId(userId);

        // Filtrar nulos y duplicados por fecha
        Set<LocalDate> completedDates = questionnaires.stream()
                .map(Questionnaire::getCompletionDate)
                .collect(Collectors.toSet());

        while (completedDates.contains(today.minusDays(streak))) {
            streak++;
        }

        return streak;
    }

    public List<LocalDate> getAllDates(String userId) {
        List<Questionnaire> questionnaires = questionnaireRepository.findByUserId(userId);

        Map<LocalDate, List<Questionnaire>> groupedByDate = questionnaires.stream()
            .filter(q -> q.getCompletionDate() != null)
            .collect(Collectors.groupingBy(Questionnaire::getCompletionDate));

        List<LocalDate> dates = new ArrayList<>(groupedByDate.keySet());

        Collections.sort(dates, Comparator.reverseOrder());
        return dates;
    }
}
