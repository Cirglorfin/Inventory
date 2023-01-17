package com.example.inventory.ui.login;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inventory.data.repository.UserRepository;
import com.example.inventory.ui.login.LoginResult;
import com.example.inventory.utils.CommonUtils;


/**
 * Esta clase contiene:
 * 1) Los métodos que garantizan que se cumple las reglas de negocio
 * 2) Los datos de la vista que quiera que se actualicen automáticamente y se mantengan
 * durante el ciclo de vida de la activity/fragment(UI Controller)
 */
public class LoginViewModel extends ViewModel {
    public MutableLiveData<String> email=new MutableLiveData<>();
    public MutableLiveData<String> password=new MutableLiveData<>();
    //Los siguientes LiveData tendrán observadores en la Activity/Fragment y
    //se debe implementar un método de obtención
    private MutableLiveData<LoginResult> result;
    private MutableLiveData<Enum> errorEmailFormat=new MutableLiveData<>();
    /**
     * Este método es el encargado de comprobar que todas las reglas de negocio se cumplen.
     * Si es correcto->consulta al repositorio.
     */
    public void validateCredentials(){

        if (TextUtils.isEmpty(email.getValue()))
        {
            //Se tiene que crear un LIVE DATA que englobe todas las posibles alternativas
            result.setValue(LoginResult.EMAILEMPTY);
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email.getValue()).matches())
        {
            result.setValue(LoginResult.EMAILFORMAT);

        }
        else if(TextUtils.isEmpty(password.getValue()))
        {
            result.setValue(LoginResult.PASSEMPTY);
        }
        else if(!CommonUtils.isPasswordValid(password.getValue()))
        {
            result.setValue(LoginResult.PASSWORDFORMAT);
        }
        //Se llama al método Login del repositorio
        else
            login();


    }

    private void login() {
        if(UserRepository.getInstance().login(email.getValue(),password.getValue()))
        {
            result.setValue(LoginResult.SUCCESS);
        }
        else
        {
            result.setValue(LoginResult.FAILURE);
        }
    }

    public MutableLiveData<LoginResult> getResult()
    {
        if(result==null)
        {result=new MutableLiveData<>();

        }
        return result;
    }

   /* @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginViewModel)) return false;
        LoginViewModel that = (LoginViewModel) o;
        return Objects.equals(getUsuario(), that.getUsuario()) && Objects.equals(getCorreo(), that.getCorreo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsuario(), getCorreo());
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LoginViewModel(String usuario, String contraseña, String correo) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.correo = correo;
    }

    String contraseña;
    String correo;*/

}
