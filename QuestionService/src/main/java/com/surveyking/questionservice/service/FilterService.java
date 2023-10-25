package com.surveyking.questionservice.service;

import com.surveyking.questionservice.model.FilterRequest;
import com.surveyking.questionservice.model.entity.Filter;
import com.surveyking.questionservice.model.entity.Question;
import com.surveyking.questionservice.repository.FilterRepository;
import com.surveyking.questionservice.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilterService {
    private final FilterRepository filterRepository;
    private final QuestionRepository questionRepository;
    public boolean add(FilterRequest request) {
        Optional<Question> question = questionRepository.findById(request.getQuestionId());
        if(question.isEmpty()) return false;
        for(Filter filter: request.getFilters()){
            filter.setQuestion(question.get());
        }
        filterRepository.saveAll(request.getFilters());
        return true;
    }
}
