package com.ds.owl.dsdormitory;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragmentOut extends Fragment {
    TextView apply_day;
    Button out_start;
    Button out_end;
    Button out_apply;
    //TextView today;
    EditText desti;
    EditText reason;

    public TabFragmentOut() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =inflater.inflate(R.layout.fragment_tab_fragment_out, container, false);

        ListView listView;
        final OutListviewAdapter adapter;

        apply_day = (TextView)v.findViewById(R.id.today);

        SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        Date date = new Date();
        String strDate = dateFormat.format(date);
        apply_day.setText(strDate);

        out_start = (Button)v.findViewById(R.id.out_start);

        out_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dFragment1 = new DatePickerFragment();

                // Show the date picker dialog fragment
                dFragment1.show(getFragmentManager(), "Date Picker");

            }
        });

        out_end = (Button)v.findViewById(R.id.out_end);

        out_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dFragment2 = new DatePickerFragment2();

                dFragment2.show(getFragmentManager(), "Date Picker");
            }
        });


        adapter = new OutListviewAdapter();

        listView=(ListView)v.findViewById(R.id.out_list);
        listView.setAdapter(adapter);

        desti=(EditText)v.findViewById(R.id.desti);
        reason=(EditText)v.findViewById(R.id.reason);

        out_apply = (Button)v.findViewById(R.id.out_apply);
        out_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // today=(TextView) v.findViewById(R.id.today);
                final String out_day = apply_day.getText().toString();
                final String start_day = out_start.getText().toString();
                final String end_day = out_end.getText().toString();
                final String out_term = start_day+" "+"~"+" "+end_day;

                final String out_desti = desti.getText().toString();

                final String out_reason = reason.getText().toString();

                adapter.addItem(out_day,out_term,out_desti,out_reason);
            }
        });




        return v;
    }

}
