package com.zephiro.artifacts.entity;

import java.util.List;

public class Response {
    private String question;
    private String selectedAnswer;
    private int numericalValue;
    private List<String> measures;
    
    public Response(String question, String selectedAnswer, int numericalValue, List<String> measures) {
        this.question = question;
        this.selectedAnswer = selectedAnswer;
        this.numericalValue = numericalValue;
        this.measures = measures;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public int getNumericalValue() {
        return numericalValue;
    }

    public void setNumericalValue(int numericalValue) {
        this.numericalValue = numericalValue;
    }

    public List<String> getMeasures() {
        return measures;
    }

    public void setMeasures(List<String> measures) {
        this.measures = measures;
    }
}
