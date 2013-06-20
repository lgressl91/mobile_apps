package com.example.bluetoothdevicefinder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.bluetoothdevicefinder.MainActivity;

public class InformationDialogFragment extends DialogFragment {

    public static InformationDialogFragment newInstance(int title) {
    	InformationDialogFragment frag = new InformationDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(R.string.dialog_information)
                .setNegativeButton(R.string.dialog_read,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ((MainActivity)getActivity()).doPositiveClick();
                        }
                    }
                )
                .create();
    }

}
