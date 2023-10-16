package com.surveyking.questionservice.service;

import com.surveyking.questionservice.model.entity.Language;
import com.surveyking.questionservice.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageService {
    private final LanguageRepository languageRepository;

    public boolean save(Language request) {
        languageRepository.save(request);
        return true;
    }

    public List<Language> get() {
        return languageRepository.findAll();
    }
}
