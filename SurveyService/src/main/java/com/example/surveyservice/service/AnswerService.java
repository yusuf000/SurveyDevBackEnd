package com.example.surveyservice.service;

import com.example.surveyservice.model.AnswerId;
import com.example.surveyservice.model.ResponseRequest;
import com.example.surveyservice.model.entity.Answer;
import com.example.surveyservice.model.entity.Response;
import com.example.surveyservice.model.entity.ResponseCache;
import com.example.surveyservice.repository.AnswerRedisRepository;
import com.example.surveyservice.repository.ResponseRedisRepository;
import com.example.surveyservice.repository.ResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final ResponseRedisRepository responseRedisRepository;
    private final ResponseRepository responseRepository;
    private final AnswerRedisRepository answerRedisRepository;

    public boolean submit(ResponseRequest request, String userId) {
        if (!isValid(request)) return false;
        Answer answer = Answer.builder()
                .choiceId(request.getChoiceId())
                .description(request.getDescription())
                .serial(request.getSerial())
                .build();
        answerRedisRepository.save(answer);

        Optional<ResponseCache> cachedResponse = responseRedisRepository.findById(
                AnswerId.builder()
                        .questionId(request.getQuestionId())
                        .userId(userId)
                        .build()
        );
        if (cachedResponse.isPresent()) {
            cachedResponse.get().getAnswers().add(answer);
            responseRedisRepository.save(cachedResponse.get());
        } else {
            ResponseCache response = ResponseCache.builder()
                    .id(AnswerId.builder()
                            .userId(userId)
                            .questionId(request.getQuestionId())
                            .build())
                    .sasCode(request.getSasCode())
                    .phaseId(request.getPhaseId())
                    .answers(Set.of(answer))
                    .build();
            responseRedisRepository.save(response);
        }

        return true;
    }

    private boolean isValid(ResponseRequest request) {
        return !((request.getDescription() == null || request.getDescription().isEmpty())
                && (request.getChoiceId() == null) || request.getSasCode() == null || request.getSasCode().isEmpty());
    }

    public List<ResponseCache> getAll(Long questionId) {
        return responseRedisRepository.findAllByIdQuestionId(questionId);
    }

    public List<ResponseCache> getAllForUser(Long phaseId, String userId) {
        return responseRedisRepository.findAllByPhaseIdAndIdUserId(phaseId, userId);
    }

    public boolean completeSurveyForUser(String sasCode, String userId) {
        List<ResponseCache> responsesInCache = responseRedisRepository.findAllBySasCodeAndIdUserId(sasCode, userId);
        List<Response> responses = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (ResponseCache responseCache : responsesInCache) {
            responses.add(Response.builder()
                    .id(responseCache.getId())
                    .phaseId(responseCache.getPhaseId())
                    .sasCode(responseCache.getSasCode())
                    .answers(responseCache.getAnswers())
                    .date(sdf.format(new Date()))
                    .build());
        }
        responseRepository.saveAll(responses);
        return true;
    }
}
