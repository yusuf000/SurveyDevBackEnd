package com.surveyking.questionservice.service;

import com.surveyking.questionservice.client.SurveyServiceClient;
import com.surveyking.questionservice.constants.PrivilegeConstants;
import com.surveyking.questionservice.model.ProjectRequest;
import com.surveyking.questionservice.model.RunningProjectResponse;
import com.surveyking.questionservice.model.entity.Phase;
import com.surveyking.questionservice.model.entity.Project;
import com.surveyking.questionservice.model.entity.ProjectCompletionStatus;
import com.surveyking.questionservice.repository.ProjectCompletionStatusRepository;
import com.surveyking.questionservice.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectCompletionStatusRepository projectCompletionStatusRepository;
    private final SurveyServiceClient surveyServiceClient;

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
        if (projectRequest.getPhases() == null || projectRequest.getPhases().isEmpty()) {
            phases.add(Phase.builder()
                    .serial(0)
                    .name(project.getName())
                    .project(project)
                    .build());
        } else {
            for (Phase phase : projectRequest.getPhases()) {
                phase.setProject(project);
                phases.add(phase);
            }
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
        return projectRepository.findByMembersContains(userId);
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

    public List<RunningProjectResponse> getRunningProject(String userId) {
        Optional<List<Project>> runningProjects = projectRepository.findProjectByOwnerAndStatus(userId, "running");
        if (runningProjects.isEmpty()) return List.of();
        return runningProjects.get().stream().map(p-> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            long difference = 0;
            try {
                difference = new Date().getTime() - (sdf.parse(p.getStartDate()).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return RunningProjectResponse.builder().sasCode(p.getSasCode()).projectName(p.getName()).startedFor(TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS)+"").build();
        }).collect(Collectors.toList());
    }

    public Boolean completeProjectSurvey(String sasCode, String userId) {
        projectCompletionStatusRepository.save(ProjectCompletionStatus.builder()
                .userId(userId)
                .sasCode(sasCode)
                .build());
        return surveyServiceClient.completeSurvey(sasCode, userId, PrivilegeConstants.ANSWER_INFO).getBody();
    }

    public List<ProjectCompletionStatus> getProjectCompletionStatuses(String userId){
        return projectCompletionStatusRepository.findProjectCompletionStatusByUserId(userId);
    }
}
