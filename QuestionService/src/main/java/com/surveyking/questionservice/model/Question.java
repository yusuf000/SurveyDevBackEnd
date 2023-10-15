package com.surveyking.questionservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long serial;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Language language;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private QuestionType questionType;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Project project;

    @Column(nullable = false, length = 1000)
    private String description;

    @OneToMany
    private Set<Choice> choices;
}
