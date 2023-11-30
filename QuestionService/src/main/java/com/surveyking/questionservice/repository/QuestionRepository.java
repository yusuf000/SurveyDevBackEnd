package com.surveyking.questionservice.repository;

import com.surveyking.questionservice.model.entity.Phase;
import com.surveyking.questionservice.model.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByPhase(Phase phase);
    Optional<Question> findBySerial(Long Serial);
}
