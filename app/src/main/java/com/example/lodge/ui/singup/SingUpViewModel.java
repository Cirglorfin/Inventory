package com.example.lodge.ui.singup;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lodge.data.model.User;
import com.example.lodge.data.repository.UserRepository;
import com.example.lodge.utils.CommonUtils;



public class SingUpViewModel extends ViewModel {
    public MutableLiveData<String> email=new MutableLiveData<>();
    public MutableLiveData<String> name=new MutableLiveData<>();
    public MutableLiveData<String> password=new MutableLiveData<>();
    public MutableLiveData<String> password2=new MutableLiveData<>();
    //Los siguientes LiveData tendrán observadores en la Activity/Fragment y
    //se debe implementar un método de obtención
    private MutableLiveData<SingUpResult> result;
    private MutableLiveData<Enum> errorEmailFormat=new MutableLiveData<>();

    public void validateCredentials(){

        if (TextUtils.isEmpty(email.getValue()))
        {
            //Se tiene que crear un LIVE DATA que englobe todas las posibles alternativas
            result.setValue(SingUpResult.EMAILEMPTY);
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email.getValue()).matches())
        {
            result.setValue(SingUpResult.EMAILFORMAT);

        }else if (TextUtils.isEmpty(name.getValue()))
        {
           result.setValue(SingUpResult.NAMEMPTY);
        }
        else if(TextUtils.isEmpty(password.getValue()))
        {
            result.setValue(SingUpResult.PASSEMPTY);
        }
        else if(!CommonUtils.isPasswordValid(password.getValue()))
        {
            result.setValue(SingUpResult.PASSWORDFORMAT);
        }
        else if(!password.getValue().equals(password2.getValue()))
        {
            result.setValue(SingUpResult.WRONGPASSWORD);
        }
        //Se llama al método Login del repositorio
        else
            singUp();


    }

    private void singUp() {
        if(UserRepository.getInstance().singUp(new User(name.getValue(),email.getValue(),password.getValue())))
        {
            result.setValue(SingUpResult.SUCCESS);
        }
        else
        {
            result.setValue(SingUpResult.FAILURE);
        }
    }

    public MutableLiveData<SingUpResult> getResult()
    {
        if(result==null)
        {result=new MutableLiveData<>();

        }
        return result;
    }

   /* @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SingUpViewModel)) return false;
        SingUpViewModel that = (SingUpViewModel) o;
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

    public SingUpViewModel(String usuario, String contraseña, String correo) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.correo = correo;
    }

    String contraseña;
    String correo;*/

}
