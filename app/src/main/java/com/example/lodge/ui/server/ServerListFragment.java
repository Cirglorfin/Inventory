package com.example.lodge.ui.server;

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

import com.example.lodge.R;
import com.example.lodge.adapter.DependencyAdapter;
import com.example.lodge.data.model.Server;
import com.example.lodge.databinding.FragmentServerListBinding;
import com.example.lodge.ui.base.BaseFragmentDialog;
import com.example.lodge.viewmodel.StateDataList;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


/**
 * A fragment representing a list of Items.
 */
public class ServerListFragment extends Fragment implements DependencyAdapter.OnManageDependencyListener {
    private static final String TAG = "ServerListFragment";
    private FragmentServerListBinding binding;
    private DependencyAdapter adapter;
    private ServerListViewModel viewModel;



    public ServerListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = FragmentServerListBinding.inflate(inflater);
        binding.fab.setOnClickListener(v ->
                NavHostFragment.findNavController(this).navigate(R.id.action_serverListFragment_to_serverManageFragment));
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
        initRecyclerServer();

    }


    private void initRecyclerServer() {
        adapter = new DependencyAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        binding.rvServer.setLayoutManager(layoutManager);
        binding.rvServer.setAdapter(adapter);
    }

    public void initViewModel() {
        viewModel = new ViewModelProvider(this).get(ServerListViewModel.class);
        viewModel.getLiveDataList().observe(getViewLifecycleOwner(), new Observer<StateDataList<ArrayList<Server>>>() {
            @Override
            public void onChanged(StateDataList<ArrayList<Server>> stateDataList) {
                switch (stateDataList.getStatus()) {
                    case LOADING:
                        break;
                    case NODATA:
                        showNoData();
                        hideReciclerServer();
                       stateDataList.complete();
                        break;
                    case SUCCESS:
                        hideNoData();
                        showRecyclerServer();
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
        viewModel.getDeleteServer().observe(getViewLifecycleOwner(),new Observer<Server>(){

            @Override
            public void onChanged(Server deleteServer) {
                if (deleteServer != null) {
                    Snackbar.make(getView(), "Deshacer eliminar " + deleteServer.getName(), Snackbar.LENGTH_SHORT).setAction(R.string.undo, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.undo();
                            adapter.undo(deleteServer);
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

    private void showRecyclerServer() {
        binding.rvServer.setVisibility(View.VISIBLE);
    }

    private void hideNoData() {
        binding.lnodata.setVisibility(View.INVISIBLE);
    }

    private void hideReciclerServer() {
        binding.rvServer.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onEditServer(Server server) {
        Bundle bundle=new Bundle();
        bundle.putParcelable("server", server);
       /* bundle.putString("nombre",server.getName());
        bundle.putString("descripcion",server.getDescription());
        bundle.putString("imagen",server.getImageName());
        bundle.putString("nombreCorto",server.getShortname());*/
        NavHostFragment.findNavController(this).navigate(R.id.action_serverListFragment_to_serverManageFragment,bundle);
        Snackbar.make(getView(), "Has pulsado sobre: " + server.getName(), Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onDeleteServer(Server server) {
        Bundle bundle=new Bundle();
        bundle.putString(BaseFragmentDialog.KEY_TITLE,getString(R.string.title_delete_server));
        bundle.putString(BaseFragmentDialog.KEY_MESSAGE,getString(R.string.message_delete_server, server.getName()));
       // bundle.putString(BaseFragmentDialog.KEY_REQUEST,TAG);
        NavHostFragment.findNavController(this).navigate(R.id.action_serverListFragment_to_baseFragmentDialog,bundle);
        getActivity().getSupportFragmentManager().setFragmentResultListener(BaseFragmentDialog.KEY_REQUEST,this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if(result.getBoolean(BaseFragmentDialog.KEY_BUNDLE))
                {
                    viewModel.delete(server);
                    adapter.delete(server);
                }
            }
        });
    }
}