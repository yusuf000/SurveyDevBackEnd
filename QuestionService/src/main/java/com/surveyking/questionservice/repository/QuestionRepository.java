package com.surveyking.questionservice.repository;

import com.surveyking.questionservice.model.entity.Phase;
import com.surveyking.questionservice.model.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findBySerial(Long Serial);

    @Query("select max(q.serial) from question q where q.phase.id = :phaseId")
    Long findMaxSerial(Long phaseId);
}
