package com.questionbank.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "answer", nullable = false, columnDefinition = "TEXT")
    private String answer;

    @Column(name = "language", nullable = false)
    private String language;
    
    @Column(name = "category")
    private String category;

    @Column(name = "difficulty")
    private String difficulty;

    @ElementCollection
    @CollectionTable(name = "question_tags", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "tag")
    private List<String> tags;

    @Column(name = "author")
    private String author;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

//    @CreationTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ElementCollection
    @CollectionTable(name = "example_inputs", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "input_example")
    private List<String> exampleInputs;

    @ElementCollection
    @CollectionTable(name = "example_outputs", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "output_example")
    private List<String> exampleOutputs;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @ElementCollection
    @CollectionTable(name = "related_topics", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "related_topic")
    private List<String> relatedTopics;

    @ElementCollection
    @CollectionTable(name = "hints", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "hint")
    private List<String> hints;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "question_id")
    private List<Reference> references;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "question_id")
    private List<CodeSnippet> codeSnippets;


}
