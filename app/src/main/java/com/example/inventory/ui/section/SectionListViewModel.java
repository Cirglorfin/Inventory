package com.example.inventory.ui.section;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inventory.data.model.Section;
import com.example.inventory.data.repository.SectionRepository;

import java.util.ArrayList;

public class SectionListViewModel extends ViewModel {
    private MutableLiveData<SectionListResut> state=new MutableLiveData<>();
    private MutableLiveData<ArrayList<Section>> data=new MutableLiveData<>();
    public MutableLiveData<SectionListResut> getState()
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

        state.setValue(SectionListResut.LOADING);
        ArrayList<Section> list = SectionRepository.getInstance().getList();
        if(list.isEmpty())
        {
            state.setValue(SectionListResut.NODATA);
        }
        else{
            data.setValue(list);
            state.setValue(SectionListResut.SUCCESS);

        }
        //state.setValue(SectionListResut.COMPLETE);
    }


}
