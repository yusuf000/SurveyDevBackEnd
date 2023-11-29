package com.surveyking.questionservice.service;

import com.surveyking.questionservice.model.entity.Phase;
import com.surveyking.questionservice.model.entity.Project;
import com.surveyking.questionservice.repository.PhaseRepository;
import com.surveyking.questionservice.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhaseService {
    private final ProjectRepository projectRepository;
    private final PhaseRepository phaseRepository;

    public Boolean add(String sasCode, String name) {
        Optional<Project> project = projectRepository.findProjectBySasCode(sasCode);
        if (project.isEmpty()) return false;
        Optional<Phase> lastPhase = project.get().getPhases().stream().max(Comparator.comparingInt(Phase::getSerial));
        int serial = 0;
        if(lastPhase.isPresent()) serial = lastPhase.get().getSerial() + 1;

        Phase phase = Phase.builder()
                .project(project.get())
                .name(name)
                .serial(serial)
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
    public List<Phase> get(String sasCode) {
        Optional<Project> project = projectRepository.getProjectByCode(sasCode);
        if (project.isEmpty()) return List.of();

        return project.get().getPhases().stream().toList();
    }

}
