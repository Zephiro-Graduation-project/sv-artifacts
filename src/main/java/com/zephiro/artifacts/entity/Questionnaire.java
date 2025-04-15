package com.zephiro.artifacts.entity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "questionnaire")
public class Questionnaire {
    
    @Id
    private String id;
    private String userId;
    private String surveyId;
    private String surveyName;
    private String type;
    private LocalDate completionDate;
    private List<Response> responses;
    private int score;

    public Questionnaire(String userId, String surveyId, String surveyName, String type, LocalDate completionDate, List<Response> responses) {
        this.userId = userId;
        this.surveyId = surveyId;
        this.surveyName = surveyName;
        this.type = type;
        this.completionDate = completionDate;
        this.responses = responses;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
