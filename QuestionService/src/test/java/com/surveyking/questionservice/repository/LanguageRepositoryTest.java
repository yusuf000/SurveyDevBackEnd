package com.surveyking.questionservice.repository;

import com.surveyking.questionservice.model.entity.Language;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;


@DataJpaTest
class LanguageRepositoryTest {

    @Autowired
    private LanguageRepository languageRepository;

    @AfterEach
    void tearDown() {
        languageRepository.deleteAll();
    }

    @Test
    public void LanguageRepository_FindLanguageByCode_ReturnOptionalLanguagePresent(){
        Language language = Language.builder()
                .name("English")
                .code("eng")
                .build();

        languageRepository.save(language);
        Optional<Language> expectedLanguage = languageRepository.findLanguageByCode("eng");

        Assertions.assertThat(expectedLanguage.isPresent()).isTrue();
        Assertions.assertThat(expectedLanguage.get().getCode()).isEqualTo("eng");
    }

    @Test
    public void LanguageRepository_FindLanguageByCode_ReturnOptionalLanguageEmpty(){
        Optional<Language> expectedLanguage = languageRepository.findLanguageByCode("eng");

        Assertions.assertThat(expectedLanguage.isEmpty()).isTrue();
    }
}