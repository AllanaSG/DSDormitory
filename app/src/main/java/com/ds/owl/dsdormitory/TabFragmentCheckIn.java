package com.ds.owl.dsdormitory;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ToggleButton;


/**
 * A simple {@link Fragment} subclass.
 */

//점호 tab에 보여 줄 fragment

public class TabFragmentCheckIn extends Fragment {

    public static final String RECO_UUID = "24DDF411-8CF1-440C-87CD-E368DAF9C93E";

    public static final boolean SCAN_RECO_ONLY = true;

    public static final boolean ENABLE_BACKGROUND_RANGING_TIMEOUT = true;

    public static final boolean DISCONTINUOUS_SCAN = false;

    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_LOCATION = 10;

    ToggleButton backmonitoringBtn;
    Button foremonitoringBtn;

    public TabFragmentCheckIn() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_tab_fragment_checkin, container, false);

        backmonitoringBtn = (ToggleButton)v.findViewById(R.id.backgroundMonitoringToggleButton);

       backmonitoringBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleButton toggle = (ToggleButton)v;
                if(toggle.isChecked()) {
                    Log.i("MainActivity", "onMonitoringToggleButtonClicked off to on");
                    // Intent intent = new Intent(getActivity(), RecoBackgroundMonitoringService.class);
                    //startService(intent);
                    getActivity().startService(new Intent(getActivity(),RecoBackgroundMonitoringService.class));

                } else {
                    Log.i("MainActivity", "onMonitoringToggleButtonClicked on to off");
                    //stopService(new Intent(this, RecoBackgroundMonitoringService.class));
                }
            }
        });

        foremonitoringBtn = (Button)v.findViewById(R.id.monitoringButton);

        foremonitoringBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Button btn = (Button)v;
                getActivity().startActivity(new Intent(getActivity(),RecoMonitoringActivity.class));
            }
        });

        return v;

    }


}
