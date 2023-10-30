package com.surveyking.questionservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Language language;

    @ManyToOne
    private QuestionType questionType;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Project project;

    @Column(nullable = false, length = 1000)
    private String description;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, mappedBy = "question")
    private Set<Choice> choices;

    @OneToOne
    private QuestionFilter questionFilter;
}
