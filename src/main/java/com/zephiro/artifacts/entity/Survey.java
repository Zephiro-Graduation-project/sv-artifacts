package com.zephiro.artifacts.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "surveys")
public class Survey {
    
    @Id
    private String id;
    private String name;
    private List<String> measures;
    private String periodicity;
    private List<Question> questions;

    public Survey(String name, List<String> measures, String periodicity, List<Question> questions) {
        this.name = name;
        this.measures = measures;
        this.periodicity = periodicity;
        this.questions = questions;
    }

    public Survey() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMeasures() {
        return measures;
    }

    public void setMeasures(List<String> measures) {
        this.measures = measures;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }    
}
