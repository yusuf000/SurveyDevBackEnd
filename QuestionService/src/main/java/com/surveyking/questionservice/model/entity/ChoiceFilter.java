package com.surveyking.questionservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "choice_filter")
public class ChoiceFilter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JsonIgnore
    private Choice choice;

    @Column(nullable = false)
    private Long questionIdToFilter;

    @Column(nullable = false)
    private Long choiceIdToFilter;

    private String valueEqual;
    private String valueGreater;
    private String valueSmaller;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private ChoiceFilter parent;

    @OneToOne(cascade = CascadeType.ALL)
    private ChoiceFilter choiceFilterToAnd;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, mappedBy = "parent")
    private Set<ChoiceFilter> choiceFiltersToOr;
}
