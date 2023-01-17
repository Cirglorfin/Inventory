package com.example.inventory.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventory.R;
import com.example.inventory.data.model.Dependency;
import com.example.inventory.data.repository.DependencyRepository;
import com.example.inventory.databinding.ItemServerBinding;

import java.util.ArrayList;
import java.util.List;

public class DependencyAdapter extends RecyclerView.Adapter<DependencyAdapter.ViewHolder> {
    private List<Dependency> list;
    private OnManageDependencyListener listener;

    public void delete(Dependency dependency) {
        list.remove(dependency);
        notifyDataSetChanged();
    }

    public void undo(Dependency deleteDependency) {
        list.add(deleteDependency);
        notifyDataSetChanged();
    }



    /*
    Se declare una interfaz entre el Adapter- IU Controller
     */
    public interface OnManageDependencyListener {
        //1. Si se hace clic se editará la dependencia (onClickListener)
        void onEditDependency(Dependency dependency);

        //2. Si se hace longclick que se quiere eliminar la dependencia (onLongClickListener)
        void onDeleteDependency(Dependency dependency);
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
        public void bind (OnManageDependencyListener listener,Dependency dependency)
        {
            itemView.setOnClickListener(view -> listener.onEditDependency(dependency));
            itemView.setOnLongClickListener(view->{
                    listener.onDeleteDependency(dependency);
            return true;}
            );
        }


    }
    /**
     * Este método actualiza los datos del arraylist
     * @param data
     */
    public void update(ArrayList<Dependency> data)
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
    public void orderBy(ArrayList<Dependency> data) {
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }
}


