package com.example.lodge.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lodge.data.model.Section;
import com.example.lodge.databinding.ItemSectionBinding;

import java.util.ArrayList;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.ViewHolder> {
    private ArrayList<Section> list;
    private OnManageSectionListener listener;

    /*
    Se declare una interfaz entre el Adapter- IU Controller
     */
    public interface OnManageSectionListener {
        //1. Si se hace clic se editará la dependencia (onClickListener)
        void onEditSection(Section section);

        //2. Si se hace longclick que se quiere eliminar la dependencia (onLongClickListener)
        void onDeleteSection(Section section);
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //SOLUCIÓN 1: tengo que conseguir el LayoutInflater
        //View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dependency,parent,false));

        //SOLUCIÓN 2: Utiliza la clase binding
        ItemSectionBinding binding = ItemSectionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new SectionAdapter.ViewHolder(binding.getRoot());

    }

    public SectionAdapter() {
        this.list = new ArrayList<>();
        this.listener=listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //SOLUCIÓN 1:
        //holder.tvName.setText(list.get(position).getName());
        //holder.bind(listener,list.get(position));

        holder.binding.tvName.setText(list.get(position).getNombre());
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
        ItemSectionBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //SOLUCIÓN 1: Tengo que utilizar el findById
            //imgIcon=itemView.findViewById(R.id.imgIcon);
            //tvName=itemView.findViewById(R.id.tvName);

            //SOLUCIÓN 2: Tengo que ver para que me sirve el método

            binding = ItemSectionBinding.bind(itemView);

        }
        //SOLUCIÓN 1 y 2: se inicializa el listener y su dependencia a cada elemento intenView
        public void bind (OnManageSectionListener listener, Section dependency)
        {
            itemView.setOnClickListener(view -> listener.onEditSection(dependency));
            itemView.setOnLongClickListener(view->{
                listener.onDeleteSection(dependency);
                return true;}
            );
        }


    }
    /**
     * Este método actualiza los datos del arraylist
     * @param data
     */
    public void update(ArrayList<Section> data)
    {
        list.clear();
        list.addAll(data);
        //Importante: si no se llama a este método no se actualiza el recyclerviwer
        notifyDataSetChanged();
    }
}
