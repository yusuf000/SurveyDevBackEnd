package com.surveyking.questionservice.service;

import com.surveyking.questionservice.client.SurveyServiceClient;
import com.surveyking.questionservice.constants.PrivilegeConstants;
import com.surveyking.questionservice.model.Answer;
import com.surveyking.questionservice.model.QuestionRequest;
import com.surveyking.questionservice.model.entity.*;
import com.surveyking.questionservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final LanguageRepository languageRepository;
    private final ProjectRepository projectRepository;
    private final ChoiceRepository choiceRepository;
    private final QuestionTypeRepository questionTypeRepository;
    private final SurveyServiceClient surveyServiceClient;

    public boolean add(QuestionRequest request) {
        Optional<Language> language = languageRepository.findLanguageByCode(
                request.getLanguageCode()
        );
        Optional<Project> project = projectRepository.findProjectBySasCode(
                request.getProjectSasCode()
        );
        Optional<QuestionType> questionType = questionTypeRepository.findQuestionTypeByName(
                request.getQuestionType()
        );
        if (language.isEmpty() || project.isEmpty() || questionType.isEmpty()) return false;
        Question question = Question.builder()
                .serial(request.getSerial())
                .language(language.get())
                .questionType(questionType.get())
                .project(project.get())
                .description(request.getDescription())
                .build();
        setQuestion(question, request.getChoices());
        setParent(null, question.getChoices());
        questionRepository.save(question);
        return true;
    }

    public Boolean add(List<QuestionRequest> requests) {
        if(requests == null || requests.isEmpty()) return true;

        Optional<Language> language = languageRepository.findLanguageByCode(
                requests.get(0).getLanguageCode()
        );
        Optional<Project> project = projectRepository.findProjectBySasCode(
                requests.get(0).getProjectSasCode()
        );
        Optional<QuestionType> questionType = questionTypeRepository.findQuestionTypeByName(
                requests.get(0).getQuestionType()
        );
        if (language.isEmpty() || project.isEmpty() || questionType.isEmpty()) return false;

        List<Question> questions = new ArrayList<>();
        for(QuestionRequest request: requests){
            Question question = Question.builder()
                    .serial(request.getSerial())
                    .language(language.get())
                    .questionType(questionType.get())
                    .project(project.get())
                    .description(request.getDescription())
                    .build();
            setQuestion(question, request.getChoices());
            setParent(null, question.getChoices());
            questions.add(question);
        }
        questionRepository.saveAll(questions);
        return true;
    }

    private void setQuestion(Question question, List<Choice> choices) {
        if(choices == null) return;
        for(Choice choice: choices){
            choice.setQuestion(question);
        }
        question.setChoices(new HashSet<>(choices));
    }

    private void setParent(Choice parent, Set<Choice> choices) {
        if(choices == null || choices.isEmpty()) return;

        for(Choice choice: choices){
            choice.setParent(parent);
            setParent(choice, choice.getChoices());
        }
    }

    public List<Question> get(String sasCode) {
        Optional<Project> project = projectRepository.findProjectBySasCode(
                sasCode
        );
        if (project.isEmpty()) return Collections.emptyList();
        return questionRepository.findAllByProject(project.get());
    }

    public boolean delete(Long questionId) {
        questionRepository.deleteById(questionId);
        return true;
    }

    public Optional<Question> get(Long questionId) {
        return questionRepository.findById(questionId);
    }

    public Question getNext(String userId, Long questionId) {
        Optional<Question> currentQuestion = questionRepository.findById(questionId);
        if(currentQuestion.isEmpty()) return null;

        Long nextSerial = currentQuestion.get().getSerial() + 1;
        Optional<Question> nextQuestion = questionRepository.findBySerial(nextSerial);
        if(nextQuestion.isEmpty()) return null;
        if(nextQuestion.get().getQuestionFilter() == null) return nextQuestion.get();

        List<Answer> answers = surveyServiceClient.getAllForUser(currentQuestion.get().getProject().getSasCode(), userId, PrivilegeConstants.ANSWER_INFO).getBody();
        return findNextQuestion(nextQuestion.get(), answers);
    }

    private Question findNextQuestion(Question currentQuestion, List<Answer> answers) {
        if(!skipQuestion(currentQuestion.getQuestionFilter(), answers)){
            return currentQuestion;
        }else{
            Long nextSerial = currentQuestion.getSerial() + 1;
            Optional<Question> nextQuestion = questionRepository.findBySerial(nextSerial);
            if(nextQuestion.isEmpty()) return null;
            if(nextQuestion.get().getQuestionFilter() == null) return nextQuestion.get();

            return findNextQuestion(nextQuestion.get(), answers);
        }
    }

    private boolean skipQuestion(QuestionFilter questionFilter, List<Answer> answers) {
        if(questionFilter == null) return false;
        boolean result = false;
        for(Answer answer: answers){
            if (
                    checkAnswer(answer, questionFilter)
            ) {
                result = true;
                break;
            }
        }
        if(questionFilter.getQuestionFilterToAnd() != null) result &= skipQuestion(questionFilter.getQuestionFilterToAnd(), answers);

        for(QuestionFilter questionFilterOr: questionFilter.getQuestionFiltersToOr()){
            result |= skipQuestion(questionFilterOr, answers);
        }
        return result;
    }

    private boolean checkAnswer(Answer answer, QuestionFilter questionFilter) {
        if(answer.getChoiceId().longValue() == questionFilter.getChoiceIdToFilter().longValue()
                && answer.getId().getQuestionId().longValue() == questionFilter.getQuestionIdToFilter().longValue()){
            Optional<Choice> choice = choiceRepository.findById(questionFilter.getChoiceIdToFilter());
            if(choice.isPresent()){
                return (questionFilter.getValueEqual() == null || questionFilter.getValueEqual().equals(choice.get().getValue()))
                        && (questionFilter.getValueSmaller() == null || Double.parseDouble(questionFilter.getValueSmaller()) > Double.parseDouble(choice.get().getValue()))
                        && (questionFilter.getValueSmaller() == null || Double.parseDouble(questionFilter.getValueGreater()) < Double.parseDouble(choice.get().getValue()));
            }
        }
        return false;
    }
}
