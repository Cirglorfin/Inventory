package com.example.inventory.data.repository;

import com.example.inventory.data.model.User;

import java.util.ArrayList;

public class UserRepository {
    private static UserRepository instance;
    private ArrayList<User> list;
    private static UserRepository INSTANCE=null;
    private UserRepository(){
        list=new ArrayList<>();
        Inicialice();

    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public void Inicialice(){

        list.add(new User("abc","abc@gmail.com","Aaaaaaaaa@!4"));
    }


    public boolean login(String email, String contraseña) {
       for(int i=0;i<list.size();i++)
       {

           if(list.get(i).getEmail().equals(email) && list.get(i).getPassword().equals(contraseña))
               return true;
       }
       return false;
    }
}
