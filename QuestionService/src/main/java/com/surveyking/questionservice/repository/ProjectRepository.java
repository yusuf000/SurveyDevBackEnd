package com.surveyking.questionservice.repository;

import com.surveyking.questionservice.model.Status;
import com.surveyking.questionservice.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findProjectBySasCode(String code);
    Optional<List<Project>> findProjectByOwner(String owner);

    Optional<List<Project>> findByMembersContains(String member);
    void deleteBySasCode(String code);

    Optional<List<Project>> findProjectByOwnerAndStatus(String owner, Status status);
}
