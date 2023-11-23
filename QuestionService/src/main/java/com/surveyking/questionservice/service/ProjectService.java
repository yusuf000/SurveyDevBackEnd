package com.surveyking.questionservice.service;

import com.surveyking.questionservice.model.entity.Project;
import com.surveyking.questionservice.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public boolean add(Project project, String userId){
        project.setOwner(userId);
        project.setMembers(Set.of(userId));
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

    public Boolean addMember(String sasCode, String memberId) {
        Optional<Project> project = projectRepository.findProjectBySasCode(sasCode);
        if(project.isEmpty()) return false;
        else{
            project.get().getMembers().add(memberId);
            projectRepository.save(project.get());
            return true;
        }
    }

    public Boolean removeMember(String sasCode, String memberId) {
        Optional<Project> project = projectRepository.findProjectBySasCode(sasCode);
        if(project.isEmpty()) return false;
        else{
            project.get().getMembers().remove(memberId);
            projectRepository.save(project.get());
            return true;
        }
    }

    public List<String> getMember(String sasCode) {
        Optional<Project> project = projectRepository.findProjectBySasCode(sasCode);
        if(project.isEmpty()) return List.of();
        else{
            return project.get().getMembers().stream().toList();
        }
    }
}
