package com.surveyking.questionservice.service;


import com.surveyking.questionservice.model.ChoiceFilterRequest;
import com.surveyking.questionservice.model.entity.Choice;
import com.surveyking.questionservice.model.entity.ChoiceFilter;
import com.surveyking.questionservice.repository.ChoiceFilterRepository;
import com.surveyking.questionservice.repository.ChoiceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChoiceFilterService {
    private final ChoiceFilterRepository choiceFilterRepository;
    private final ChoiceRepository choiceRepository;
    public boolean add(ChoiceFilterRequest request) {
        Optional<Choice> choice = choiceRepository.findById(request.getChoiceId());
        if(choice.isEmpty() || choice.get().getChoiceFilters() != null) return false;
        request.getChoiceFilter().setChoice(choice.get());
        setParent(request.getChoiceFilter());
        choiceFilterRepository.save(request.getChoiceFilter());
        return true;
    }

    private void setParent(ChoiceFilter choiceFilter) {
        if(choiceFilter.getChoiceFiltersToOr() == null) return;
        for(ChoiceFilter filter: choiceFilter.getChoiceFiltersToOr()){
            filter.setParent(choiceFilter);
            setParent(filter);
        }
    }


    @Transactional
    public boolean delete(Long choiceId) {
        Optional<Choice> choice = choiceRepository.findById(choiceId);
        if(choice.isEmpty()) return false;
        choiceFilterRepository.deleteByChoice(choice.get());
        return true;
    }
}
