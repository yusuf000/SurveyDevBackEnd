package com.surveyking.questionservice.repository;

import com.surveyking.questionservice.model.entity.Project;
import com.surveyking.questionservice.model.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByProject(Project project);
}
