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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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
    ToggleButton backrangingBtn;
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

        backrangingBtn = (ToggleButton)v.findViewById(R.id.backgroundRangingToggleButton);

        backrangingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleButton toggle = (ToggleButton)v;
                if(toggle.isChecked()) {
                    Log.i("MainActivity", "onRangingToggleButtonClicked off to on");
                    //Intent intent = new Intent(this, RecoBackgroundRangingService.class);
                    //startService(intent);
                    getActivity().startService(new Intent(getActivity(),RecoBackgroundRangingService.class));
                } else {
                    Log.i("MainActivity", "onRangingToggleButtonClicked on to off");
                    //stopService(new Intent(this, RecoBackgroundRangingService.class));
                }
            }
        });



        foremonitoringBtn = (Button)v.findViewById(R.id.monitoringButton);

        foremonitoringBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().startActivity(new Intent(getActivity(),RecoMonitoringActivity.class));

              /*  try
                {
                    HttpClient httpClient = new DefaultHttpClient();

                    String url = "http://192.168.35.241:8080/0401/checkin.jsp";
                    String studnum = String.valueOf(LoginActivity.studentnumber.getText().toString());
                    String pwd = String.valueOf(LoginActivity.password.getText().toString());

                    if(studnum!=null&&pwd!=null&&RecoMonitoringActivity.time!=null&&RecoMonitoringListAdapter.attendance!=null) {
                        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                        nameValuePairs.add(new BasicNameValuePair("Student Number", studnum));
                        nameValuePairs.add(new BasicNameValuePair("Password", pwd));
                        nameValuePairs.add(new BasicNameValuePair("Time", RecoMonitoringActivity.time));
                        nameValuePairs.add(new BasicNameValuePair("Attendance", RecoMonitoringListAdapter.attendance));

                        HttpParams params = httpClient.getParams();
                        HttpConnectionParams.setConnectionTimeout(params, 5000);
                        HttpConnectionParams.setSoTimeout(params, 5000);

                        HttpPost httpPost1 = new HttpPost(url);
                        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs, "euc-kr");
                        httpPost1.setEntity(entity);

                        HttpResponse response = httpClient.execute(httpPost1);
                        HttpEntity resEntity = response.getEntity();
                    }
                }
                catch (IOException e){}*/
            }
        });


        return v;

    }

    public static void click() {


        try
        {
            HttpClient httpClient = new DefaultHttpClient();

            String url = "http://192.168.0.67:8080/0401/checkin.jsp";
            String studnum = String.valueOf(LoginActivity.studentnumber.getText().toString());
            String pwd = String.valueOf(LoginActivity.password.getText().toString());

            if(studnum!=null&&pwd!=null&&RecoMonitoringActivity.time!=null&&RecoMonitoringListAdapter.attendance!=null) {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("Student Number", studnum));
                nameValuePairs.add(new BasicNameValuePair("Password", pwd));
                nameValuePairs.add(new BasicNameValuePair("Time", RecoMonitoringActivity.time));
                nameValuePairs.add(new BasicNameValuePair("Attendance", RecoMonitoringListAdapter.attendance));

                HttpParams params = httpClient.getParams();
                HttpConnectionParams.setConnectionTimeout(params, 5000);
                HttpConnectionParams.setSoTimeout(params, 5000);

                HttpPost httpPost1 = new HttpPost(url);
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs, "euc-kr");
                httpPost1.setEntity(entity);

                HttpResponse response = httpClient.execute(httpPost1);
                HttpEntity resEntity = response.getEntity();
            }
        }
        catch (IOException e){

        }
    }



}
