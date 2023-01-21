package com.example.lodge.ui.server;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lodge.data.model.Server;
import com.example.lodge.data.model.ServerComparatorId;
import com.example.lodge.data.repository.ServerRepository;
import com.example.lodge.viewmodel.StateLiveDataList;

import java.util.ArrayList;

/**
 * Clase que gestiona las reglas de negocio (Entidad) de la lista ListFragment
 */
public class ServerListViewModel extends ViewModel {
    private MutableLiveData<String> user;
    private StateLiveDataList<ArrayList<Server>> liveDataList=new StateLiveDataList<>();
    private MutableLiveData<Server> deleteDependency=new MutableLiveData<>();

    /**
     * Método que inicializa el objeto LiveDataList que contiene las posibles opciones alternativas
     * del caso de uso listar
     * @return
     */
    public StateLiveDataList<ArrayList<Server>> getLiveDataList(){
        if(liveDataList ==null){
            liveDataList=new StateLiveDataList<>();
        }
        return liveDataList;
    }

    public void getDataList() {
        //Muestra un progressbar
        liveDataList.setLoading();
        //3. Pedimmos los datos al repositorio
       ArrayList<Server> list= ServerRepository.getInstance().getList();
       //3.1 No DATA
        if(list.isEmpty())
            liveDataList.setNodata();
        //success
        else
            liveDataList.setSuccess(list);
       // liveDataList.setComplete();
    }

    public void delete(Server server) {
        deleteDependency.setValue(server);
        ServerRepository.getInstance().delete(server);


    }


    public LiveData<Server> getDeleteServer() {
        return deleteDependency;
    }

    public void setDeleteDependency(MutableLiveData<Server> deleteDependency) {
        this.deleteDependency = deleteDependency;
    }

    public void undo() {
        ServerRepository.getInstance().add(deleteDependency.getValue());
        deleteDependency.setValue(null);
    }

    public void orderById() {
       // Collections.sort(liveDataList.getValue().getData(),new ServerComparatorId());
        liveDataList.getValue().getData().sort(new ServerComparatorId());
            liveDataList.setOrderById(liveDataList.getValue().getData());

    }
}
