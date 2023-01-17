package com.example.inventory.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inventory.databinding.FragmentLoginBinding;
import com.example.inventory.databinding.FragmentSingUpBinding;


public class SingUpFragment extends Fragment {
    private FragmentSingUpBinding binding;

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
            binding.floatingActionButton.setOnClickListener(view ->
                    NavHostFragment.findNavController(this).navigateUp());
            return binding.getRoot();
    }
}