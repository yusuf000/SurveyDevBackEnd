package com.surveyking.questionservice.repository;

import com.surveyking.questionservice.model.entity.Choice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {
}
