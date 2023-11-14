package com.surveyking.questionservice.service;

import com.surveyking.questionservice.model.ChoiceRequest;
import com.surveyking.questionservice.model.QuestionRequest;
import com.surveyking.questionservice.model.entity.Choice;
import com.surveyking.questionservice.model.entity.Project;
import com.surveyking.questionservice.model.entity.Question;
import com.surveyking.questionservice.repository.ChoiceRepository;
import com.surveyking.questionservice.repository.ProjectRepository;
import com.surveyking.questionservice.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OwnershipCheckService {
    private final ProjectRepository projectRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;

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

    public boolean checkProjectMembershipFromChoiceRequests(List<ChoiceRequest> requests, String userId) {
        for(ChoiceRequest request: requests){
            if(!checkProjectMembershipFromQuestionId(request.getQuestionId(), userId)){
                return false;
            }
        }
        return true;
    }

    public boolean checkProjectMembershipFromChoiceId(Long choiceId, String userId) {
        Optional<Choice> choice = choiceRepository.findById(choiceId);
        if(choice.isEmpty()) return false;
        else{
            return choice.get().getQuestion().getProject().getMembers().contains(userId);
        }
    }
}
