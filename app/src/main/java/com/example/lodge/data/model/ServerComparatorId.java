package com.example.lodge.data.model;

import java.util.Comparator;

public class ServerComparatorId implements Comparator<Server> {
    @Override
    public int compare(Server d1, Server d2) {
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
