package com.surveyking.apigateway.service;

import com.surveyking.apigateway.repository.SecretKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecretKeyService {
    private final SecretKeyRepository secretKeyRepository;
    private static String SECRET_KEY = "";

    public String getSecretKey(){
        if(SECRET_KEY.isEmpty()){
            if(secretKeyRepository.count() == 0){
                SECRET_KEY = "5641ba8955470cf9bf8b347825be5aa540f2adaf86c93d37416866dadaf1593b";
            }else{
                SECRET_KEY = secretKeyRepository.findAll().get(0).getSecretKey();
            }
        }
        return SECRET_KEY;
    }
}
