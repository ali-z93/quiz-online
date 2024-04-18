package com.aliz.quizonline.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.aliz.quizonline.model.Question;

public interface IQuestionService {

	Question createeQuestion(Question question);

	List<Question> getAllQuestion();

	Optional<Question> getQuestionById(Long id);

	List<String> getAllSubjects();

	Question updateQuestion(Long Id, Question question) throws NotFoundException;

	void deleteQuestion(Long id);

	List<Question> getQuestionForUser(Integer numberOfQuestoions, String subject);
}
