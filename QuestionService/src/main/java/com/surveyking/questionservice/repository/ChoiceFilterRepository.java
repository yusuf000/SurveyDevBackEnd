package com.surveyking.questionservice.repository;

import com.surveyking.questionservice.model.entity.Choice;
import com.surveyking.questionservice.model.entity.ChoiceFilter;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface ChoiceFilterRepository extends JpaRepository<ChoiceFilter, Integer> {

    @Modifying(clearAutomatically=true, flushAutomatically = true)
    @Transactional
    void deleteByChoice(Choice choice);
}
