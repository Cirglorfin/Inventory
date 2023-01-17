package com.example.inventory.ui.inventory;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.inventory.R;
import com.example.inventory.databinding.FragmentSettingsInventoryBinding;
import com.example.inventory.ui.base.BaseDatePickerDialog;


public class SettingInventoryFragment extends Fragment {
private FragmentSettingsInventoryBinding binding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.imgCreacion.setOnClickListener(v -> {
            showDatePickerDialog();
        });
    }
private void showDatePickerDialog(){
    BaseDatePickerDialog newFragment=BaseDatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            final String selectDate=day+"/"+(month+1)+"/"+year;
            binding.edCreacion.setText(selectDate);
        }
    });
    newFragment.show(getActivity().getSupportFragmentManager(),"datepicker");
}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentSettingsInventoryBinding.inflate(inflater);
        return binding.getRoot();
    }
}