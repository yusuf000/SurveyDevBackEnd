package com.surveyking.questionservice.model;

import com.surveyking.questionservice.util.Constants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false, length = 20)
    private String type;

    @Column(name = Constants.clientName, nullable = false, length = 50)
    private String clientName;

    @Column(name = Constants.startDate, nullable = false, length = 50)
    private String startDate;

    @Column(name = Constants.endDate, nullable = false, length = 50)
    private String endDate;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(unique = true, nullable = false, length = 20)
    private String sascode;

    @Column(name = Constants.jobNumber, nullable = false, length = 20)
    private Long jobNumber;
}
