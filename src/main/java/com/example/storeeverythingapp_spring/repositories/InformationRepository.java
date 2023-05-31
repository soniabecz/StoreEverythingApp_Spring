package com.example.storeeverythingapp_spring.repositories;

import com.example.storeeverythingapp_spring.data.Category;
import com.example.storeeverythingapp_spring.data.Information;
import com.example.storeeverythingapp_spring.validators.category.CategoryValidation;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Getter
public class InformationRepository {
    List<Information> infos = new ArrayList<>();
    List<Category> categories = new ArrayList<>();
    HashMap infosPopularity = new HashMap<>();

    public InformationRepository() {

    }

    public Information getInfo(int id) {
        return infos.get(id);
    }

    public List<Information> filterInfos(String choice) {
        List<Information> filteredInfos = new ArrayList<>();

        if (Objects.equals(choice, "category")) {
            Iterator i = infosPopularity.keySet().iterator();

            while (i.hasNext()) {
                String category = i.next().toString();
                int count = Integer.parseInt(infosPopularity.get(category).toString());

                for (Information info:infos) {
                    if(info.getCategory().getName().equals(category) && count >= 2) {
                        filteredInfos.add(info);
                    }
                }
            }
        } else if(Objects.equals(choice, "date")) {
            Date yesterday = new Date(System.currentTimeMillis()-24*60*60*1000);

            for (Information info:infos) {
                if(info.getDate().after(yesterday)) {
                    filteredInfos.add(info);
                }
            }
            Collections.sort(filteredInfos, new Comparator<Information>() {
                @Override
                public int compare(final Information info1, final Information info2) {
                    return info1.getDate().compareTo(info2.getDate());
                }
            });
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

        int count = Integer.parseInt(infosPopularity.get(newInfo.getCategory().getName()).toString());
        infosPopularity.put(newInfo.getCategory().getName(), Integer.valueOf(++count));
        infosPopularity = sortByValue(infosPopularity);

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
