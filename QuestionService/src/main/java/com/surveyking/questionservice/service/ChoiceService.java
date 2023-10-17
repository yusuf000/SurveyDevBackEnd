package com.surveyking.questionservice.service;

import com.surveyking.questionservice.model.ChoiceRequest;
import com.surveyking.questionservice.model.entity.Choice;
import com.surveyking.questionservice.model.entity.Question;
import com.surveyking.questionservice.repository.ChoiceRepository;
import com.surveyking.questionservice.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChoiceService {
    private final ChoiceRepository choiceRepository;
    private final QuestionRepository questionRepository;

    public boolean add(ChoiceRequest request) {
        Optional<Question> question = questionRepository.findById(request.getQuestionId());
        if (question.isEmpty()) return false;

        Choice choice = Choice.builder()
                .serial(request.getSerial())
                .value(request.getValue())
                .question(question.get())
                .choices(new HashSet<>(request.getChoices()))
                .build();
        choiceRepository.save(choice);
        return true;
    }

    public List<Choice> get(Long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        if (question.isEmpty()) return Collections.emptyList();
        return choiceRepository.findAllByQuestion(question.get());
    }
}
