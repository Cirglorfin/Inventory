package com.example.lodge.ui.server;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lodge.data.model.Server;
import com.example.lodge.data.repository.ServerRepository;

public class ServerManageViewModel extends ViewModel {
    MutableLiveData<ServerManageResult> result = new MutableLiveData<>();


    public MutableLiveData<ServerManageResult> getResult() {
        return result;
    }

    public void edit(Server server) {
        if (validateName(server)) {
            if (ServerRepository.getInstance().edit(server)) {
                result.setValue(ServerManageResult.SUCCESS);
            } else {
                result.setValue(ServerManageResult.FAILURE);
            }
        }
    }

    public void add(Server server) {
        if (validateName(server)) {
            if (ServerRepository.getInstance().add(server)) {
                result.setValue(ServerManageResult.SUCCESS);
            }
            //else {
              //  result.setValue(ServerManageResult.FAILURE);
            //}
        }
    }

    private boolean validateName(Server server) {
        if (TextUtils.isEmpty(server.getName()))
        //vista muestra un mensaje de error personalizado
        {
            result.setValue(ServerManageResult.NAMEEMPTY);
        } else {
            return true;
        }
        return false;
    }
}
