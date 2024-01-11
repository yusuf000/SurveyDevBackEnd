package com.surveyking.questionservice.model;

import com.surveyking.questionservice.model.entity.Phase;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest {

    private String name;

    private String clientName;

    private String owner;

    private String startDate;

    private String endDate;

    private String sasCode;

    private Long jobNumber;

    private ProjectType projectType;

    private List<Phase> phases;
}
