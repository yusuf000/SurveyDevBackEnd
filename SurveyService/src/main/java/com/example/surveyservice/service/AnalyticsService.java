package com.example.surveyservice.service;

import com.example.surveyservice.model.BarChartResponse;
import com.example.surveyservice.model.DataSet;
import com.example.surveyservice.model.entity.Response;
import com.example.surveyservice.repository.ResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final ResponseRepository responseRepository;
    public BarChartResponse getLastFiveDaysResponseCount(String sasCode) {
        Calendar calendar  = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        calendar.add(Calendar.DATE, -6);
        List<String> labels = new ArrayList<>();
        List<Long> data = new ArrayList<>();
        Long total = responseRepository.countBySasCode(sasCode);

        for(int i = 0; i < 7; i++){
            String currentDate = sdf.format(new Date(calendar.getTimeInMillis()));
            labels.add(currentDate.substring(0,5));
            data.add(responseRepository.countByDateAndSasCode(currentDate, sasCode));
            calendar.add(Calendar.DATE, 1);
        }

        return BarChartResponse.builder()
                .labels(labels)
                .datasets(DataSet.builder()
                        .label("responses")
                        .data(data)
                        .build())
                .total(total)
                .build();
    }

    public Page<Response> findAllByQuestionId(Long questionId, Integer pageNo){
        Pageable pageable = PageRequest.of(pageNo, 10);
        return responseRepository.findAllByIdQuestionId(questionId, pageable);
    }
}
