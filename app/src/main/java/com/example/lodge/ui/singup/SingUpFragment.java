package com.example.lodge.ui.singup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lodge.R;
import com.example.lodge.databinding.FragmentSingUpBinding;
import com.example.lodge.ui.base.BaseFragment;



public class SingUpFragment extends BaseFragment {
    private FragmentSingUpBinding binding;
    private SingUpViewModel viewModel;

    public SingUpFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            binding = FragmentSingUpBinding.inflate(inflater, container, false);
             return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel=new ViewModelProvider(this).get(SingUpViewModel.class);
        //IMPORTANTE ESTAS DOS LiNEAS
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);
        viewModel.getResult().observe(getViewLifecycleOwner(), new Observer<SingUpResult>() {
            @Override
            public void onChanged(SingUpResult value) {
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
                        showError(getString(R.string.errSingUp));
                        break;
                    case SUCCESS:
                        showSplash();
                        break;
                    case WRONGPASSWORD:
                        setPasswordErrorWrong();
                        break;
                    case NAMEMPTY:
                        setErrorNameEmpty();
                        break;
                }

            }


        });

//Se inicializa el objeto TextWatcher


        binding.tieCorreo.addTextChangedListener(new SingUpFragment.SingUpTextWatcher(binding.tieCorreo));
        binding.tieContraseA1.addTextChangedListener(new SingUpFragment.SingUpTextWatcher(binding.tieContraseA1));
        binding.tieContraseA2.addTextChangedListener(new SingUpFragment.SingUpTextWatcher(binding.tieContraseA2));

    }




    private void showSplash() {
                NavHostFragment.findNavController(this).navigate(R.id.LoginFragment);
    }


    private void clearPasswordError() {
        if(binding.tiP!=null){
            binding.tiP.setError(null);}
    }

    private void clearEmailError() {
        binding.tiC.setError(null);
    }
    /**
     * Establecer el error en el TextInputLayout Email
     */
    private void setPasswordErrorWrong() {
        binding.tiConfirP.setError("Las contraseñas no coinciden");
        binding.tiConfirP.requestFocus();
    }

    private void setEmailErrorEmpty() {
        binding.tiC.setError("El email no puede estar vacío");
        binding.tiC.requestFocus();
    }
    private void setEmailErrorFormat() {
        binding.tiC.setError("Error de formato del email");
        binding.tiC.requestFocus();
    }
    private void setPasswordErrorEmpty() {
        binding.tiP.setError("La contraseña es obligatoria");
        binding.tiP.requestFocus();
    }
    private void setPasswordErrorFormat() {
        binding.tiP.setError("La contraseña no cumple el formato");
        binding.tiP.requestFocus();
    }
    private void setErrorNameEmpty() {
        binding.tiU.setError("Nombre vacío");
        binding.tiU.requestFocus();
    }



    /**
     * Esta clase escucha los cambios de un componente texto
     * Se añade el contructor con un TextView para saber qué
     * vista ha cambiado
     */
    class SingUpTextWatcher implements TextWatcher {
        private TextView textView;
        private SingUpTextWatcher(TextView textView){
            this.textView=textView;
        }

        public SingUpTextWatcher() {

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
                case R.id.tieCorreo:
                    //Ventajas de una clase interna, es que accede a los métodos
                    //de la clase que la contiene
                    clearEmailError();

                    break;
                case R.id.tieContraseña1:
                    clearPasswordError();
                    break;
                case R.id.tieContraseña2:
                    clearPasswordError2();
                    break;

            }

        }
    }

    private void clearPasswordError2() {
        if(binding.tiConfirP!=null){
            binding.tiConfirP.setError(null);}
    }
}