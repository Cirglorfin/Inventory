package com.example.lodge.data.repository;

import android.os.Build;

import com.example.lodge.data.model.Server;


import java.util.ArrayList;
import java.util.Comparator;

public class ServerRepository {
    private static ServerRepository instance;
   private ArrayList<Server> list;
    private ServerRepository()
    {
        list=new ArrayList<>();
        Inicialice();
    }

    public static ServerRepository getInstance() {
        if (instance == null) {
            instance = new ServerRepository();
        }
        return instance;
    }

    /**
     * Método que devuelve la lista de redes sociales
     * @return
     */

    private void Inicialice()
    {
        list.add(new Server(1, "Grupo1"));
        list.add(new Server(2, "Grupo2"));
        list.add(new Server(3, "Grupo3"));
    }

    public ArrayList<Server> getList() {
        //Collections.sort(list);

        return list;
    }
    private void putId(Server server) {
        if (server != null) {
            int max=0;
            if (server.getId() == 0) {

                for(int i=0;i<list.size();i++){
                    if(list.get(i)!=null){
                        if(max<=list.get(i).getId())
                        {
                            max=list.get(i).getId()+1;
                        }
                    }
                }
                server.setId(max);
            }

        }
    }
    public boolean add(Server server) {
        if(list.contains(server))
        {
            return false;
        }
        list.add(server);
        putId(server);
        //list.sort(Comparator.comparing(Server::getShortname));
        return true;
    }
    public boolean edit(Server server) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId()==(server.getId())) {
                list.set(i, server);
                return true;
            }
        }

        return false;
    }
    public void ordenar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            list.sort(Comparator.comparing(Server::getName));
        }
    }
    public void delete(Server server) {
        list.remove(server);
    }
}

