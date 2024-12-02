package com.questionbank.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.questionbank.request.QuestionRequest;
import com.questionbank.response.QuestionResponse;
import com.questionbank.service.QuestionService;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }


    @PostMapping
    public ResponseEntity<QuestionResponse> createQuestion(
            @RequestBody QuestionRequest questionRequests) {
        try {
            // Process the list of QuestionRequest objects
            QuestionResponse response = questionService.createQuestion(questionRequests);
            return new ResponseEntity<>(response, HttpStatus.OK); 
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        } catch (RuntimeException e) {
        	System.out.println("+++++++"+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }


    @PutMapping("/{questionId}")
    public ResponseEntity<QuestionResponse> updateQuestion(
            @PathVariable String questionId,
            @RequestBody QuestionRequest questionRequest) {
        try {
            QuestionResponse response = questionService.updateQuestion(questionId, questionRequest);
            return new ResponseEntity<>(response, HttpStatus.OK); 
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }


    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionResponse> getQuestionById(@PathVariable String questionId) {
        try {
            QuestionResponse response = questionService.getQuestionById(questionId);
            return new ResponseEntity<>(response, HttpStatus.OK); 
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }


    @GetMapping
    public ResponseEntity<List<QuestionResponse>> getAllQuestions() {
        try {
            List<QuestionResponse> responses = questionService.getAllQuestions();
            return new ResponseEntity<>(responses, HttpStatus.OK); 
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }


    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestionById(@PathVariable String questionId) {
        try {
            questionService.deleteQuestionById(questionId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }

}
