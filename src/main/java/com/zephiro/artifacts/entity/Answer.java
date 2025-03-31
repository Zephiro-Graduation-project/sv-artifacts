package com.zephiro.artifacts.entity;

public class Answer {
    private String text;
    private int numericValue;
    
    public Answer(String text, int numericValue) {
        this.text = text;
        this.numericValue = numericValue;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNumericValue() {
        return numericValue;
    }

    public void setNumericValue(int numericValue) {
        this.numericValue = numericValue;
    }
}
