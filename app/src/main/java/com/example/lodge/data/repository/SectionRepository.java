package com.example.lodge.data.repository;

import com.example.lodge.data.model.Section;

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


        list.add(new Section("a","a"));



    }

    public ArrayList<Section> getList() {
        return list;
    }
}
