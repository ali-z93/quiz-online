package com.aliz.quizonline.service;


import java.util.List;
import java.util.Optional;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.aliz.quizonline.model.Question;
import com.aliz.quizonline.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService {

	private final QuestionRepository questionRepository;

	@Override
	public Question createeQuestion(Question question) {
		// TODO Auto-generated method stub
		return questionRepository.save(question);
	}

	@Override
	public List<Question> getAllQuestion() {
		// TODO Auto-generated method stub

		return questionRepository.findAll();
	}

	@Override
	public Optional<Question> getQuestionById(Long id) {
		// TODO Auto-generated method stub
		return questionRepository.findById(id);
	}

	@Override
	public List<String> getAllSubjects() {
		// TODO Auto-generated method stub
		return questionRepository.findDistinctSubject();
	}

	@Override
	public Question updateQuestion(Long Id, Question question) throws NotFoundException {
		// TODO Auto-generated method stub
		Optional<Question> theQuestion = this.getQuestionById(Id);

		if (theQuestion.isPresent()) {
			Question updatedQuestion = theQuestion.get();
			updatedQuestion.setQuestion(question.getQuestion());
			updatedQuestion.setChoices(question.getChoices());
			updatedQuestion.setCorrectAnswers(question.getCorrectAnswers());
			return questionRepository.save(updatedQuestion);
		} else {
			throw new ChangeSetPersister.NotFoundException();
		}

	}

	@Override
	public void deleteQuestion(Long id) {
		// TODO Auto-generated method stub
		questionRepository.deleteById(id);

	}

	@Override
	public List<Question> getQuestionForUser(Integer numberOfQuestoions, String subject) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(0, numberOfQuestoions);
		return questionRepository.findBySubject(subject, pageable).getContent();
	}

}
