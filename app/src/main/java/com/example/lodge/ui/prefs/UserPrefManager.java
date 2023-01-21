package com.example.lodge.ui.prefs;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Esta clase se encargará de leer/escribir cualquier información del usuario como preferencias del sistema.
 * Cualquier Activity/Fragment que permite información del usuario utilizará una instancia de esta clase.
 */
public class UserPrefManager {
    private static final String KEY_EMAIL ="email" ;
    private static final String KEY_PASSWORD ="password" ;
    private Context context;
    private final String PREFS_NAME="userpref";
    public UserPrefManager (Context context){
        this.context=context;
    }
    public void login(String email){
        SharedPreferences sharedPreferences=context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putString(KEY_EMAIL,email);
        editor.commit();
    }
    public boolean isUserLogin()
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        return !sharedPreferences.getString(KEY_EMAIL,"").isEmpty();
    }
    public void logout(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove(KEY_EMAIL);
        editor.commit();
    }
    public String getUserEmail(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL,"");
    }
}
