package com.ds.owl.dsdormitory;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragmentLaundry extends Fragment {

    Button get_reserve;

    Button laundry1;
    Button laundry2;
    Button laundry3;
    Button laundry4;

    public TabFragmentLaundry() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_tab_fragment_laundry, container, false);

        get_reserve = (Button)v.findViewById(R.id.get_reserve);

        get_reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        laundry1 = (Button)v.findViewById(R.id.laundry1);
        laundry2 = (Button)v.findViewById(R.id.laundry2);
        laundry3 = (Button)v.findViewById(R.id.laundry3);
        laundry4 = (Button)v.findViewById(R.id.laundry4);

        laundry1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), DateandTimeActivity.class);
                startActivity(intent1);
            }
        });


        return v;
    }


}
