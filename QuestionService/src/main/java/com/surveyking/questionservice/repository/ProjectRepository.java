package com.surveyking.questionservice.repository;

import com.surveyking.questionservice.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findProjectBySasCode(String code);
    Optional<List<Project>> findProjectByOwner(String owner);
    void deleteBySasCode(String code);

    @Query("SELECT e1 FROM project e1 LEFT JOIN FETCH e1.phases WHERE e1.sasCode = :code")
    Optional<Project> getProjectByCode(String code);
}
