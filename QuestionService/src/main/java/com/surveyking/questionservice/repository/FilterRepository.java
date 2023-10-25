package com.surveyking.questionservice.repository;

import com.surveyking.questionservice.model.entity.Filter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilterRepository extends JpaRepository<Filter, Long> {
}
