package com.surveyking.questionservice.service;

import com.surveyking.questionservice.model.entity.Project;
import com.surveyking.questionservice.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public boolean add(Project project, String userId){
        project.setOwner(userId);
        projectRepository.save(project);
        return true;
    }

    @Transactional
    public boolean  delete(String sasCode) {
        projectRepository.deleteBySasCode(sasCode);
        return true;
    }

    public Optional<List<Project>> getAll(String userId){
        return projectRepository.findProjectByOwner(userId);
    }

    public Optional<Project> get(String sasCode){
        return projectRepository.findProjectBySasCode(sasCode);
    }
}
