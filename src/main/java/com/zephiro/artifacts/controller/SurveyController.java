package com.zephiro.artifacts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zephiro.artifacts.entity.Survey;
import com.zephiro.artifacts.service.SurveyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/artifact")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @PostMapping("/add")
    public ResponseEntity<?> createArtifact(@RequestBody Survey artifact) {
        try {
            surveyService.createArtifact(artifact);
            return ResponseEntity.ok("{\"message\": \"Content created successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    @GetMapping("/{keyword}")
    public ResponseEntity<?> getArtifactByKeyword(@PathVariable String keyword) {
        try {
            Survey artifact = surveyService.getArtifact(keyword);
            return ResponseEntity.ok(artifact);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while fetching content\"}");
        }
    }

}
