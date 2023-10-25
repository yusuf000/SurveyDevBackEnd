package com.surveyking.questionservice.model;

import com.surveyking.questionservice.model.entity.Filter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterRequest {
    Long questionId;
    List<Filter> filters;
}
