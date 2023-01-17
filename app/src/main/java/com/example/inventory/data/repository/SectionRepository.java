package com.example.inventory.data.repository;

import com.example.inventory.R;
import com.example.inventory.data.model.Dependency;
import com.example.inventory.data.model.Section;

import java.util.ArrayList;

public class SectionRepository {
    private static SectionRepository instance;
    private ArrayList<Section> list;
    /*
    Solo se puede tener una única instancia del REPOSITORY
     */
    private SectionRepository()
    {
        list=new ArrayList<>();
        Inicialice();
    }

    public static SectionRepository getInstance() {
        if (instance == null) {
            instance = new SectionRepository();
        }
        return instance;
    }


    private void Inicialice()
    {


        list.add(new Section("a","a",new Dependency("abc","abcd")));



    }

    public ArrayList<Section> getList() {
        return list;
    }
}
