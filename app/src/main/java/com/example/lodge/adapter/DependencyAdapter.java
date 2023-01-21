package com.example.lodge.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lodge.data.model.Server;
import com.example.lodge.databinding.ItemServerBinding;

import java.util.ArrayList;
import java.util.List;

public class DependencyAdapter extends RecyclerView.Adapter<DependencyAdapter.ViewHolder> {
    private List<Server> list;
    private OnManageDependencyListener listener;

    public void delete(Server server) {
        list.remove(server);
        notifyDataSetChanged();
    }

    public void undo(Server deleteServer) {
        list.add(deleteServer);
        notifyDataSetChanged();
    }



    /*
    Se declare una interfaz entre el Adapter- IU Controller
     */
    public interface OnManageDependencyListener {
        //1. Si se hace clic se editará la dependencia (onClickListener)
        void onEditServer(Server server);

        //2. Si se hace longclick que se quiere eliminar la dependencia (onLongClickListener)
        void onDeleteServer(Server server);
    }

    @NonNull
    @Override
    public DependencyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //SOLUCIÓN 1: tengo que conseguir el LayoutInflater
        //View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dependency,parent,false));

        //SOLUCIÓN 2: Utiliza la clase binding
        ItemServerBinding binding = ItemServerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding.getRoot());

    }

    public DependencyAdapter(OnManageDependencyListener listener) {
        this.list = new ArrayList<>();
        this.listener=listener;
    }

    @Override
    public void onBindViewHolder(@NonNull DependencyAdapter.ViewHolder holder, int position) {
        //SOLUCIÓN 1:
       //holder.tvName.setText(list.get(position).getName());
       //holder.bind(listener,list.get(position));
        //SOLUCIÓN 2:
        holder.binding.tvName.setText(list.get(position).getName());
        holder.bind(listener,list.get(position));
    }

    /**
     * CIUDADO: que este método no puede devolver el valor por defecto 0. Nunca llamaría a onCreateViewHolder() ni onBindViewHolder
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //CircularImageView imgIcon
        ImageView imgIcon;
        TextView tvName;
        ItemServerBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //SOLUCIÓN 1: Tengo que utilizar el findById
             //imgIcon=itemView.findViewById(R.id.imgIcon);
            //tvName=itemView.findViewById(R.id.tvName);

            //SOLUCIÓN 2: Tengo que ver para que me sirve el método

          binding = ItemServerBinding.bind(itemView);

        }
        //SOLUCIÓN 1 y 2: se inicializa el listener y su dependencia a cada elemento intenView
        public void bind (OnManageDependencyListener listener, Server server)
        {
            itemView.setOnClickListener(view -> listener.onEditServer(server));
            itemView.setOnLongClickListener(view->{
                    listener.onDeleteServer(server);
            return true;}
            );
        }


    }
    /**
     * Este método actualiza los datos del arraylist
     * @param data
     */
    public void update(ArrayList<Server> data)
    {
        list.clear();
        list.addAll(data);
        //Importante: si no se llama a este método no se actualiza el recyclerviwer
        notifyDataSetChanged();
    }

    /**
     * actualiza los datos
     * @param data
     */
    public void orderBy(ArrayList<Server> data) {
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }
}


