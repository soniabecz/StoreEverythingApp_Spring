package com.example.storeeverythingapp_spring.repositories;

import com.example.storeeverythingapp_spring.data.Category;
import com.example.storeeverythingapp_spring.data.Information;
import com.example.storeeverythingapp_spring.data.db.CategoryEntity;
import com.example.storeeverythingapp_spring.data.db.InformationEntity;
import com.example.storeeverythingapp_spring.repositories.db.CategoryEntityRepository;
import com.example.storeeverythingapp_spring.repositories.db.InformationEntityRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
@Getter
@Scope("session")
public class InformationRepository {

    @Autowired
    InformationEntityRepository informationEntityRepository;

    @Autowired
    CategoryEntityRepository categoryEntityRepository;

    List<InformationEntity> infos;
    List<InformationEntity> allInfos = new ArrayList<>();
    List<Category> categories = new ArrayList<>();
    HashMap infosPopularity = new HashMap<>();

    int counter = 0;

    public InformationRepository() {
        infos = informationEntityRepository.findAll();
    }

    public InformationEntity getInfo(int id) {
        return infos.get(id);
    }

    public List<InformationEntity> getAllInfos() {
        infos = allInfos;
        counter = 0;
        return infos;
    }

    public List<InformationEntity> all() {
        return informationEntityRepository.findAll();
    }

    public List<InformationEntity> filterInfos(String choice) {
        if(counter == 0) {
            allInfos = infos;
            counter++;
        }

        List<InformationEntity> filteredInfos = new ArrayList<>();

        if (Objects.equals(choice, "category")) {
            Iterator i = infosPopularity.keySet().iterator();

            while (i.hasNext()) {
                String category = i.next().toString();
                int count = Integer.parseInt(infosPopularity.get(category).toString());

                for (InformationEntity info:infos) {
                    if(info.getCategoryName().getName().equals(category) && count >= 2) {
                        filteredInfos.add(info);
                    }
                }
            }
        } else if(Objects.equals(choice, "date")) {
            Date yesterday = new Date(System.currentTimeMillis()-24*60*60*1000);

            for (InformationEntity info:infos) {
                if(info.getDate().after(yesterday)) {
                    filteredInfos.add(info);
                }
            }
            Collections.sort(filteredInfos, new Comparator<InformationEntity>() {
                @Override
                public int compare(final InformationEntity info1, final InformationEntity info2) {
                    return info1.getDate().compareTo(info2.getDate());
                }
            });
        }

        infos = filteredInfos;
        return filteredInfos;

    }

    public List<InformationEntity> sortByNameASC() {
        if (infos.size() > 0) {
            Collections.sort(infos, new Comparator<InformationEntity>() {
                @Override
                public int compare(InformationEntity info1, InformationEntity info2) {
                    return info1.getTitle().compareTo(info2.getTitle());
                }
            });
        }

        return infos;
    }

    public List<InformationEntity> sortByNameDSC() {
        if (infos.size() > 0) {
            Collections.sort(infos, new Comparator<InformationEntity>() {
                @Override
                public int compare(final InformationEntity info1, final InformationEntity info2) {
                    return info2.getTitle().compareTo(info1.getTitle());
                }
            });
        }

        return infos;
    }

    public List<InformationEntity> sortByDateASC() {
        if (infos.size() > 0) {
            Collections.sort(infos, new Comparator<InformationEntity>() {
                @Override
                public int compare(final InformationEntity info1, final InformationEntity info2) {
                    return info1.getDate().compareTo(info2.getDate());
                }
            });
        }

        return infos;
    }

    public List<InformationEntity> sortByDateDSC() {
        if (infos.size() > 0) {
            Collections.sort(infos, new Comparator<InformationEntity>() {
                @Override
                public int compare(final InformationEntity info1, final InformationEntity info2) {
                    return info2.getDate().compareTo(info1.getDate());
                }
            });
        }

        return infos;
    }

    public List<InformationEntity> sortByCategoryASC() {
        if (infos.size() > 0) {
            Collections.sort(infos, new Comparator<InformationEntity>() {
                @Override
                public int compare(final InformationEntity info1, final InformationEntity info2) {
                    return info1.getCategoryName().getName().compareTo(info2.getCategoryName().getName());
                }
            });
        }

        return infos;
    }

    public List<InformationEntity> sortByCategoryDSC() {
        if (infos.size() > 0) {
            Collections.sort(infos, new Comparator<InformationEntity>() {
                @Override
                public int compare(final InformationEntity info1, final InformationEntity info2) {
                    return info2.getCategoryName().getName().compareTo(info1.getCategoryName().getName());
                }
            });
        }

        return infos;
    }

    public void addInfo(InformationEntity newInfo) {
        infos.add(newInfo);


        int count = Integer.parseInt(infosPopularity.get(newInfo.getCategoryName().getName()).toString());
        infosPopularity.put(newInfo.getCategoryName().getName(), Integer.valueOf(++count));
        infosPopularity = sortByValue(infosPopularity);

    }
    public void removeInfo(int id) {
        infos.remove(id);
    }
    public void editInfo(int id, Information newInfo) {
        String catName = newInfo.getCategory().getName();
        Optional<CategoryEntity> categoryEntity = categoryEntityRepository.findByNameIgnoreCase(catName);
        infos.get(id).setTitle(newInfo.getTitle());
        infos.get(id).setContent(newInfo.getContent());
        infos.get(id).setLink(newInfo.getLink());
        infos.get(id).setDate(new java.sql.Date(newInfo.getDate().getTime()));
        infos.get(id).setCategoryName(categoryEntity.get());
    }

    public void addCategory(Category newCat) {
        categories.add(newCat);

        infosPopularity.put(newCat.getName(), 0);
    }

    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
    {
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
