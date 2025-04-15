package com.zephiro.artifacts.DTO;

import java.time.LocalDate;

public class Search {
    private String userId;
    private String surveyId;
    private LocalDate completionDate;
    
    public Search(String userId, String surveyId, LocalDate completionDate) {
        this.userId = userId;
        this.surveyId = surveyId;
        this.completionDate = completionDate;
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

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }
}
