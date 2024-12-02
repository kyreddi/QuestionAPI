package com.questionbank.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.questionbank.model.CodeSnippet;
import com.questionbank.model.Question;
import com.questionbank.model.Reference;
import com.questionbank.repository.QuestionRepository;
import com.questionbank.request.CodeSnippetRequest;
import com.questionbank.request.QuestionRequest;
import com.questionbank.request.ReferenceRequest;
import com.questionbank.response.CodeSnippetResponse;
import com.questionbank.response.QuestionResponse;
import com.questionbank.response.ReferenceResponse;

@Service
public class QuestionService {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	// Create or Save a Question
    public QuestionResponse createQuestion(QuestionRequest questionRequest) {
        Question question = mapToEntity(questionRequest);
        Question savedQuestion = questionRepository.save(question);
        return mapToResponse(savedQuestion);
    }

    // Update a Question
    public QuestionResponse updateQuestion(String questionId, QuestionRequest questionRequest) {
        Question existingQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + questionId));

        // Update fields
        existingQuestion.setQuestion(questionRequest.getQuestion());
        existingQuestion.setAnswer(questionRequest.getAnswer());
        existingQuestion.setLanguage(questionRequest.getLanguage());
        existingQuestion.setCategory(questionRequest.getCategory());
        existingQuestion.setDifficulty(questionRequest.getDifficulty());
        existingQuestion.setTags(questionRequest.getTags());
        existingQuestion.setAuthor(questionRequest.getAuthor());
        existingQuestion.setExampleInputs(questionRequest.getExampleInputs());
        existingQuestion.setExampleOutputs(questionRequest.getExampleOutputs());
        existingQuestion.setNotes(questionRequest.getNotes());
        existingQuestion.setRelatedTopics(questionRequest.getRelatedTopics());
        existingQuestion.setHints(questionRequest.getHints());

        // Update references and code snippets
        existingQuestion.setReferences(questionRequest.getReferences().stream()
                .map(this::mapToReference)
                .collect(Collectors.toList()));

        existingQuestion.setCodeSnippets(questionRequest.getCodeSnippets().stream()
                .map(this::mapToCodeSnippet)
                .collect(Collectors.toList()));

        Question updatedQuestion = questionRepository.save(existingQuestion);
        return mapToResponse(updatedQuestion);
    }

    // Get a Question by ID
    public QuestionResponse getQuestionById(String questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + questionId));
        return mapToResponse(question);
    }

    // Get All Questions
    public List<QuestionResponse> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Delete a Question by ID
    public void deleteQuestionById(String questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new RuntimeException("Question not found with id: " + questionId);
        }
        questionRepository.deleteById(questionId);
    }
	
	private Question mapToEntity(QuestionRequest dto) {
        Question question = new Question();
        question.setQuestionId(dto.getQuestionId());
        question.setQuestion(dto.getQuestion());
        question.setAnswer(dto.getAnswer());
        question.setLanguage(dto.getLanguage());
        question.setCategory(dto.getCategory());
        question.setDifficulty(dto.getDifficulty());
        question.setTags(dto.getTags());
        question.setAuthor(dto.getAuthor());
        question.setExampleInputs(dto.getExampleInputs());
        question.setExampleOutputs(dto.getExampleOutputs());
        question.setNotes(dto.getNotes());
        question.setRelatedTopics(dto.getRelatedTopics());
        question.setHints(dto.getHints());
        question.setCreatedAt(LocalDateTime.now());

        // Map references
        List<Reference> references = dto.getReferences().stream()
                .map(this::mapToReference)
                .collect(Collectors.toList());
        question.setReferences(references);

        // Map code snippets
        List<CodeSnippet> codeSnippets = dto.getCodeSnippets().stream()
                .map(this::mapToCodeSnippet)
                .collect(Collectors.toList());
        question.setCodeSnippets(codeSnippets);

        return question;
    }
	
	// Maps ReferenceRequest DTO to Reference entity
    private Reference mapToReference(ReferenceRequest refDto) {
        Reference reference = new Reference();
        reference.setTitle(refDto.getTitle());
        reference.setUrl(refDto.getUrl());
        return reference;
    }

    // Maps CodeSnippetRequest DTO to CodeSnippet entity
    private CodeSnippet mapToCodeSnippet(CodeSnippetRequest csDto) {
        CodeSnippet codeSnippet = new CodeSnippet();
        codeSnippet.setLanguage(csDto.getLanguage());
        codeSnippet.setCodeSnippet(csDto.getCodeSnippet());
        return codeSnippet;
    }
	
	private QuestionResponse mapToResponse(Question entity) {
        QuestionResponse response = new QuestionResponse();
        response.setQuestionId(entity.getQuestionId());
        response.setQuestion(entity.getQuestion());
        response.setAnswer(entity.getAnswer());
        response.setLanguage(entity.getLanguage());
        response.setCategory(entity.getCategory());
        response.setDifficulty(entity.getDifficulty());
        response.setTags(entity.getTags());
        response.setAuthor(entity.getAuthor());
        response.setExampleInputs(entity.getExampleInputs());
        response.setExampleOutputs(entity.getExampleOutputs());
        response.setNotes(entity.getNotes());
        response.setRelatedTopics(entity.getRelatedTopics());
        response.setHints(entity.getHints());

        // Map references
        List<ReferenceResponse> references = entity.getReferences().stream()
                .map(this::mapToReferenceResponse)
                .collect(Collectors.toList());
        response.setReferences(references);

        // Map code snippets
        List<CodeSnippetResponse> codeSnippets = entity.getCodeSnippets().stream()
                .map(this::mapToCodeSnippetResponse)
                .collect(Collectors.toList());
        response.setCodeSnippets(codeSnippets);

        return response;
    }
	
	 // Maps Reference entity to ReferenceResponse DTO
    private ReferenceResponse mapToReferenceResponse(Reference ref) {
        ReferenceResponse response = new ReferenceResponse();
        response.setTitle(ref.getTitle());
        response.setUrl(ref.getUrl());
        return response;
    }

    // Maps CodeSnippet entity to CodeSnippetResponse DTO
    private CodeSnippetResponse mapToCodeSnippetResponse(CodeSnippet cs) {
        CodeSnippetResponse response = new CodeSnippetResponse();
        response.setLanguage(cs.getLanguage());
        response.setCodeSnippet(cs.getCodeSnippet());
        return response;
    }

}
