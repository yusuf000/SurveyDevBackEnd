package com.surveyking.questionservice.service;

import com.surveyking.questionservice.model.entity.QuestionType;
import com.surveyking.questionservice.repository.QuestionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionTypeService {
    private final QuestionTypeRepository questionTypeRepository;

    public boolean save(QuestionType questionType){
        questionTypeRepository.save(questionType);
        return true;
    }

    public List<QuestionType> get(){
        return questionTypeRepository.findAll();
    }
}
