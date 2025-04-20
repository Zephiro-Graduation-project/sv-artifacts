package com.zephiro.artifacts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zephiro.artifacts.entity.Survey;
import com.zephiro.artifacts.repository.SurveyRepository;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    // For the develpment team only
    public void createArtifact(Survey survey) {
        surveyRepository.save(survey);
    }

    public Survey getArtifact(String keyword) {
        return surveyRepository.findFirstByNameContainingIgnoreCase(keyword)
                    .orElseThrow(() -> new RuntimeException("Artifact with keyword " + keyword + " not found"));
    }
    
}
