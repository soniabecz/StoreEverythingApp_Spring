package com.example.storeeverythingapp_spring.listeners;

import com.example.storeeverythingapp_spring.data.db.InformationEntity;
import com.example.storeeverythingapp_spring.repositories.db.InformationEntityRepository;
import com.example.storeeverythingapp_spring.services.AppService;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;


@SessionScope
public class CustomSessionListener implements HttpSessionListener {

    @Autowired
    InformationEntityRepository informationEntityRepository;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("sesja start");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("sesja koniec");
        List<InformationEntity> informationEntities = (List<InformationEntity>) se.getSession().getAttribute("newInfos");

        System.out.println(informationEntities);

        informationEntityRepository.saveAll(informationEntities);
    }
}

