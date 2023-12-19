package com.surveyking.questionservice.service;

import com.surveyking.questionservice.model.ChoiceRequest;
import com.surveyking.questionservice.model.QuestionRequest;
import com.surveyking.questionservice.model.entity.Choice;
import com.surveyking.questionservice.model.entity.Phase;
import com.surveyking.questionservice.model.entity.Project;
import com.surveyking.questionservice.model.entity.Question;
import com.surveyking.questionservice.repository.ChoiceRepository;
import com.surveyking.questionservice.repository.PhaseRepository;
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
    private final PhaseRepository phaseRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;

    public boolean checkProjectOwner(String sasCode, String userId){
        if(sasCode == null || sasCode.isEmpty()) return false;
        Optional<Project> project = projectRepository.findProjectBySasCode(sasCode);
        if(project.isEmpty()) return false;
        else{
            return project.get().getOwner().equals(userId);
        }
    }

    public boolean checkProjectMembershipFromPhaseId(Long phaseId, String userId){
        if(phaseId == null) return false;
        Optional<Phase> phase = phaseRepository.findById(phaseId);
        if(phase.isEmpty()) return false;
        else{
            return phase.get().getProject().getMembers().contains(userId);
        }
    }

    public boolean checkProjectMembershipFromQuestionRequests(List<QuestionRequest> requests, String userId){
        for(QuestionRequest request: requests){
            if(!checkProjectMembershipFromPhaseId(request.getPhaseId(), userId)){
                return false;
            }
        }
        return true;
    }

    public boolean checkProjectMembershipFromQuestionId(Long questionId, String userId) {
        if(questionId == null) return false;
        Optional<Question> question = questionRepository.findById(questionId);
        if(question.isEmpty()) return false;
        else{
            return question.get().getPhase().getProject().getMembers().contains(userId);
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
        if(choiceId == null) return false;
        Optional<Choice> choice = choiceRepository.findById(choiceId);
        if(choice.isEmpty()) return false;
        else{
            Choice parentChoice = findParentChoice(choice.get());
            return parentChoice.getQuestion().getPhase().getProject().getMembers().contains(userId);
        }
    }

    private Choice findParentChoice(Choice choice){
        if(choice.getParent() == null) return choice;
        else return findParentChoice(choice.getParent());
    }
}
