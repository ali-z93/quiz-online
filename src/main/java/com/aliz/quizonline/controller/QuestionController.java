package com.aliz.quizonline.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aliz.quizonline.model.Question;
import com.aliz.quizonline.service.IQuestionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/quizzes")
@RequiredArgsConstructor
public class QuestionController {

	private final IQuestionService questionservice;

	@PostMapping("create-new-question")
	public ResponseEntity<Question> createQuestion(@Valid @RequestBody Question question) {
		Question createdQuestion = questionservice.createeQuestion(question);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
	}

	@GetMapping("/all-questions")
	public ResponseEntity<List<Question>> getAllQuestions() {
		List<Question> questions = questionservice.getAllQuestion();
		return ResponseEntity.ok(questions);
	}

	@GetMapping("/question/{id}")
	public ResponseEntity<Optional<Question>> getQuestionById(@PathVariable Long id) throws NotFoundException {
		Optional<Question> theQuestion = questionservice.getQuestionById(id);
		if (theQuestion.isPresent()) {
			return ResponseEntity.ok(theQuestion);
		} else {
			throw new ChangeSetPersister.NotFoundException();
		}

	}

	@PutMapping("/{id}/update")
	public ResponseEntity<Question> update(@PathVariable Long id, @RequestBody Question question)
			throws NotFoundException {
		Question updatedQuestion = questionservice.updateQuestion(id, question);
		return ResponseEntity.ok(updatedQuestion);
	}

	@DeleteMapping("{id}/delete")
	public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
		questionservice.deleteQuestion(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/subejcts")
	public ResponseEntity<List<String>> getAllSubjects() {
		List<String> subjects = questionservice.getAllSubjects();
		return ResponseEntity.ok(subjects);
	}

	@GetMapping("fetch-questions-for-user")
	public ResponseEntity<List<Question>> getQuestionsForUser(@RequestParam Integer numOfQuestion, @RequestParam String subjects){
		List<Question> allQuestions = questionservice.getQuestionForUser(numOfQuestion, subjects);
		
		List<Question> mutableQuestions = new ArrayList<>(allQuestions);
		Collections.shuffle(mutableQuestions);
		
		int availableQuestions = Math.min(numOfQuestion, mutableQuestions.size());
		List<Question> randomQuestions = mutableQuestions.subList(0, availableQuestions);
		return ResponseEntity.ok(randomQuestions);
		
	}
	
}
