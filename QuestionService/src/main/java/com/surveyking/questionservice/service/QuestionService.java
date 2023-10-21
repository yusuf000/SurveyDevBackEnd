package com.surveyking.questionservice.service;

import com.surveyking.questionservice.model.QuestionRequest;
import com.surveyking.questionservice.model.entity.Language;
import com.surveyking.questionservice.model.entity.Project;
import com.surveyking.questionservice.model.entity.Question;
import com.surveyking.questionservice.model.entity.QuestionType;
import com.surveyking.questionservice.repository.LanguageRepository;
import com.surveyking.questionservice.repository.ProjectRepository;
import com.surveyking.questionservice.repository.QuestionRepository;
import com.surveyking.questionservice.repository.QuestionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final LanguageRepository languageRepository;
    private final ProjectRepository projectRepository;
    private final QuestionTypeRepository questionTypeRepository;

    public boolean save(QuestionRequest request) {
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
        questionRepository.save(question);
        return true;
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
}
