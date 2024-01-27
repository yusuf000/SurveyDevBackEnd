package com.surveyking.questionservice.service;

import com.surveyking.questionservice.model.entity.Language;
import com.surveyking.questionservice.repository.LanguageRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LanguageServiceTest {
    @Mock
    private LanguageRepository languageRepository;
    private LanguageService languageService;

    @BeforeEach
    void setUp() {
        languageService = new LanguageService(languageRepository);
    }

    @Test
    void LanguageService_Add_ReturnTrue() {
        //given
        Language language = Language.builder()
                .code("eng")
                .name("English")
                .build();
        //when
        boolean result = languageService.add(language);
        //then
        Assertions.assertThat(result).isTrue();
        ArgumentCaptor<Language> languageArgumentCaptor = ArgumentCaptor.forClass(Language.class);
        Mockito.verify(languageRepository).save(languageArgumentCaptor.capture());
        Language capturedLanguage = languageArgumentCaptor.getValue();
        Assertions.assertThat(capturedLanguage).isEqualTo(language);
    }

    @Test
    void get() {
    }
}