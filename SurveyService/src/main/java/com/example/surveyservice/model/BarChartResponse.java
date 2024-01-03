package com.example.surveyservice.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BarChartResponse {
    private List<String> labels;
    private DataSet datasets;
    private Long total;
}
