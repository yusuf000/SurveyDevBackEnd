package com.surveyking.questionservice.repository;

import com.surveyking.questionservice.model.entity.Phase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhaseRepository extends JpaRepository<Phase, Long> {
    Optional<Phase> findByProjectSasCodeAndName(String sasCode, String name);
}
