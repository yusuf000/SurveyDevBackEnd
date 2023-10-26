package com.surveyking.questionservice.repository;

import com.surveyking.questionservice.model.entity.Choice;
import com.surveyking.questionservice.model.entity.ChoiceFilter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChoiceFilterRepository extends JpaRepository<ChoiceFilter, Integer> {
    void deleteByChoice(Choice choice);
}
