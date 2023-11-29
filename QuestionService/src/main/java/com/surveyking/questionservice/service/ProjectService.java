package com.surveyking.questionservice.service;

import com.surveyking.questionservice.model.ProjectRequest;
import com.surveyking.questionservice.model.entity.Phase;
import com.surveyking.questionservice.model.entity.Project;
import com.surveyking.questionservice.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public boolean add(ProjectRequest projectRequest, String userId) {
        Project project = Project.builder()
                .name(projectRequest.getName())
                .projectType(projectRequest.getProjectType())
                .clientName(projectRequest.getClientName())
                .startDate(projectRequest.getStartDate())
                .endDate(projectRequest.getEndDate())
                .status(projectRequest.getStatus())
                .sasCode(projectRequest.getSasCode())
                .jobNumber(projectRequest.getJobNumber())
                .build();
        project.setOwner(userId);
        project.setMembers(Set.of(userId));
        Set<Phase> phases = new HashSet<>();
        for(Phase phase: projectRequest.getPhases()){
            phase.setProject(project);
            phases.add(phase);
        }
        project.setPhases(phases);
        projectRepository.save(project);
        return true;
    }

    @Transactional
    public boolean delete(String sasCode) {
        projectRepository.deleteBySasCode(sasCode);
        return true;
    }

    public Optional<List<Project>> getAll(String userId) {
        return projectRepository.findProjectByOwner(userId);
    }

    public Project get(String sasCode) {
        Optional<Project> project = projectRepository.findProjectBySasCode(sasCode);
        if (project.isEmpty()) return null;
        return project.get();
    }

    public Boolean addMember(String sasCode, String memberId) {
        Optional<Project> project = projectRepository.findProjectBySasCode(sasCode);
        if (project.isEmpty()) return false;
        else {
            project.get().getMembers().add(memberId);
            projectRepository.save(project.get());
            return true;
        }
    }

    public Boolean removeMember(String sasCode, String memberId) {
        Optional<Project> project = projectRepository.findProjectBySasCode(sasCode);
        if (project.isEmpty()) return false;
        else {
            project.get().getMembers().remove(memberId);
            projectRepository.save(project.get());
            return true;
        }
    }

    public List<String> getMembers(String sasCode) {
        Optional<Project> project = projectRepository.findProjectBySasCode(sasCode);
        if (project.isEmpty()) return List.of();
        else {
            return project.get().getMembers().stream().toList();
        }
    }
}
