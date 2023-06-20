package com.example.storeeverythingapp_spring.services;

import com.example.storeeverythingapp_spring.data.Category;
import com.example.storeeverythingapp_spring.data.Information;
import com.example.storeeverythingapp_spring.data.db.AuthoritiesEntity;
import com.example.storeeverythingapp_spring.data.db.CategoryEntity;
import com.example.storeeverythingapp_spring.data.db.InformationEntity;
import com.example.storeeverythingapp_spring.data.db.UserEntity;
import com.example.storeeverythingapp_spring.repositories.db.AuthoritiesEntityRepository;
import com.example.storeeverythingapp_spring.repositories.db.CategoryEntityRepository;
import com.example.storeeverythingapp_spring.repositories.db.InformationEntityRepository;
import com.example.storeeverythingapp_spring.repositories.db.UserEntityRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Getter
@NoArgsConstructor
@Scope("session")
public class AppService {
    @Autowired
    InformationEntityRepository informationEntityRepository;
    @Autowired
    CategoryEntityRepository categoryEntityRepository;

    @Autowired
    UserEntityRepository userEntityRepository;

    @Autowired
    AuthoritiesEntityRepository authoritiesEntityRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    List<InformationEntity> infos = new ArrayList<InformationEntity>();

    List<InformationEntity> newInfos = new ArrayList<InformationEntity>();
    List<InformationEntity> infosDB = new ArrayList<InformationEntity>();
    List<InformationEntity> allInfos = new ArrayList<InformationEntity>();
    List<CategoryEntity> categories = new ArrayList<CategoryEntity>();
    List<CategoryEntity> newCategories = new ArrayList<CategoryEntity>();

    HashMap<String, Integer> infosPopularity = new HashMap<>();

    int dbSize = 0;


    public List<InformationEntity> getInfos() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        infos = informationEntityRepository.findAllByUsername(currentUserName);

        dbSize = infos.size();

        for (InformationEntity info : infos) {
            int count = Integer.parseInt(infosPopularity.get(info.getCategoryName().getName()).toString());
            infosPopularity.put(info.getCategoryName().getName(), Integer.valueOf(++count));
            infosPopularity = sortByValue(infosPopularity);
        }

        if (newInfos.size() != 0) {
            infos.addAll(newInfos);
        }

        return infos;
    }

    public Information getInfo(int id) {
        Information information = new Information();

        String catName = infos.get(id).getCategoryName().getName();
        System.out.println(catName);

        System.out.println("id:" + infos.get(id).getId());

        information.setDatabaseId(infos.get(id).getId());
        information.setTitle(infos.get(id).getTitle());
        information.setContent(infos.get(id).getContent());
        information.setDate(infos.get(id).getDate());
        information.setLink(infos.get(id).getLink());
        information.setCategory(new Category(catName));

        System.out.println(information);

        return information;
    }

    public List<InformationEntity> getAllInfos() {
        getInfos();
        return infos;
    }

    public List<InformationEntity> getSharedInfos() {
        getInfos();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        infos = informationEntityRepository.findAllBySharedTo(currentUserName);
        return infos;
    }


    public List<InformationEntity> filterInfos(String choice) {

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
            filteredInfos.sort(new Comparator<InformationEntity>() {
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

        if (infos.size() == 0) {
            getInfos();
        }

        if (infos.size() > 0) {
            infos.sort(new Comparator<InformationEntity>() {
                @Override
                public int compare(final InformationEntity info1, final InformationEntity info2) {
                    return info1.getTitle().compareTo(info2.getTitle());
                }
            });
            System.out.println(infos);
        }

        return infos;
    }

    public List<InformationEntity> sortByNameDSC() {

        if (infos.size() == 0) {
            getInfos();
        }

        if (infos.size() > 0) {
            infos.sort(new Comparator<InformationEntity>() {
                @Override
                public int compare(final InformationEntity info1, final InformationEntity info2) {
                    return info2.getTitle().compareTo(info1.getTitle());
                }
            });
        }

        return infos;
    }

    public List<InformationEntity> sortByDateASC() {

        if (infos.size() == 0) {
            getInfos();
        }

        if (infos.size() > 0) {
            infos.sort(new Comparator<InformationEntity>() {
                @Override
                public int compare(final InformationEntity info1, final InformationEntity info2) {
                    return info1.getDate().compareTo(info2.getDate());
                }
            });
        }

        return infos;
    }

    public List<InformationEntity> sortByDateDSC() {

        if (infos.size() == 0) {
            getInfos();
        }

        if (infos.size() > 0) {
            infos.sort(new Comparator<InformationEntity>() {
                @Override
                public int compare(final InformationEntity info1, final InformationEntity info2) {
                    return info2.getDate().compareTo(info1.getDate());
                }
            });
        }

        return infos;
    }

    public List<InformationEntity> sortByCategoryASC() {

        if (infos.size() == 0) {
            getInfos();
        }

        if (infos.size() > 0) {
            infos.sort(new Comparator<InformationEntity>() {
                @Override
                public int compare(final InformationEntity info1, final InformationEntity info2) {
                    return info1.getCategoryName().getName().compareTo(info2.getCategoryName().getName());
                }
            });
        }

        return infos;
    }

    public List<InformationEntity> sortByCategoryDSC() {

        if (infos.size() == 0) {
            getInfos();
        }


        if (infos.size() > 0) {
            infos.sort(new Comparator<InformationEntity>() {
                @Override
                public int compare(final InformationEntity info1, final InformationEntity info2) {
                    return info2.getCategoryName().getName().compareTo(info1.getCategoryName().getName());
                }
            });
        }

        return infos;
    }

    public void addInfo(Information newInfo) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        InformationEntity newInformationEntity = new InformationEntity();
        String catName = newInfo.getCategory().getName();
        Optional<CategoryEntity> categoryEntity = categoryEntityRepository.findByNameIgnoreCase(catName);
        newInformationEntity.setTitle(newInfo.getTitle());
        newInformationEntity.setContent(newInfo.getContent());
        newInformationEntity.setDate(new java.sql.Date(newInfo.getDate().getTime()));
        newInformationEntity.setLink(newInfo.getLink());
        newInformationEntity.setUsername(currentUserName);
        newInformationEntity.setCategoryName(categoryEntity.get());

        infos.add(newInformationEntity);
        newInfos.add(newInformationEntity);

        int count = Integer.parseInt(infosPopularity.get(newInfo.getCategory().getName()).toString());
        infosPopularity.put(newInfo.getCategory().getName(), Integer.valueOf(++count));
        infosPopularity = sortByValue(infosPopularity);

    }
    public void removeInfo(int id, Information newInfo) {
        if (informationEntityRepository.findById(newInfo.getDatabaseId()).isPresent()) {
            informationEntityRepository.deleteById(newInfo.getDatabaseId());
        } else {
            System.out.println("to: " + infos.get(id));
            newInfos.removeIf(info -> info.equals(infos.get(id)));
            infos.remove(id);
        }

    }
    public void editInfo(int id, Information newInfo) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        String catName = newInfo.getCategory().getName();
        Optional<CategoryEntity> categoryEntity = categoryEntityRepository.findByNameIgnoreCase(catName);

        if (informationEntityRepository.findById(newInfo.getDatabaseId()).isPresent()) {
            InformationEntity information = informationEntityRepository.findById(newInfo.getDatabaseId()).get();
            information.setTitle(newInfo.getTitle());
            information.setContent(newInfo.getContent());
            information.setLink(newInfo.getLink());
            information.setDate(new java.sql.Date(newInfo.getDate().getTime()));
            information.setUsername(currentUserName);
            information.setCategoryName(categoryEntity.get());
            informationEntityRepository.save(information);
            System.out.println(information);
        } else {

            for (InformationEntity info : newInfos) {
                if (info.equals(infos.get(id))) {
                    info.setTitle(newInfo.getTitle());
                    info.setContent(newInfo.getContent());
                    info.setLink(newInfo.getLink());
                    info.setDate(new java.sql.Date(newInfo.getDate().getTime()));
                    info.setCategoryName(categoryEntity.get());
                }
            }

            infos.get(id).setTitle(newInfo.getTitle());
            infos.get(id).setContent(newInfo.getContent());
            infos.get(id).setLink(newInfo.getLink());
            infos.get(id).setDate(new java.sql.Date(newInfo.getDate().getTime()));
            infos.get(id).setCategoryName(categoryEntity.get());

        }
    }

    public void shareInfo(int id, Information newInfo, String username) {
        if (informationEntityRepository.findById(newInfo.getDatabaseId()).isPresent()) {
            InformationEntity information = informationEntityRepository.findById(newInfo.getDatabaseId()).get();
            information.setSharedTo(username);
            informationEntityRepository.save(information);
            System.out.println(information);
        } else {

            for (InformationEntity info : newInfos) {
                if (info.equals(infos.get(id))) {
                    info.setSharedTo(username);
                }
            }

            infos.get(id).setSharedTo(username);

        }
    }

    public List<CategoryEntity> getCategories() {

        if (categories.size() == 0) {

            categories = categoryEntityRepository.findAll();

            if (newCategories.size() != 0) {
                categories.addAll(newCategories);
            }

            for (CategoryEntity cat : categories) {
                infosPopularity.put(cat.getName(), 0);
            }
        }

        return categories;
    }

    public void addCategory(Category newCat) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(newCat.getName());
        categories.add(categoryEntity);
        newCategories.add(categoryEntity);

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

    public void addUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntityRepository.save(user);
        AuthoritiesEntity authority = new AuthoritiesEntity();
        authority.setUsername(user.getUsername());
        authority.setAuthority("FULL_USER");
        authoritiesEntityRepository.save(authority);
    }
}
