package com.example.surveyservice.repository;

import com.example.surveyservice.model.AnswerId;
import com.example.surveyservice.model.Chart;
import com.example.surveyservice.model.entity.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResponseRepository extends MongoRepository<Response, AnswerId> {
    List<Response> findAllByIdQuestionId(Long questionId);
    List<Response> findAllBySasCodeAndIdUserId(String sasCode, String userId);
    Long countByDateAndSasCode(String date, String sasCode);
    Long countBySasCode(String sasCode);

    Page<Response> findAllByIdQuestionId(Long questionId, Pageable pageable);

    @Aggregation(
            pipeline = {
                    "{'$match':{'_id.questionId': ?0}}",
                    "{'$group' : {'_id': '$answers.choiceId', 'count':{$sum:1}}}",
                    "{'$addFields' : {'choiceId' : { $first: '$_id' }}}",
                    "{'$project' :  {'_id':  0}}"
            }
    )
    List<Chart> findChartByQuestionId(Long questionId);
}
