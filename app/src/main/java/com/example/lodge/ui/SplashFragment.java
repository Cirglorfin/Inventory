package com.example.lodge.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.lodge.R;
import com.example.lodge.databinding.FragmentSplashBinding;
import com.example.lodge.ui.prefs.UserPrefManager;

public class SplashFragment extends Fragment {
    final int WAIT_TIME=2000;
    private FragmentSplashBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSplashBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Se puede hacer lambda con alt+intro
        new Handler().postDelayed(() ->{
    if(new UserPrefManager(getContext()).isUserLogin()) {
        NavHostFragment.findNavController(SplashFragment.this).navigate(R.id.dashBoardFragment);

        }
    else{
        NavHostFragment.findNavController(SplashFragment.this).navigate(R.id.LoginFragment);
        }
        },WAIT_TIME);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}