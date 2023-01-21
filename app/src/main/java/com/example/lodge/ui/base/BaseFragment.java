package com.example.lodge.ui.base;

import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class BaseFragment  extends Fragment {
    /**
     * Este método muestra un error en el fragment
     * @param error
     */
    public void showError(String error)
    {
        Toast.makeText(getContext(),error, Toast.LENGTH_SHORT).show();
    }
    public void showError(int error)
    {
        Toast.makeText(getContext(),getString(error), Toast.LENGTH_SHORT).show();
    }
}
