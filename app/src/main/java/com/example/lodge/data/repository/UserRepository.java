package com.example.lodge.data.repository;

import com.example.lodge.data.model.User;

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

        list.add(new User("abc","abc@gmail.com","Abcd1"));
    }
    public ArrayList<User> getList() {
        //Collections.sort(list);

        return list;
    }

    public boolean login(String email, String contraseña) {
       for(int i=0;i<list.size();i++)
       {

           if(list.get(i).getEmail().equals(email) && list.get(i).getPassword().equals(contraseña))
               return true;
       }
       return false;
    }
    public boolean singUp(User user) {
        for(int i=0; i<list.size();i++){
            if(list.get(i).getEmail().equals(user.getEmail())){
                return false;
            }
        }
        list.add(user);
        //list.sort(Comparator.comparing(Server::getShortname));
        return true;
    }
}
