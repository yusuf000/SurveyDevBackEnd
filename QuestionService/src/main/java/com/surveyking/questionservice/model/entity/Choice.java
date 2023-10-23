package com.surveyking.questionservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "choice")
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Question question;

    @Column(nullable = false)
    private Long serial;

    @Column(nullable = false)
    private String value;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Choice parent;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, mappedBy = "parent")
    private Set<Choice> choices;
}
