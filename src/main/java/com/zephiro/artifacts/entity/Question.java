package com.zephiro.artifacts.entity;

import java.util.List;

public class Question {
    private String text;
    private List<String> measures;
    private List<Answer> answers;
    
    public Question(String text, List<String> measures, List<Answer> answers) {
        this.text = text;
        this.measures = measures;
        this.answers = answers;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getMeasures() {
        return measures;
    }

    public void setMeasures(List<String> measures) {
        this.measures = measures;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
