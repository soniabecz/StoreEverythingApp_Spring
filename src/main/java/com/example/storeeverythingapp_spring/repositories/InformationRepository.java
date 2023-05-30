package com.example.storeeverythingapp_spring.repositories;

import com.example.storeeverythingapp_spring.data.Category;
import com.example.storeeverythingapp_spring.data.Information;
import com.example.storeeverythingapp_spring.validators.category.CategoryValidation;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public List<Information> sortByNameASC() {
        if (infos.size() > 0) {
            Collections.sort(infos, new Comparator<Information>() {
                @Override
                public int compare(final Information info1, final Information info2) {
                    return info1.getTitle().compareTo(info2.getTitle());
                }
            });
        }
        return infos;
    }

    public List<Information> sortByNameDSC() {
        if (infos.size() > 0) {
            Collections.sort(infos, new Comparator<Information>() {
                @Override
                public int compare(final Information info1, final Information info2) {
                    return info2.getTitle().compareTo(info1.getTitle());
                }
            });
        }
        return infos;
    }

    public List<Information> sortByDateASC() {
        if (infos.size() > 0) {
            Collections.sort(infos, new Comparator<Information>() {
                @Override
                public int compare(final Information info1, final Information info2) {
                    return info1.getDate().compareTo(info2.getDate());
                }
            });
        }
        return infos;
    }

    public List<Information> sortByDateDSC() {
        if (infos.size() > 0) {
            Collections.sort(infos, new Comparator<Information>() {
                @Override
                public int compare(final Information info1, final Information info2) {
                    return info2.getDate().compareTo(info1.getDate());
                }
            });
        }
        return infos;
    }

    public List<Information> sortByCategoryASC() {
        if (infos.size() > 0) {
            Collections.sort(infos, new Comparator<Information>() {
                @Override
                public int compare(final Information info1, final Information info2) {
                    return info1.getCategory().getName().compareTo(info2.getCategory().getName());
                }
            });
        }
        return infos;
    }

    public List<Information> sortByCategoryDSC() {
        if (infos.size() > 0) {
            Collections.sort(infos, new Comparator<Information>() {
                @Override
                public int compare(final Information info1, final Information info2) {
                    return info2.getCategory().getName().compareTo(info1.getCategory().getName());
                }
            });
        }
        return infos;
    }

    public void addInfo(Information newInfo) {
        infos.add(newInfo);
    }
    public void removeInfo(int id) {
        infos.remove(id);
    }
    public void editInfo(int id, Information newInfo) {
        infos.get(id).setTitle(newInfo.getTitle());
        infos.get(id).setContent(newInfo.getContent());
        infos.get(id).setLink(newInfo.getLink());
        infos.get(id).setDate(newInfo.getDate());
        infos.get(id).setCategory(newInfo.getCategory());
    }

    public void addCategory(Category newCat) {
        categories.add(newCat);
    }
}
