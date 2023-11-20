package com.example.surveyservice.service;

import com.example.surveyservice.model.AnswerId;
import com.example.surveyservice.model.AnswerRequest;
import com.example.surveyservice.model.entity.Answer;
import com.example.surveyservice.model.entity.AnswerCache;
import com.example.surveyservice.repository.AnswerRedisRepository;
import com.example.surveyservice.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRedisRepository answerRedisRepository;
    private final AnswerRepository answerRepository;

    public boolean submit(AnswerRequest request, String userId) {
        if (!isValid(request)) return false;
        AnswerCache answer = AnswerCache.builder()
                .id(AnswerId.builder()
                        .userId(userId)
                        .questionId(request.getQuestionId())
                        .build())
                .sasCode(request.getSasCode())
                .choiceId(request.getChoiceId())
                .description(request.getDescription())
                .build();
        answerRedisRepository.save(answer);
        return true;
    }

    private boolean isValid(AnswerRequest request) {
        return !((request.getDescription() == null || request.getDescription().isEmpty())
                && (request.getChoiceId() == null) || request.getSasCode() == null || request.getSasCode().isEmpty());
    }

    public List<AnswerCache> getAll(Long questionId) {
        return answerRedisRepository.findAllByIdQuestionId(questionId);
    }

    public List<AnswerCache> getAllForUser(String sasCode, String userId) {
        return answerRedisRepository.findAllBySasCodeAndIdUserId(sasCode, userId);
    }

    public boolean completeSurveyForUser(String sasCode, String userId) {
        List<AnswerCache> answersInCache = getAllForUser(sasCode, userId);
        List<Answer> answers = new ArrayList<>();
        for (AnswerCache answerCache : answersInCache) {
            answers.add(Answer.builder()
                    .id(answerCache.getId())
                    .sasCode(answerCache.getSasCode())
                    .choiceId(answerCache.getChoiceId())
                    .description(answerCache.getDescription())
                    .build());
        }
        answerRepository.saveAll(answers);
        return true;
    }
}
