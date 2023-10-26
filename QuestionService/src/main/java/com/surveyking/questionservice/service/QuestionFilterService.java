package com.surveyking.questionservice.service;

import com.surveyking.questionservice.model.QuestionFilterRequest;
import com.surveyking.questionservice.model.entity.QuestionFilter;
import com.surveyking.questionservice.model.entity.Question;
import com.surveyking.questionservice.repository.QuestionFilterRepository;
import com.surveyking.questionservice.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionFilterService {
    private final QuestionFilterRepository questionFilterRepository;
    private final QuestionRepository questionRepository;
    public boolean add(QuestionFilterRequest request) {
        Optional<Question> question = questionRepository.findById(request.getQuestionId());
        if(question.isEmpty()) return false;
        for(QuestionFilter questionFilter : request.getQuestionFilters()){
            questionFilter.setQuestion(question.get());
        }
        questionFilterRepository.saveAll(request.getQuestionFilters());
        return true;
    }

    public boolean delete(Long filterId) {
        questionFilterRepository.deleteById(filterId);
        return true;
    }
}
