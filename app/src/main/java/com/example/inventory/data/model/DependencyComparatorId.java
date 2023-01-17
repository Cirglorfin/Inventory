package com.example.inventory.data.model;

import java.util.Comparator;

public class DependencyComparatorId implements Comparator<Dependency> {
    @Override
    public int compare(Dependency d1,Dependency d2) {
        if(d1.getId()< d2.getId())
        {
            return -1;
        }
        else if(d1.getId()== d2.getId())
        {
            return 0;
        }
        else{
            return 1;
        }
    }
}
