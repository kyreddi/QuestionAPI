package com.questionbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.questionbank.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
}

