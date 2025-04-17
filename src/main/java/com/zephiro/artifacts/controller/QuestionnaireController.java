package com.zephiro.artifacts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zephiro.artifacts.entity.Questionnaire;
import com.zephiro.artifacts.DTO.Graphic;
import com.zephiro.artifacts.DTO.Quest;
import com.zephiro.artifacts.service.QuestionnaireService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {
    
    @Autowired
    private QuestionnaireService questionnaireService;
    
    @PostMapping("/add")
    public ResponseEntity<?> saveQuestionnaire(@RequestBody Questionnaire questionnaire) {
        try {
            // ToDo: Add rewards for the gamification system
            questionnaireService.saveQuestionnaire(questionnaire);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("{\"message\": \"Answer saved successfully\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred during registration\"}");
        }
    }

    @GetMapping("/ondate/{userId}/{date}")
    public ResponseEntity<?> getQuestionnairesOnDate(@PathVariable String userId, @PathVariable String date) {
        try {
            List<Quest> questionnaires = questionnaireService.getQuestionnairesOnDate(userId, date);
            return ResponseEntity.ok(questionnaires);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while fetching content\"}");
        }
    }

    @GetMapping("/specific/{responseId}")
    public ResponseEntity<?> getSpecificQuestionnaire(@PathVariable String responseId) {
        try {
            Questionnaire quest = questionnaireService.getSpecificQuestionnaire(responseId);
            return ResponseEntity.ok(quest);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while fetching content\"}");
        }
    }

    @GetMapping("/graphic/{userId}")
    public ResponseEntity<?> getGraphicData(@PathVariable String userId) {
        try {
            List<Graphic> graphicData = questionnaireService.getGraphicData(userId);
            return ResponseEntity.ok(graphicData);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while fetching content\"}");
        }
    }
}
