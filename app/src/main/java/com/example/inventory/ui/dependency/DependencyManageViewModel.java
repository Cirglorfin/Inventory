package com.example.inventory.ui.dependency;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inventory.data.model.Dependency;
import com.example.inventory.data.repository.DependencyRepository;
import com.example.inventory.utils.CommonUtils;

public class DependencyManageViewModel extends ViewModel {
    MutableLiveData<DependencyManageResult> result = new MutableLiveData<>();


    public MutableLiveData<DependencyManageResult> getResult() {
        return result;
    }

    public void edit(Dependency dependency) {
        if (validateName(dependency)) {
            if (DependencyRepository.getInstance().edit(dependency)) {
                result.setValue(DependencyManageResult.SUCCESS);
            } else {
                result.setValue(DependencyManageResult.FAILURE);
            }
        }
    }

    public void add(Dependency dependency) {
        if (validateName(dependency)&&validateShortName(dependency)) {
            if (DependencyRepository.getInstance().add(dependency)) {
                result.setValue(DependencyManageResult.SUCCESS);
            }
            //else {
              //  result.setValue(DependencyManageResult.FAILURE);
            //}
        }
    }

    private boolean validateName(Dependency dependency) {
        if (TextUtils.isEmpty(dependency.getName()))
        //vista muestra un mensaje de error personalizado
        {
            result.setValue(DependencyManageResult.NAMEEMPTY);
        } else {
            return true;
        }
        return false;
    }

    private boolean validateShortName(Dependency dependency) {
        if (TextUtils.isEmpty(dependency.getShortname())) {
            result.setValue(DependencyManageResult.SHORTNAMEEMPTY);
        } else if (!CommonUtils.isShortNameValid(dependency.getShortname())) {
            result.setValue(DependencyManageResult.SHORTNAMEFORMATE);
        } else {
            return true;
        }
        return false;
    }
}
