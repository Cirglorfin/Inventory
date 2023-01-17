package com.example.inventory.ui.dependency;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inventory.data.model.Dependency;
import com.example.inventory.data.model.DependencyComparatorId;
import com.example.inventory.data.repository.DependencyRepository;
import com.example.inventory.viewmodel.StateLiveDataList;

import java.util.ArrayList;

/**
 * Clase que gestiona las reglas de negocio (Entidad) de la lista ListFragment
 */
public class DependencyListViewModel extends ViewModel {
    private MutableLiveData<String> user;
    private StateLiveDataList<ArrayList<Dependency>> liveDataList=new StateLiveDataList<>();
    private MutableLiveData<Dependency> deleteDependency=new MutableLiveData<>();

    /**
     * Método que inicializa el objeto LiveDataList que contiene las posibles opciones alternativas
     * del caso de uso listar
     * @return
     */
    public StateLiveDataList<ArrayList<Dependency>> getLiveDataList(){
        if(liveDataList ==null){
            liveDataList=new StateLiveDataList<>();
        }
        return liveDataList;
    }

    public void getDataList() {
        //Muestra un progressbar
        liveDataList.setLoading();
        //3. Pedimmos los datos al repositorio
       ArrayList<Dependency> list= DependencyRepository.getInstance().getList();
       //3.1 No DATA
        if(list.isEmpty())
            liveDataList.setNodata();
        //success
        else
            liveDataList.setSuccess(list);
       // liveDataList.setComplete();
    }

    public void delete(Dependency dependency) {
        deleteDependency.setValue(dependency);
        DependencyRepository.getInstance().delete(dependency);


    }


    public LiveData<Dependency> getDeleteDependency() {
        return deleteDependency;
    }

    public void setDeleteDependency(MutableLiveData<Dependency> deleteDependency) {
        this.deleteDependency = deleteDependency;
    }

    public void undo() {
        DependencyRepository.getInstance().add(deleteDependency.getValue());
        deleteDependency.setValue(null);
    }

    public void orderById() {
       // Collections.sort(liveDataList.getValue().getData(),new DependencyComparatorId());
        liveDataList.getValue().getData().sort(new DependencyComparatorId());
            liveDataList.setOrderById(liveDataList.getValue().getData());

    }
}
