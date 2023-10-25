package com.surveyking.questionservice.service;

import com.surveyking.questionservice.model.QuestionRequest;
import com.surveyking.questionservice.model.entity.*;
import com.surveyking.questionservice.repository.LanguageRepository;
import com.surveyking.questionservice.repository.ProjectRepository;
import com.surveyking.questionservice.repository.QuestionRepository;
import com.surveyking.questionservice.repository.QuestionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final LanguageRepository languageRepository;
    private final ProjectRepository projectRepository;
    private final QuestionTypeRepository questionTypeRepository;

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
}
