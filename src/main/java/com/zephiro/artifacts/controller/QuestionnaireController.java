package com.zephiro.artifacts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zephiro.artifacts.entity.Questionnaire;
import com.zephiro.artifacts.DTO.Quest;
import com.zephiro.artifacts.DTO.Search;
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

    @GetMapping("/ondate/{id}/{date}")
    public ResponseEntity<?> getQuestionnairesOnDate(@PathVariable String id, @PathVariable String date) {
        try {
            List<Quest> questionnaires = questionnaireService.getQuestionnairesOnDate(id, date);
            return ResponseEntity.ok(questionnaires);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while fetching content\"}");
        }
    }

    @GetMapping("/specific")
    public ResponseEntity<?> getQuestionnaireByDate(@RequestBody Search search) {
        try {
            Questionnaire quest = questionnaireService.getQuestionnaireByDate(search);
            return ResponseEntity.ok(quest);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while fetching content\"}");
        }
    }
    
}
