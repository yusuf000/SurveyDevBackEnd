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
    private final PhaseRepository phaseRepository;
    private final ChoiceRepository choiceRepository;
    private final QuestionTypeRepository questionTypeRepository;
    private final SurveyServiceClient surveyServiceClient;

    public boolean add(QuestionRequest request) {
        Optional<Language> language = languageRepository.findLanguageByCode(
                request.getLanguageCode()
        );
        Optional<Phase> phase = phaseRepository.findById(
                request.getPhaseId()
        );
        Optional<QuestionType> questionType = questionTypeRepository.findQuestionTypeByName(
                request.getQuestionType()
        );
        if (language.isEmpty() || phase.isEmpty() || questionType.isEmpty()) return false;

        Long serial = questionRepository.findMaxSerial();
        if(serial == null) serial = 0L;
        else serial++;

        Question question = Question.builder()
                .serial(serial)
                .language(language.get())
                .questionType(questionType.get())
                .phase(phase.get())
                .description(request.getDescription())
                .build();
        setQuestion(question, request.getChoices());
        setParent(null, question.getChoices());
        questionRepository.save(question);
        return true;
    }

    public Boolean add(List<QuestionRequest> requests) {
        if (requests == null || requests.isEmpty()) return true;

        Optional<Language> language = languageRepository.findLanguageByCode(
                requests.get(0).getLanguageCode()
        );
        Optional<Phase> phase = phaseRepository.findById(
                requests.get(0).getPhaseId()
        );
        Optional<QuestionType> questionType = questionTypeRepository.findQuestionTypeByName(
                requests.get(0).getQuestionType()
        );
        if (language.isEmpty() || phase.isEmpty() || questionType.isEmpty()) return false;

        List<Question> questions = new ArrayList<>();

        Long serial = questionRepository.findMaxSerial();
        if(serial == null) serial = 0L;
        else serial++;

        for (QuestionRequest request : requests) {
            Question question = Question.builder()
                    .serial(serial)
                    .language(language.get())
                    .questionType(questionType.get())
                    .phase(phase.get())
                    .description(request.getDescription())
                    .build();
            setQuestion(question, request.getChoices());
            setParent(null, question.getChoices());
            questions.add(question);
            serial++;
        }
        questionRepository.saveAll(questions);
        return true;
    }

    private void setQuestion(Question question, List<Choice> choices) {
        if (choices == null) return;
        for (Choice choice : choices) {
            choice.setQuestion(question);
        }
        question.setChoices(new HashSet<>(choices));
    }

    private void setParent(Choice parent, Set<Choice> choices) {
        if (choices == null || choices.isEmpty()) return;

        for (Choice choice : choices) {
            choice.setParent(parent);
            setParent(choice, choice.getChoices());
        }
    }

    public List<Question> getByPhaseId(Long phaseId) {
        Optional<Phase> phase = phaseRepository.findById(
                phaseId
        );
        if (phase.isEmpty()) return Collections.emptyList();
 //       return phase.get().getQuestions().stream().toList();
        return null;
    }

    public boolean delete(Long questionId) {
        questionRepository.deleteById(questionId);
        return true;
    }

    public Question get(Long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        return question.orElse(null);
    }

    public Question getNext(String userId, Long questionId) {
        Optional<Question> currentQuestion = questionRepository.findById(questionId);
        if (currentQuestion.isEmpty()) return null;

        List<Answer> answers = surveyServiceClient.getAllForUser(currentQuestion.get().getPhase().getProject().getSasCode(), userId, PrivilegeConstants.ANSWER_INFO).getBody();

        Question question = getQuestion(currentQuestion.get(), answers);
        if (question != null) {
            Set<Choice> choices = getChoices(question.getChoices(), answers);
            return Question.builder()
                    .id(question.getId())
                    .serial(question.getSerial())
                    .language(question.getLanguage())
                    .questionType(question.getQuestionType())
                    .description(question.getDescription())
                    .choices(choices)
                    .build();
        }
        return null;
    }

    private Set<Choice> getChoices(Set<Choice> choices, List<Answer> answers) {
        Set<Choice> finalChoices = new HashSet<>();
        if(choices == null || choices.isEmpty()) return finalChoices;

        for (Choice choice : choices) {
            if (!skipChoice(choice.getChoiceFilters(), answers)) {
                finalChoices.add(Choice.builder()
                        .id(choice.getId())
                        .serial(choice.getSerial())
                        .value(choice.getValue())
                        .choices(getChoices(choice.getChoices(), answers))
                        .build());
            }
        }
        return finalChoices;
    }

    private boolean skipChoice(ChoiceFilter choiceFilter, List<Answer> answers) {
        if (choiceFilter == null) return false;
        boolean result = false;
        for (Answer answer : answers) {
            if (
                    checkChoiceFilter(answer, choiceFilter)
            ) {
                result = true;
                break;
            }
        }
        if (choiceFilter.getChoiceFilterToAnd() != null)
            result &= skipChoice(choiceFilter.getChoiceFilterToAnd(), answers);

        for (ChoiceFilter choiceFilterOr : choiceFilter.getChoiceFiltersToOr()) {
            result |= skipChoice(choiceFilterOr, answers);
        }
        return result;
    }

    private boolean checkChoiceFilter(Answer answer, ChoiceFilter choiceFilter) {
        return checkFilter(answer, choiceFilter.getChoiceIdToFilter(), choiceFilter.getQuestionIdToFilter(), choiceFilter.getValueEqual(), choiceFilter.getValueSmaller(), choiceFilter.getValueGreater());
    }

    private Question getQuestion(Question currentQuestion, List<Answer> answers) {

        Long nextSerial = currentQuestion.getSerial() + 1;
        Optional<Question> nextQuestion = questionRepository.findBySerial(nextSerial);
        if (nextQuestion.isEmpty()) return null;
        if (nextQuestion.get().getQuestionFilter() == null) return nextQuestion.get();

        return findNextQuestion(nextQuestion.get(), answers);
    }

    private Question findNextQuestion(Question currentQuestion, List<Answer> answers) {
        if (!skipQuestion(currentQuestion.getQuestionFilter(), answers)) {
            return currentQuestion;
        } else {
            return getQuestion(currentQuestion, answers);
        }
    }

    private boolean skipQuestion(QuestionFilter questionFilter, List<Answer> answers) {
        if (questionFilter == null) return false;
        boolean result = false;
        for (Answer answer : answers) {
            if (
                    checkQuestionFilter(answer, questionFilter)
            ) {
                result = true;
                break;
            }
        }
        if (questionFilter.getQuestionFilterToAnd() != null)
            result &= skipQuestion(questionFilter.getQuestionFilterToAnd(), answers);

        for (QuestionFilter questionFilterOr : questionFilter.getQuestionFiltersToOr()) {
            result |= skipQuestion(questionFilterOr, answers);
        }
        return result;
    }

    private boolean checkQuestionFilter(Answer answer, QuestionFilter questionFilter) {
        return checkFilter(answer, questionFilter.getChoiceIdToFilter(), questionFilter.getQuestionIdToFilter(), questionFilter.getValueEqual(), questionFilter.getValueSmaller(), questionFilter.getValueGreater());
    }

    private boolean checkFilter(Answer answer, Long choiceIdToFilter, Long questionIdToFilter, String valueEqual, String valueSmaller, String valueGreater) {
        if (answer.getChoiceId().longValue() == choiceIdToFilter.longValue()
                && answer.getId().getQuestionId().longValue() == questionIdToFilter.longValue()) {
            Optional<Choice> choice = choiceRepository.findById(choiceIdToFilter);
            if (choice.isPresent()) {
                return (valueEqual == null || valueEqual.equals(choice.get().getValue()))
                        && (valueSmaller == null || Double.parseDouble(valueSmaller) > Double.parseDouble(choice.get().getValue()))
                        && (valueSmaller == null || Double.parseDouble(valueGreater) < Double.parseDouble(choice.get().getValue()));
            }
        }
        return false;
    }
}
