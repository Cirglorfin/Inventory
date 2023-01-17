package com.example.inventory.ui.dependency;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.inventory.R;
import com.example.inventory.adapter.DependencyAdapter;
import com.example.inventory.data.model.Dependency;
import com.example.inventory.databinding.FragmentDependencyListBinding;
import com.example.inventory.ui.base.BaseFragmentDialog;
import com.example.inventory.viewmodel.StateDataList;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


/**
 * A fragment representing a list of Items.
 */
public class DependencyListFragment extends Fragment implements DependencyAdapter.OnManageDependencyListener {
    private static final String TAG = "DependencyListFragment";
    private FragmentDependencyListBinding binding;
    private DependencyAdapter adapter;
    private DependencyListViewModel viewModel;



    public DependencyListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = FragmentDependencyListBinding.inflate(inflater);
        binding.fab.setOnClickListener(v ->
                NavHostFragment.findNavController(this).navigate(R.id.action_dependencyListFragment_to_dependencyAddEditFragment));
        return binding.getRoot();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.menu_server_list,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

      switch (item.getItemId()){
          case R.id.action_server_orderById:
              viewModel.orderById();
              return true;
      }
        return false;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initRecyclerDependency();

    }

    //Inicicializa el componente reciclerview
    private void initRecyclerDependency() {
        //1. Se debe inicializar el Adapter del RecyclerView
        adapter = new DependencyAdapter(this);
        //2. Se debe asimilar el diseño (layout) que tendrá el componente recyclerview+
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        //3. Se asigna el Adapter al RecyclerView
        binding.rvDependency.setLayoutManager(layoutManager);
        //4. Asignar el adapter al recyclerview
        binding.rvDependency.setAdapter(adapter);
        //5.

    }

    /**
     * Este método inicializa el componente ViewModel de esta vista
     */
    public void initViewModel() {
        //1. Instanciar la clase ViewModel a través de su Provider
        viewModel = new ViewModelProvider(this).get(DependencyListViewModel.class);
        //2. Crear el observador del objeto MutableLiveData
        viewModel.getLiveDataList().observe(getViewLifecycleOwner(), new Observer<StateDataList<ArrayList<Dependency>>>() {
            @Override
            public void onChanged(StateDataList<ArrayList<Dependency>> stateDataList) {
                switch (stateDataList.getStatus()) {
                    case LOADING:
                        //Se mostraría un progressbar
                        break;
                    case NODATA:
                        //hide
                        showNoData();
                        hideReciclerDependency();
                       stateDataList.complete();
                        break;
                    case SUCCESS:
                        hideNoData();
                        showRecyclerDependency();
                        adapter.update(stateDataList.getData());
                        stateDataList.complete();
                        break;
                    case COMPLETE:
                        //Se ocultaría el progressbar al usuario
                        break;
                    case ORDERBYID:
                        adapter.orderBy(stateDataList.getData());
                        break;
                }

            }
        });
        viewModel.getDeleteDependency().observe(getViewLifecycleOwner(),new Observer<Dependency>(){

            @Override
            public void onChanged(Dependency deleteDependency) {
                if (deleteDependency != null) {
                    Snackbar.make(getView(), "Deshacer eliminar " + deleteDependency.getShortname(), Snackbar.LENGTH_SHORT).setAction(R.string.undo, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.undo();
                            adapter.undo(deleteDependency);
                        }
                    }).show();
                }
            }
        });
        //Consultar o pedir los datos al viewModel, que a s
        viewModel.getDataList();
    }

    private void showNoData() {
        binding.lnodata.setVisibility(View.VISIBLE);
    }

    private void showRecyclerDependency() {
        binding.rvDependency.setVisibility(View.VISIBLE);
    }

    private void hideNoData() {
        binding.lnodata.setVisibility(View.INVISIBLE);
    }

    private void hideReciclerDependency() {
        binding.rvDependency.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onEditDependency(Dependency dependency) {
        Bundle bundle=new Bundle();
        bundle.putParcelable("dependency",dependency);
       /* bundle.putString("nombre",dependency.getName());
        bundle.putString("descripcion",dependency.getDescription());
        bundle.putString("imagen",dependency.getImageName());
        bundle.putString("nombreCorto",dependency.getShortname());*/
        NavHostFragment.findNavController(this).navigate(R.id.action_dependencyListFragment_to_dependencyAddEditFragment,bundle);
        Snackbar.make(getView(), "Has pulsado sobre: " + dependency.getName(), Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onDeleteDependency(Dependency dependency) {
        Bundle bundle=new Bundle();
        bundle.putString(BaseFragmentDialog.KEY_TITLE,getString(R.string.title_delete_dependency));
        bundle.putString(BaseFragmentDialog.KEY_MESSAGE,getString(R.string.message_delete,dependency.getShortname()));
       // bundle.putString(BaseFragmentDialog.KEY_REQUEST,TAG);
        NavHostFragment.findNavController(this).navigate(R.id.action_dependencyListFragment_to_baseFragmentDialog,bundle);
        getActivity().getSupportFragmentManager().setFragmentResultListener(BaseFragmentDialog.KEY_REQUEST,this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if(result.getBoolean(BaseFragmentDialog.KEY_BUNDLE))
                {
                    viewModel.delete(dependency);
                    adapter.delete(dependency);
                }
            }
        });
    }
}