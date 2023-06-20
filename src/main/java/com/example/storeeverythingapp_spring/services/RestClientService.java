package com.example.storeeverythingapp_spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestClientService {

    @Autowired
    RestTemplate connector;

    String url ="http://localhost:8090/dictionary";

    public boolean checkIfWordIsInDictionary(String word) {
        ResponseEntity<Boolean> response = connector.getForEntity(url+"/checkWord?word="+word, Boolean.class);
        boolean check = response.getBody();
        return check;
    }
}
