package com.example.lodge.ui.server;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.lodge.R;
import com.example.lodge.data.model.Server;
import com.example.lodge.databinding.FragmentServerManageBinding;
import com.example.lodge.ui.LodgeApplication;
import com.example.lodge.ui.base.BaseFragment;


public class ServerManageFragment extends BaseFragment {
    private static final int NOTIFICATION_ID = 1234;
    FragmentServerManageBinding binding;
    private ServerManageViewModel viewModel;
    private Server server;

    public ServerManageFragment() {
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
        binding = FragmentServerManageBinding.inflate(inflater, container, false);

        initLayout();
        intViewModel();

        return binding.getRoot();
    }

    private void initLayout() {
        ArrayAdapter<CharSequence> tipo=ArrayAdapter.createFromResource(requireContext(),R.array.tipo,R.layout.spinner_item);
        tipo.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.spinner.setAdapter(tipo);
        if (getArguments() != null) {
            binding.btn.setText(R.string.editar);
            binding.titulo.setText(R.string.editar_grupo);
            server =getArguments().getParcelable("server");
            int posicion=0;
            String accesiblidad=server.getPrivacy();
            if(accesiblidad!=getResources().getString(R.string.publico)){
                posicion=1;
            }
            binding.spinner.setSelection(posicion);
            binding.Descripcion.setText(server.getDescription());
            binding.imagen.setText(server.getImageName());
            binding.nombre.setText(server.getName());
            binding.btn.setOnClickListener(v -> {
                server = new Server( server.getId(),binding.nombre.getText().toString(), binding.Descripcion.getText().toString(), binding.imagen.getText().toString(),binding.spinner.getSelectedItem().toString());
                viewModel.edit(server);
            });
        } else {
            binding.btn.setOnClickListener(v -> {
                server =new Server( binding.nombre.getText().toString(), binding.Descripcion.getText().toString(), binding.imagen.getText().toString(),binding.spinner.getSelectedItem().toString());
                viewModel.add(server);
            });
        }
    }

    private void showAddNotification(Server server) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //1. Crear un Bundle con la dependencia añadida
            Bundle bundle=new Bundle();
            bundle.putParcelable("depedency", server);
         /*   bundle.putString("nombre", server.getName());
            bundle.putString("descripcion", server.getDescription());
            bundle.putString("imagen", server.getImageName());
            */
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
                    .setDestination(R.id.serverManageFragment)
                    .setArguments(bundle)
                    .createPendingIntent();
            //5. Es crear la notificación
            Notification.Builder builder = new Notification.Builder(getContext(), LodgeApplication.CHANNEL_ID)
                    .setSmallIcon(R.drawable.lodge)
                    .setContentTitle(getString(R.string.notify_add_server_title))
                    .setContentText(getString(R.string.notify_add_server_description))
                    .setAutoCancel(true);
            builder.setContentIntent(pendingIntent);
            //6. Añadir esa notificación al Manager a gestor.
            NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }


    }

    private void intViewModel() {
        viewModel = new ViewModelProvider(this).get(ServerManageViewModel.class);
        viewModel.getResult().observe(getViewLifecycleOwner(), dependencyManageResult -> {
            switch (dependencyManageResult) {
                case NAMEEMPTY:
                    showError(R.string.errNameEmpty);
                    break;
                case FAILURE:
                    showError(R.string.errDuplicate);
                    break;
                case SUCCESS:
                    showAddNotification(server);
                    NavHostFragment.findNavController(this).navigateUp();
                    break;
            }

        });


    }
}