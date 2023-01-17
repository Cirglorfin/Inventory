package com.example.inventory.ui.section;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inventory.adapter.SectionAdapter;
import com.example.inventory.data.model.Dependency;
import com.example.inventory.data.model.Section;
import com.example.inventory.databinding.FragmentSectionListBinding;

import java.util.ArrayList;


public class SectionListFragment extends Fragment {
private FragmentSectionListBinding binding;
    private SectionListViewModel viewModel;
    private SectionAdapter adapter;


    public SectionListFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel=new ViewModelProvider(this).get(SectionListViewModel.class);
        viewModel.getData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Section>>() {
            @Override
            public void onChanged(ArrayList<Section> list) {

                adapter.update(list);
            }
        });
        viewModel.getState().observe(getViewLifecycleOwner(),sectionListResut -> {
            switch (sectionListResut)
            {
                case LOADING:
                    break;
                case NODATA:
                    showNoSata();
                    hideRecycler();
                    break;
                case SUCCESS:
                    hideNoData();
                    showRecycler();
                    break;
                case COMPLETE:
                    break;
            }
        });
        viewModel.getList();
        initRVSection(view);
        initFab();

    }

    private void showRecycler() {
        binding.rvDependency.setVisibility(View.VISIBLE);
    }

    private void hideNoData() {
        binding.lnodata.setVisibility(View.INVISIBLE);
    }

    private void hideRecycler() {
        binding.rvDependency.setVisibility(View.INVISIBLE);
    }

    private void showNoSata() {
        binding.lnodata.setVisibility(View.VISIBLE);
    }

    private void initFab() {

    }

    private void initRVSection(View view)
    {
        adapter=new SectionAdapter();
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        binding.rvDependency.setLayoutManager(layoutManager);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSectionListBinding.inflate(inflater, container, false);
        return binding.getRoot();  }
}