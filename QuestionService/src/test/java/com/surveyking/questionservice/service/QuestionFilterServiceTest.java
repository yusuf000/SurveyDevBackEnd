package com.surveyking.questionservice.service;

import com.surveyking.questionservice.exceptions.InvalidExpressionException;
import com.surveyking.questionservice.model.entity.Language;
import com.surveyking.questionservice.model.entity.Question;
import com.surveyking.questionservice.repository.ChoiceRepository;
import com.surveyking.questionservice.repository.QuestionFilterRepository;
import com.surveyking.questionservice.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class QuestionFilterServiceTest {
    @Mock
    private QuestionFilterRepository questionFilterRepository;
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private ChoiceRepository choiceRepository;
    private QuestionFilterService questionFilterService;

    @BeforeEach
    void setUp() {
        questionFilterService = new QuestionFilterService(questionFilterRepository, questionRepository, choiceRepository);
    }

    @Test
    void QuestionFilterService_Add_WhenValidQuestionId_ThrowsException()  {
        given(questionRepository.findById(anyLong()))
                .willReturn(Optional.of(Question.builder()
                                .description("dummy")
                                .serial(1L)
                                .id(1L)
                                .language(Language.builder()
                                        .name("English")
                                        .code("eng")
                                        .build())
                        .build()));

        assertThatThrownBy(() -> questionFilterService.add(1L, "Q1C1", "Q1C1"))
                .isInstanceOf(InvalidExpressionException.class)
                .hasMessageContaining("Invalid expression");

        verify(questionFilterRepository, never()).save(any());
        //verify(questionFilterRepository, times(1)).save(any());
    }
}