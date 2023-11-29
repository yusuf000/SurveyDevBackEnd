package com.surveyking.questionservice.service;

import com.surveyking.questionservice.model.entity.Phase;
import com.surveyking.questionservice.model.entity.Project;
import com.surveyking.questionservice.repository.PhaseRepository;
import com.surveyking.questionservice.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhaseService {
    private final ProjectRepository projectRepository;
    private final PhaseRepository phaseRepository;

    public Boolean add(String sasCode) {
        Optional<Project> project = projectRepository.findProjectBySasCode(sasCode);
        if (project.isEmpty()) return false;

        Phase phase = Phase.builder()
                .project(project.get())
                .build();
        phaseRepository.save(phase);
        return true;
    }

    public Boolean delete(Long phaseId) {
        Optional<Phase> phase = phaseRepository.findById(phaseId);
        if(phase.isEmpty()) return false;

        phaseRepository.delete(phase.get());
        return true;
    }
    public List<Long> get(String sasCode) {
        Optional<Project> project = projectRepository.getProjectByCode(sasCode);
        if (project.isEmpty()) return List.of();

        return project.get().getPhases().stream().map(Phase::getId).collect(Collectors.toList());
    }

}
