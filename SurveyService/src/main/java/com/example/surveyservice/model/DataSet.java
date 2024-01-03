package com.example.surveyservice.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataSet{
    private String label;
    List<Long> data;
}
