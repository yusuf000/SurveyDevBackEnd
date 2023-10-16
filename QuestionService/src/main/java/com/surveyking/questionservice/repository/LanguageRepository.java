package com.surveyking.questionservice.repository;

import com.surveyking.questionservice.model.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    Optional<Language> findLanguageByCode(String code);
}
