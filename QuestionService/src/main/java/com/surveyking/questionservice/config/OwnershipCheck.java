package com.surveyking.questionservice.config;

import com.surveyking.questionservice.model.QuestionRequest;
import com.surveyking.questionservice.model.entity.Project;
import com.surveyking.questionservice.model.entity.Question;
import com.surveyking.questionservice.repository.ProjectRepository;
import com.surveyking.questionservice.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OwnershipCheck {
    private final ProjectRepository projectRepository;
    private final QuestionRepository questionRepository;

    public boolean checkProjectOwner(String sasCode, String userId){
        Optional<Project> project = projectRepository.findProjectBySasCode(sasCode);
        if(project.isEmpty()) return false;
        else{
            return project.get().getOwner().equals(userId);
        }
    }

    public boolean checkProjectMembershipFromQuestionRequest(QuestionRequest request, String userId){
        Optional<Project> project = projectRepository.findProjectBySasCode(request.getProjectSasCode());
        if(project.isEmpty()) return false;
        else{
            return project.get().getMembers().contains(userId);
        }
    }

    public boolean checkProjectMembershipFromQuestionRequests(List<QuestionRequest> requests, String userId){
        for(QuestionRequest request: requests){
            if(!checkProjectMembershipFromQuestionRequest(request, userId)){
                return false;
            }
        }
        return true;
    }

    public boolean checkProjectMembershipFromSasCode(String sasCode, String userId) {
        Optional<Project> project = projectRepository.findProjectBySasCode(sasCode);
        if(project.isEmpty()) return false;
        else{
            return project.get().getMembers().contains(userId);
        }
    }

    public boolean checkProjectMembershipFromQuestionId(Long questionId, String userId) {
        Optional<Question> question = questionRepository.findById(questionId);
        if(question.isEmpty()) return false;
        else{
            return question.get().getProject().getMembers().contains(userId);
        }
    }
}
