package com.example.inventory.ui.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import com.example.inventory.databinding.FragmentLoginBinding;
import com.example.inventory.R;
import com.example.inventory.ui.base.BaseFragment;
import com.example.inventory.ui.login.LoginResult;
import com.example.inventory.ui.login.LoginViewModel;
import com.example.inventory.ui.prefs.UserPrefManager;

public class LoginFragment extends BaseFragment {

    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;
    private LoginTextWatcher loginTextWatcher;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
            return binding.getRoot();

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel=new ViewModelProvider(this).get(LoginViewModel.class);
        //IMPORTANTE ESTAS DOS LiNEAS
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);
        viewModel.getResult().observe(getViewLifecycleOwner(), new Observer<LoginResult>() {
            @Override
            public void onChanged(LoginResult value) {
                switch (value)
                {
                    case EMAILEMPTY:
                        setEmailErrorEmpty();//Método que mostrará el error en TextInputLayout
                        break;
                    case EMAILFORMAT:
                        setEmailErrorFormat();//Método que mostrará el error en TextInputLayout
                        break;
                    case PASSEMPTY:
                        setPasswordErrorEmpty();//Método que mostrará el error en TextInputLayout
                        break;
                    case PASSWORDFORMAT:
                        setPasswordErrorFormat();
                        break;
                    case FAILURE:
                        showError(getString(R.string.errLogin));
                        break;
                    case SUCCESS:
                        saveUserPref();
                        showDashboard();
                        break;
                }

            }


        });
        binding.btnRegistrarse.setOnClickListener(v ->
                NavHostFragment.findNavController(this).navigate(R.id.action_LoginFragment_to_singUpFragment));
//Se inicializa el objeto TextWatcher

        //Se establece el objeto loginTextWatcher a las vistas
        binding.tieEmail.addTextChangedListener(new LoginTextWatcher(binding.tieEmail));
        binding.tiepass.addTextChangedListener(new LoginTextWatcher(binding.tiepass));

    }

    /**
     * Comprueba que el usuario ha seleccionado el checkBox Recuérdame y se guarda la informacion de usuario.
     */
    private void saveUserPref() {
       if( binding.check.isChecked()){
           new UserPrefManager(getContext()).login(binding.tieEmail.getText().toString());
       }
    }
    private void showDashboard() {

                NavHostFragment.findNavController(this).navigate(R.id.dashBoardFragment);


    }
    //Gestionan errores edittext
    private void clearPasswordError() {
        if(binding.tilpass!=null){
        binding.tilpass.setError(null);}
    }

    private void clearEmailError() {
        binding.tilEmail.setError(null);
    }
    /**
     * Establecer el error en el TextInputLayout Email
     */
    private void setEmailErrorEmpty() {
        binding.tilEmail.setError("El email no puede estar vacío");
        binding.tilEmail.requestFocus();
    }
    private void setEmailErrorFormat() {
        binding.tilEmail.setError("Error de formato del email");
        binding.tilEmail.requestFocus();
    }
    private void setPasswordErrorEmpty() {
        binding.tilpass.setError("La contraseña es obligatoria");
        binding.tilpass.requestFocus();
    }
    private void setPasswordErrorFormat() {
        binding.tilpass.setError("La contraseña no cumple el formato");
        binding.tilpass.requestFocus();
    }




    /**
     * Esta clase escucha los cambios de un componente texto
     * Se añade el contructor con un TextView para saber qué
     * vista ha cambiado
     */
    class LoginTextWatcher implements TextWatcher{
        private TextView textView;
        private LoginTextWatcher(TextView textView){
            this.textView=textView;
        }

        public LoginTextWatcher() {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (textView.getId())
            {
                case R.id.tieEmail:
                    //Ventajas de una clase interna, es que accede a los métodos
                    //de la clase que la contiene
                    clearEmailError();

                    break;
                case R.id.tiepass:
                    clearPasswordError();
                    break;

            }

        }
    }



}