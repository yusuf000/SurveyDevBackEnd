package com.surveyking.questionservice.service;

import com.surveyking.questionservice.model.ChoiceRequest;
import com.surveyking.questionservice.model.entity.Choice;
import com.surveyking.questionservice.model.entity.Question;
import com.surveyking.questionservice.repository.ChoiceRepository;
import com.surveyking.questionservice.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

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
        setParent(choice, choice.getChoices());
        choiceRepository.save(choice);
        return true;
    }


    public Boolean add(List<ChoiceRequest> requests) {
        if(requests == null || requests.isEmpty()) return true;
        Optional<Question> question = questionRepository.findById(requests.get(0).getQuestionId());
        if (question.isEmpty()) return false;

        List<Choice> choices = new ArrayList<>();
        for(ChoiceRequest request : requests){
            Choice choice = Choice.builder()
                    .serial(request.getSerial())
                    .value(request.getValue())
                    .question(question.get())
                    .choices(new HashSet<>(request.getChoices()))
                    .build();
            setParent(choice, choice.getChoices());
            choices.add(choice);
        }
        choiceRepository.saveAll(choices);
        return true;
    }

    private void setParent(Choice parent, Set<Choice> choices) {
        if(choices == null || choices.isEmpty()) return;

        for(Choice choice: choices){
            choice.setParent(parent);
            setParent(choice, choice.getChoices());
        }
    }


    public boolean delete(Long choiceId) {
        choiceRepository.deleteById(choiceId);
        return true;
    }


    public List<Choice> get(Long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        if (question.isEmpty()) return Collections.emptyList();
        return choiceRepository.findAllByQuestion(question.get());
    }

}
