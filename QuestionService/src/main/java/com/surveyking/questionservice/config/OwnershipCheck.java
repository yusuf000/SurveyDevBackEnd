package com.surveyking.questionservice.config;

import com.surveyking.questionservice.model.entity.Project;
import com.surveyking.questionservice.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OwnershipCheck {
    private final ProjectRepository projectRepository;

    public boolean checkProjectOwner(String sasCode, String userId){
        Optional<Project> project = projectRepository.findProjectBySasCode(sasCode);
        if(project.isEmpty()) return false;
        else{
            return project.get().getOwner().equals(userId);
        }
    }
}
