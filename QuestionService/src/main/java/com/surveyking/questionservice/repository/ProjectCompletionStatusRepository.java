package com.surveyking.questionservice.repository;

import com.surveyking.questionservice.model.entity.ProjectCompletionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectCompletionStatusRepository  extends JpaRepository<ProjectCompletionStatus, Long> {
    List<ProjectCompletionStatus> findProjectCompletionStatusByUserId(String userId);
}
