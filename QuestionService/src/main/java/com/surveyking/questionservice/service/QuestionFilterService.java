package com.surveyking.questionservice.service;

import com.surveyking.questionservice.model.QuestionFilterRequest;
import com.surveyking.questionservice.model.entity.QuestionFilter;
import com.surveyking.questionservice.model.entity.Question;
import com.surveyking.questionservice.repository.QuestionFilterRepository;
import com.surveyking.questionservice.repository.QuestionRepository;
import jakarta.transaction.Transactional;
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
        request.getQuestionFilter().setQuestion(question.get());
        setParent(request.getQuestionFilter());
        questionFilterRepository.save(request.getQuestionFilter());
        return true;
    }

    private void setParent(QuestionFilter questionFilter) {
        if(questionFilter.getQuestionFiltersToOr() == null) return;
        for(QuestionFilter filter: questionFilter.getQuestionFiltersToOr()){
            filter.setParent(questionFilter);
            setParent(filter);
        }
    }


    @Transactional
    public boolean delete(Long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        if(question.isEmpty()) return false;
        questionFilterRepository.deleteByQuestion(question.get());
        return true;
    }
}
