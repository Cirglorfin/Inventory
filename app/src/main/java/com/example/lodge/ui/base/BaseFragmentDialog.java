package com.example.lodge.ui.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class BaseFragmentDialog extends DialogFragment {
    public static final String KEY_TITLE="title";
    public static final String KEY_MESSAGE="message";
    public static final String KEY_BUNDLE="result";
    public static final String KEY_REQUEST="request";
    public static final String TAG_FRAGMENT="fragment";
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if(getArguments()!=null)
        {
            //Me han pasado información
            String title=getArguments().getString(KEY_TITLE);
            String message=getArguments().getString(KEY_MESSAGE);
          //  String request =getArguments().getString(KEY_REQUEST);
            AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
            builder.setTitle(title);
            builder.setMessage(message);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Bundle result=new Bundle();
                    result.putBoolean(KEY_BUNDLE,true);
                    //
                    getActivity().getSupportFragmentManager().setFragmentResult(KEY_REQUEST,result);
                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dismiss();
                }
            });
            return builder.create();
        }
        return null;
    }
}
