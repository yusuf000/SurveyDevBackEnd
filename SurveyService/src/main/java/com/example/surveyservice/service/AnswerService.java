package com.example.surveyservice.service;

import com.example.surveyservice.model.AnswerId;
import com.example.surveyservice.model.AnswerRequest;
import com.example.surveyservice.model.entity.Answer;
import com.example.surveyservice.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public boolean submit(AnswerRequest request) {
        if (!isValid(request)) return false;
        Answer answer = Answer.builder()
                .id(AnswerId.builder()
                        .userId(request.getUserId())
                        .questionId(request.getQuestionId())
                        .build())
                .choiceId(request.getChoiceId())
                .description(request.getDescription())
                .build();
        answerRepository.save(answer);
        return true;
    }

    private boolean isValid(AnswerRequest request) {
        return !((request.getDescription() == null || request.getDescription().isEmpty())
                && (request.getChoiceId() == null));
    }

    public List<Answer> getAll(Long questionId) {
        return answerRepository.findAllByIdQuestionId(questionId);
    }
}
