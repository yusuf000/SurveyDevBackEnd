package com.surveyking.questionservice.service;


import com.surveyking.questionservice.exceptions.InvalidExpressionException;
import com.surveyking.questionservice.model.ChoiceFilterRequest;
import com.surveyking.questionservice.model.entity.Choice;
import com.surveyking.questionservice.model.entity.ChoiceFilter;
import com.surveyking.questionservice.model.entity.Question;
import com.surveyking.questionservice.repository.ChoiceFilterRepository;
import com.surveyking.questionservice.repository.ChoiceRepository;
import com.surveyking.questionservice.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChoiceFilterService {
    private final ChoiceFilterRepository choiceFilterRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    public boolean add(ChoiceFilterRequest request) {
        Optional<Choice> choice = choiceRepository.findById(request.getChoiceId());
        if(choice.isEmpty() || request.getExpressionToEvaluate() == null) return false;
        try {
            ChoiceFilter choiceFilter = getChoiceFilter(0,request.getExpressionToEvaluate(), null).getSecond();
            saveChoiceFilter(choice.get(), choiceFilter);
            choice.get().setChoiceFilterExpression(request.getExpressionToShow());

            if(choice.get().getChoices() != null && choice.get().getChoices().size() != 0){
                for(Choice subChoices: choice.get().getChoices()){
                    subChoices.setChoiceFilterExpression(request.getExpressionToShow());
                }
            }
            choiceRepository.save(choice.get());
        } catch (InvalidExpressionException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void saveChoiceFilter(Choice choice, ChoiceFilter choiceFilter) {
        choiceFilterRepository.deleteByChoice(choice);
        choiceFilter.setChoice(choice);
        setParent(choiceFilter);
        choiceFilterRepository.save(choiceFilter);
    }

    private void setParent(ChoiceFilter choiceFilter) {
        if(choiceFilter.getChoiceFiltersToOr() == null) return;
        for(ChoiceFilter filter: choiceFilter.getChoiceFiltersToOr()){
            filter.setParent(choiceFilter);
            setParent(filter);
        }
    }

    private Pair<Integer, ChoiceFilter> getChoiceFilter(int st, String expression, ChoiceFilter parentChoiceFilter) throws InvalidExpressionException{
        ChoiceFilter currentChoiceFilter;
        int i = st;
        Pair<Integer, ChoiceFilter> response;
        if(expression.charAt(i) == '('){
            response = getChoiceFilter(i + 1, expression,null);
            currentChoiceFilter = ChoiceFilter.builder()
                    .choiceFiltersToOr(new HashSet<>())
                    .build();
            currentChoiceFilter.getChoiceFiltersToOr().add(response.getSecond());
        }
        else{
            response = createChoiceFilter(i, expression);
            currentChoiceFilter = response.getSecond();
        }
        i = response.getFirst();

        if(i < expression.length()){
            if(parentChoiceFilter == null) parentChoiceFilter = currentChoiceFilter;
            if (expression.charAt(i) == '&') {
                Pair<Integer, ChoiceFilter> res = getChoiceFilter(i + 1, expression, parentChoiceFilter);
                i = res.getFirst();
                currentChoiceFilter.setChoiceFilterToAnd(res.getSecond());
            } else if (expression.charAt(i) == '|') {
                Pair<Integer, ChoiceFilter> res = getChoiceFilter(i + 1, expression, parentChoiceFilter);
                i = res.getFirst();
                parentChoiceFilter.getChoiceFiltersToOr().add(res.getSecond());
            }else if(expression.charAt(i) == ')'){
                i++;
            }else{
                throw new InvalidExpressionException("Invalid expression");
            }
        }


        return Pair.of(i, currentChoiceFilter);
    }

    private Pair<Integer, ChoiceFilter> createChoiceFilter(int st, String expression) throws InvalidExpressionException {
        long qId = 0L;
        long cId = 0L;
        if(expression.charAt(st) != 'Q') throw new InvalidExpressionException("Invalid expression");
        int i = st + 1;
        while (i < expression.length() && (expression.charAt(i) >= '0' && expression.charAt(i) <= '9')) {
            qId = qId * 10L + (expression.charAt(i) - '0');
            i++;
        }
        if (expression.charAt(i) != 'C') throw new InvalidExpressionException("Invalid expression");
        i++;
        while (i < expression.length() && (expression.charAt(i) >= '0' && expression.charAt(i) <= '9')) {
            cId = cId * 10L + (expression.charAt(i) - '0');
            i++;
        }
        Optional<Question> questionToFilter = questionRepository.findById(qId);
        Optional<Choice> choiceToFilter = choiceRepository.findById(cId);
        if(questionToFilter.isEmpty() || choiceToFilter.isEmpty())  throw new InvalidExpressionException("Invalid expression");
        return Pair.of(i, ChoiceFilter.builder()
                .choiceToFilter(choiceToFilter.get())
                .questionToFilter(questionToFilter.get())
                .choiceFiltersToOr(new HashSet<>())
                .build());
    }


    public boolean delete(Long choiceId) {
        Optional<Choice> choice = choiceRepository.findById(choiceId);
        if(choice.isEmpty()) return false;
        choiceFilterRepository.deleteByChoice(choice.get());
        clearChoiceExpression(choice.get());
        choiceRepository.save(choice.get());
        return true;
    }

    private void clearChoiceExpression(Choice choice) {
        choice.setChoiceFilterExpression("");
        for(Choice subChoice: choice.getChoices()){
            clearChoiceExpression(subChoice);
        }
    }
}
