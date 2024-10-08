package com.surveyking.questionservice.service;

import com.surveyking.questionservice.exceptions.InvalidExpressionException;
import com.surveyking.questionservice.model.entity.Choice;
import com.surveyking.questionservice.model.entity.Question;
import com.surveyking.questionservice.model.entity.QuestionFilter;
import com.surveyking.questionservice.repository.ChoiceRepository;
import com.surveyking.questionservice.repository.QuestionFilterRepository;
import com.surveyking.questionservice.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionFilterService {
    private final QuestionFilterRepository questionFilterRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;

    private void setParent(QuestionFilter questionFilter) {
        if (questionFilter.getQuestionFiltersToOr() == null) return;
        for (QuestionFilter filter : questionFilter.getQuestionFiltersToOr()) {
            filter.setParent(questionFilter);
            setParent(filter);
        }
    }


    public boolean delete(Long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        if (question.isEmpty()) return false;
        questionFilterRepository.deleteByQuestion(question.get());
        question.get().setQuestionFilterExpression("");
        questionRepository.save(question.get());
        return true;
    }


    public Boolean add(long questionId, String expressionToEvaluate, String expressionToShow) throws InvalidExpressionException {

            QuestionFilter questionFilter = getQuestionFilter(0,  expressionToEvaluate, null).getSecond();
            Optional<Question> question = questionRepository.findById(questionId);
            if (question.isEmpty())  throw new InvalidExpressionException("Invalid expression");
            else {
                questionFilterRepository.deleteByQuestion(question.get());
                questionFilter.setQuestion(question.get());
                setParent(questionFilter);
                questionFilterRepository.save(questionFilter);
                question.get().setQuestionFilterExpression(expressionToShow);
                questionRepository.save(question.get());
                return true;
            }

    }

    private Pair<Integer, QuestionFilter> getQuestionFilter(int st, String expression, QuestionFilter parentQuestionFilter) throws InvalidExpressionException{
        QuestionFilter currentQuestionFilter;
        int i = st;
        Pair<Integer, QuestionFilter> response;
        if(expression.charAt(i) == '('){
            response = getQuestionFilter(i + 1, expression,null);
            currentQuestionFilter = QuestionFilter.builder()
                    .questionFiltersToOr(new HashSet<>())
                    .build();
            currentQuestionFilter.getQuestionFiltersToOr().add(response.getSecond());
        }
        else{
            response = createQuestionFilter(i, expression);
            currentQuestionFilter = response.getSecond();
        }
        i = response.getFirst();

        if(i < expression.length()){
            if(parentQuestionFilter == null) parentQuestionFilter = currentQuestionFilter;
            if (expression.charAt(i) == '&') {
                Pair<Integer, QuestionFilter> res = getQuestionFilter(i + 1, expression, parentQuestionFilter);
                i = res.getFirst();
                currentQuestionFilter.setQuestionFilterToAnd(res.getSecond());
            } else if (expression.charAt(i) == '|') {
                Pair<Integer, QuestionFilter> res = getQuestionFilter(i + 1, expression, parentQuestionFilter);
                i = res.getFirst();
                parentQuestionFilter.getQuestionFiltersToOr().add(res.getSecond());
            }else if(expression.charAt(i) == ')'){
                i++;
            }else{
                throw new InvalidExpressionException("Invalid expression");
            }
        }


        return Pair.of(i, currentQuestionFilter);
    }

    private Pair<Integer, QuestionFilter> createQuestionFilter(int st, String expression) throws InvalidExpressionException {
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
        return Pair.of(i, QuestionFilter.builder()
                .choiceToFilter(choiceToFilter.get())
                .questionToFilter(questionToFilter.get())
                .questionFiltersToOr(new HashSet<>())
                .build());
    }
}

