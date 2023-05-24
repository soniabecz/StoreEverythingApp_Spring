package com.example.storeeverythingapp_spring.repositories;

import com.example.storeeverythingapp_spring.data.Category;
import com.example.storeeverythingapp_spring.data.Information;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Getter
public class InformationRepository {
    List<Information> infos = new ArrayList<>();
    List<Category> categories = new ArrayList<>();

    public InformationRepository() {

    }

    public Information getInfo(int id) {
        return infos.get(id);
    }

    public List<Information> getInfosFromCategory(String category) {
        List<Information> filteredInfos = new ArrayList<>();
        for (Information info:infos) {
            if(info.getCategory().getName().equals(category)) {
                filteredInfos.add(info);
            }
        }
        return filteredInfos;
    }

    public void addInfo(Information newInfo) {
        infos.add(newInfo);
    }

    public void addCategory(Category newCat) {
        categories.add(newCat);
    }
}
