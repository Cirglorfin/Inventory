package com.example.inventory.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inventory.R;
import com.example.inventory.databinding.FragmentDashboardBinding;



public class DashboardFragment extends Fragment {
FragmentDashboardBinding binding;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
          return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.imgInventory.setOnClickListener(v ->NavHostFragment.findNavController(this).navigate(R.id.action_dashBoardFragment_to_addInventoryFragment));
        binding.imgDependency.setOnClickListener(v ->
                NavHostFragment.findNavController(this).navigate(R.id.dependencyListFragment));
        binding.imgPedidos.setOnClickListener(v ->NavHostFragment.findNavController(this).navigate(R.id.sectionListFragment));

    }
}