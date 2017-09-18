package com.ds.owl.dsdormitory;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Chronometer;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.Random;

public class DateandTimeActivity extends AppCompatActivity {

    Chronometer chrono;
    Button btnStart, btnEnd;
    RadioButton rdoCal, rdoTime;
    CalendarView calendarView;
    TimePicker tPicker;
    TextView tvYear, tvMonth, tvDay, tvHour, tvMin;

    Random rand = new Random();
    String reservetime;
    String laundry_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dateand_time);

        setTitle("시간예약");

        //btnStart = (Button) findViewById(R.id.btnStart);
        btnEnd = (Button) findViewById(R.id.btnEnd);
        chrono = (Chronometer) findViewById(R.id.chronometer1);
        rdoCal = (RadioButton) findViewById(R.id.rdoCal);
        rdoTime = (RadioButton) findViewById(R.id.rdoTime);
        calendarView = (CalendarView) findViewById(R.id.calendarView1);
        tPicker = (TimePicker) findViewById(R.id.timePicker1);
        tvYear = (TextView) findViewById(R.id.tvYear);
        tvMonth = (TextView) findViewById(R.id.tvMonth);
        tvDay = (TextView) findViewById(R.id.tvDay);
        tvHour = (TextView) findViewById(R.id.tvHour);
        tvMin = (TextView) findViewById(R.id.tvMin);
        tPicker.setVisibility(View.INVISIBLE);
        calendarView.setVisibility(View.INVISIBLE);

        rdoCal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                tPicker.setVisibility(View.INVISIBLE);
                calendarView.setVisibility(View.VISIBLE);
            }
        });

        rdoTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                tPicker.setVisibility(View.VISIBLE);
                calendarView.setVisibility(View.INVISIBLE);
            }
        });

       /* btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                chrono.setBase(SystemClock.elapsedRealtime());
                chrono.start();
                chrono.setTextColor(Color.RED);
            }
        });*/

        btnEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                chrono.stop();
                chrono.setTextColor(Color.BLUE);

                java.util.Calendar curDate = java.util.Calendar.getInstance();
                curDate.setTimeInMillis(calendarView.getDate());

                tvYear.setText(Integer.toString(curDate.get(Calendar.YEAR)));
                tvMonth.setText(Integer.toString(1 + curDate.get(Calendar.MONTH)));
                tvDay.setText(Integer.toString(curDate.get(Calendar.DATE)));

                tvHour.setText(Integer.toString(tPicker.getCurrentHour()));
                tvMin.setText(Integer.toString(tPicker.getCurrentMinute()));

                reservetime = (tvYear.toString() + tvMonth.toString() + tvDay.toString() + tvHour.toString() + tvMin.toString());
                laundry_num = String.valueOf(rand.nextInt(8999)+1000).toString();

                try
                {
                    HttpClient httpClient = new DefaultHttpClient();

                    String url = "http://192.168.35.101:8080/0401/laundry.jsp";
                    String studnum = String.valueOf(LoginActivity.studentnumber.getText().toString());
                    String pwd = String.valueOf(LoginActivity.password.getText().toString());

                    if(studnum!=null&&pwd!=null) {
                        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                        nameValuePairs.add(new BasicNameValuePair("Student Number", studnum));
                        nameValuePairs.add(new BasicNameValuePair("Password", pwd));
                        nameValuePairs.add(new BasicNameValuePair("Time", reservetime));
                        nameValuePairs.add(new BasicNameValuePair("LaundryNum", laundry_num));

                        Log.v("student", studnum);
                        Log.v("reserveTime", reservetime);
                        Log.v("LaundryNum", laundry_num);

                        HttpParams params = httpClient.getParams();
                        HttpConnectionParams.setConnectionTimeout(params, 5000);
                        HttpConnectionParams.setSoTimeout(params, 5000);

                        HttpPost httpPost1 = new HttpPost(url);
                        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs, "euc-kr");
                        httpPost1.setEntity(entity);

                        HttpResponse response = httpClient.execute(httpPost1);
                        HttpEntity resEntity = response.getEntity();

                        Log.v("response", response.getStatusLine().toString());

                        Toast.makeText(DateandTimeActivity.this, "예약이 완료되었습니다. 비밀번호는 " + laundry_num + "입니다.", Toast.LENGTH_SHORT);

                        SmsManager sms = SmsManager.getDefault();
                        String message = curDate + "에 예약하신 세탁기의 비밀번호는" + laundry_num + "입니다.";
                        sms.sendTextMessage(LoginActivity.phone_number, null, message, null, null);
                    }
                }
                catch (IOException e){
                    //Log.v("checkTime2", currentTime);
                   // Log.v("Attendance2", msg);
                   // Log.d("checkTime2", currentTime);
                   // Log.d("Attendance2", msg);
                }

            }
        });



    }

}
