package com.example.surveyservice.service;

import com.example.surveyservice.client.OwnershipClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnershipCheckService {
    private final OwnershipClient ownershipClient;

    public boolean checkProjectMembershipFromQuestionId(Long questionId, String userId){
        return Boolean.TRUE.equals(ownershipClient.checkProjectMembershipFromQuestionId(questionId, userId).getBody());
    }
}
