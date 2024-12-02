package com.questionbank.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class QuestionRequest {

    private Long questionId;

    private String question; 

    private String answer; 

    private String language; 

    private String category; 

    private String difficulty; 

    private List<String> tags; 

    private String author; 

    private List<String> exampleInputs; 

    private List<String> exampleOutputs; 

    private String notes; 

    private List<String> relatedTopics; 

    private List<String> hints; 

    private List<ReferenceRequest> references; 

    private List<CodeSnippetRequest> codeSnippets;


}
