package com.example.inventory.data.repository;

import static java.util.Collections.unmodifiableList;

import com.example.inventory.R;
import com.example.inventory.data.model.Dependency;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class DependencyRepository {
    private static DependencyRepository instance;
   private ArrayList<Dependency> list;
    /*
    Solo se puede tener una única instancia del REPOSITORY
     */
    private DependencyRepository()
    {
        list=new ArrayList<>();
        Inicialice();
    }

    public static DependencyRepository getInstance() {
        if (instance == null) {
            instance = new DependencyRepository();
        }
        return instance;
    }

    /**
     * Método que devuelve la lista de redes sociales
     * @return
     */

    private void Inicialice()
    {

list.add(new Dependency("Git","Github"));
       //list.add(new Dependency(R.drawable.img,"Github"));
       list.add(new Dependency("Ins","Instagram"));
       list.add(new Dependency("Twi","Twitter"));
     list.add(new Dependency("Fac","Facebook"));
        list.add(new Dependency("Lin","Linkedin"));
       // list.sort(Comparator.comparing(Dependency::getShortname));
    }

    public ArrayList<Dependency> getList() {
        //Collections.sort(list);

        return list;
    }

    public boolean add(Dependency dependency) {
        if(list.contains(dependency))
        {
            return false;
        }
        list.add(dependency);
        //list.sort(Comparator.comparing(Dependency::getShortname));
        return true;
    }
    public boolean edit(Dependency dependency) {
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).getShortname()==dependency.getShortname())
            {
                list.set(i,dependency);
                return true;
            }
        }

        return false;


    }
    public void ordenar(){

        list.sort(Comparator.comparing(Dependency::getShortname));
    }
    public void delete(Dependency dependency) {

        list.remove(dependency);

    }
}
