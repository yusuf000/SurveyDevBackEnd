package com.surveyking.questionservice.repository;

import com.surveyking.questionservice.model.entity.Phase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhaseRepository extends JpaRepository<Phase, Long> {
}
