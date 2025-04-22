package com.zephiro.artifacts.DTO;

import java.time.LocalDate;

public class Graphic {
    private LocalDate date;
    private int anxietyScore;
    private int stressScore;

    public Graphic(LocalDate date, int anxietyScore, int stressScore) {
        this.date = date;
        this.anxietyScore = anxietyScore;
        this.stressScore = stressScore;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getAnxietyScore() {
        return anxietyScore;
    }

    public void setAnxietyScore(int anxietyScore) {
        this.anxietyScore = anxietyScore;
    }

    public int getStressScore() {
        return stressScore;
    }

    public void setStressScore(int stressScore) {
        this.stressScore = stressScore;
    }
}
