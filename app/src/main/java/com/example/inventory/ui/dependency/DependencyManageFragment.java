package com.example.inventory.ui.dependency;

import static android.content.Context.NOTIFICATION_SERVICE;


import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inventory.R;
import com.example.inventory.data.model.Dependency;
import com.example.inventory.databinding.FragmentDependencyEditBinding;
import com.example.inventory.ui.InventoryApplication;
import com.example.inventory.ui.base.BaseFragment;

import java.io.Serializable;
import java.util.Random;


public class DependencyManageFragment extends BaseFragment {
    private static final int NOTIFICATION_ID = 1234;
    FragmentDependencyEditBinding binding;
    private DependencyManageViewModel viewModel;
    private Dependency dependency;

    public DependencyManageFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDependencyEditBinding.inflate(inflater, container, false);

        initLayout();
        intViewModel();

        return binding.getRoot();
    }

    private void initLayout() {
        if (getArguments() != null) {
            binding.btn.setText("Editar");
            binding.titulo.setText("Editar dependencia");
            dependency=getArguments().getParcelable("dependency");
            binding.nombreCorto.setText(dependency.getShortname());
            binding.nombreCorto.setEnabled(false);
            binding.Descripcion.setText(dependency.getDescription());
            binding.imagen.setText(dependency.getImageName());
            binding.nombre.setText(dependency.getName());
            binding.btn.setOnClickListener(v -> {
                dependency = new Dependency(dependency.getShortname(), binding.nombre.getText().toString(), binding.Descripcion.getText().toString(), binding.imagen.getText().toString());
                viewModel.edit(dependency);
            });
        } else {
            binding.nombreCorto.setEnabled(true);
            binding.btn.setOnClickListener(v -> {
                dependency=new Dependency(binding.nombreCorto.getText().toString(), binding.nombre.getText().toString(), binding.Descripcion.getText().toString(), binding.imagen.getText().toString());
                viewModel.add(dependency);
            });
        }
    }

    private void showAddNotification(Dependency dependency) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //1. Crear un Bundle con la dependencia añadida
            Bundle bundle=new Bundle();
            bundle.putParcelable("depedency",dependency);
            bundle.putString("nombre",dependency.getName());
            bundle.putString("descripcion",dependency.getDescription());
            bundle.putString("imagen",dependency.getImageName());
            bundle.putString("nombreCorto",dependency.getShortname());
            //2. Ahí se trabaja con Activity y FragmentManager
              // Intent intent=new Intent(getActivity(),SplashActivity.class);
             //intent.putExtras(intent);
            //3. Se crearía el objeto PendingIntent con el intent explícito
           // PendingIntent pendingIntent=PendingIntent.getActivities(getContext(),new Random().nextInt(),intent,PendingIntent.FLAG_UPDATE_CURRENT);
          //4. Si se utiliza el componente Navigation, se indicará el grafo de navegación en le PendingIntent.
            //¡¡ATENCIÓN!!
            //
            PendingIntent pendingIntent=new NavDeepLinkBuilder(getContext())
                    .setGraph(R.navigation.nav_graph)
                    .setDestination(R.id.dependencyAddEditFragment)
                    .setArguments(bundle)
                    .createPendingIntent();
            //5. Es crear la notificación
            Notification.Builder builder = new Notification.Builder(getContext(), InventoryApplication.CHANNEL_ID)
                    .setSmallIcon(R.drawable.dependency)
                    .setContentTitle(getString(R.string.notify_add_dependency_title))
                    .setContentText(getString(R.string.notify_add_dependency_description))
                    .setAutoCancel(true);
            builder.setContentIntent(pendingIntent);
            //6. Añadir esa notificación al Manager a gestor.
            NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }


    }

    private void intViewModel() {
        viewModel = new ViewModelProvider(this).get(DependencyManageViewModel.class);
        viewModel.getResult().observe(getViewLifecycleOwner(), dependencyManageResult -> {
            switch (dependencyManageResult) {
                case NAMEEMPTY:
                    showError(R.string.errNameEmpty);
                    break;
                case SHORTNAMEEMPTY:
                    showError(R.string.errShortNameEmpty);
                    break;
                case SHORTNAMEFORMATE:
                    showError(R.string.errShortNameFormat);
                    break;
                case FAILURE:
                    showError(R.string.errDuplicate);
                    break;
                case SUCCESS:
                    showAddNotification(dependency);
                    NavHostFragment.findNavController(this).navigateUp();
                    break;
            }

        });


    }
}