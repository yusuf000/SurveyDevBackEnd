package com.surveyking.questionservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.surveyking.questionservice.util.Constants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false, length = 20)
    private String type;

    @Column(name = Constants.clientName, nullable = false, length = 50)
    private String clientName;

    @Column(name = Constants.owner, nullable = false, length = 50)
    private String owner;

    @Column(name = Constants.startDate, nullable = false, length = 50)
    private String startDate;

    @Column(name = Constants.endDate, nullable = false, length = 50)
    private String endDate;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(name = Constants.sasCode, unique = true, nullable = false, length = 20)
    private String sasCode;

    @Column(name = Constants.jobNumber, nullable = false, length = 20)
    private Long jobNumber;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, mappedBy = "project")
    @JsonIgnore
    private Set<Phase> phases;

    @ElementCollection(targetClass = String.class)
    @JsonIgnore
    private Set<String> members;
}
