package com.surveyking.questionservice.service;

import com.surveyking.questionservice.model.QuestionFilterRequest;
import com.surveyking.questionservice.model.entity.QuestionFilter;
import com.surveyking.questionservice.model.entity.Question;
import com.surveyking.questionservice.repository.QuestionFilterRepository;
import com.surveyking.questionservice.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuestionFilterService {
    private final QuestionFilterRepository questionFilterRepository;
    private final QuestionRepository questionRepository;

    public boolean add(QuestionFilterRequest request) {
        Optional<Question> question = questionRepository.findById(request.getQuestionId());
        if (question.isEmpty() || question.get().getQuestionFilter() != null) return false;
        request.getQuestionFilter().setQuestion(question.get());
        setParent(request.getQuestionFilter());
        questionFilterRepository.save(request.getQuestionFilter());
        return true;
    }

    private void setParent(QuestionFilter questionFilter) {
        if (questionFilter.getQuestionFiltersToOr() == null) return;
        for (QuestionFilter filter : questionFilter.getQuestionFiltersToOr()) {
            filter.setParent(questionFilter);
            setParent(filter);
        }
    }


    @Transactional
    public boolean delete(Long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        if (question.isEmpty()) return false;
        questionFilterRepository.deleteByQuestion(question.get());
        return true;
    }


    public Boolean add(long questionId, String expression) {
        QuestionFilter questionFilter = getQuestionFilter(0, expression.length() - 1, expression);
        if (questionFilter != null) {
            Optional<Question> question = questionRepository.findById(questionId);
            if (question.isEmpty()) return false;
            else {
                questionFilter.setQuestion(question.get());
                questionFilterRepository.save(questionFilter);
                return true;
            }
        } else {
            return false;
        }
    }

    //q1c1 && q2c2 || q3c3
    //((q1c1 && q2c2) || q3c3)
    private QuestionFilter getQuestionFilter(int st, int en, String expression) {
        /*if(expression.charAt(st) == '('){

        }
        Long qId = 0L;
        Long cId = 0L;
        int i = st;
        while (i <= en && (expression.charAt(i) >= '0' && expression.charAt(i) <= '9')) {
            qId = qId * 10L + (expression.charAt(i) - '0');
            i++;
        }
        if (expression.charAt(i) != 'C') return null;
        i++;
        while (i <= en && (expression.charAt(i) >= '0' && expression.charAt(i) <= '9')) {
            cId = cId * 10L + (expression.charAt(i) - '0');
            i++;
        }
        QuestionFilter questionFilter;
        if(expression.charAt(i) == ')'){
            questionFilter = QuestionFilter.builder()
                    .choiceIdToFilter(cId)
                    .questionIdToFilter(qId)
                    .build();
        }else if (expression.charAt(i) == '&'){
            questionFilter = QuestionFilter.builder()
                    .choiceIdToFilter(cId)
                    .questionIdToFilter(qId)
                    .questionFilterToAnd(getQuestionFilter(i + 1, en, expression))
                    .build();
        }else if (expression.charAt(i) == '|'){
            questionFilter = QuestionFilter.builder()
                    .choiceIdToFilter(cId)
                    .questionIdToFilter(qId)
                    .questionFiltersToOr(Set.of(getQuestionFilter(i + 1, en, expression)))
                    .build();
        }*/
        return null;
    }
}

        /*int i = st;
        while (i <= en) {
            if (expression.charAt(i) == '(') {
                int cnt = 1;
                int nSt = i + 1;
                int nEn = -1;
                for (int j = i + 1; j <= en; j++) {
                    if (expression.charAt(i) == '(') {
                        cnt++;
                    } else if (expression.charAt(i) == ')') {
                        cnt--;
                    }
                    if (cnt == 0) {
                        nEn = j - 1;
                        break;
                    }
                }
                if (cnt != 0) return null;
                else {
                    QuestionFilter q = getQuestionFilter(nSt, nEn, expression);
                    i = nEn + 1;
                }
            } else if (expression.charAt(i) == 'Q') {
                Long qId = 0L;
                Long cId = 0L;
                while (i <= en && (expression.charAt(i) >= '0' && expression.charAt(i) <= '9')) {
                    qId = qId * 10L + (expression.charAt(i) - '0');
                    i++;
                }
                if (expression.charAt(i) != 'C') return null;
                i++;
                while (i <= en && (expression.charAt(i) >= '0' && expression.charAt(i) <= '9')) {
                    cId = cId * 10L + (expression.charAt(i) - '0');
                    i++;
                }
                if()
                QuestionFilter questionFilter = QuestionFilter.builder()
                        .questionIdToFilter(qId)
                        .choiceIdToFilter(cId)
                        .build();
            } else {
                return null;
            }
          }*/
