package com.example.lodge.ui.section;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lodge.data.model.Section;
import com.example.lodge.data.repository.SectionRepository;

import java.util.ArrayList;

public class SectionListViewModel extends ViewModel {
    private MutableLiveData<SectionListResult> state=new MutableLiveData<>();
    private MutableLiveData<ArrayList<Section>> data=new MutableLiveData<>();
    public MutableLiveData<SectionListResult> getState()
    {
        if(state==null)
        {
            state=new MutableLiveData<>();
        }
        return state;
    }
    public  MutableLiveData<ArrayList<Section>> getData()
    {
        if(data==null)
        {
            data=new MutableLiveData<>();
        }
        return data;
    }
    public void getList()
    {

        state.setValue(SectionListResult.LOADING);
        ArrayList<Section> list = SectionRepository.getInstance().getList();
        if(list.isEmpty())
        {
            state.setValue(SectionListResult.NODATA);
        }
        else{
            data.setValue(list);
            state.setValue(SectionListResult.SUCCESS);

        }
        //state.setValue(SectionListResult.COMPLETE);
    }


}
