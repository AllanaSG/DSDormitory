package com.ds.owl.dsdormitory;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.R.attr.id;
import static android.content.ContentValues.TAG;


public class LoginActivity extends AppCompatActivity {

    public static AutoCompleteTextView studentnumber;
    public static EditText password;
    Button loginbutton;

    public static String name="";
    String result="";

    private static final int MY_PERMISSION_REQUEST_CODE = 10;
    private View mLayout;
    String txtPhoneNo;
    String txtMessage;
    Random rand = new Random();
    public static String random_num;
    public static String phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        studentnumber = (AutoCompleteTextView)findViewById(R.id.sudent_number);
        password = (EditText)findViewById(R.id.password);
        loginbutton = (Button)findViewById(R.id.sign_in_button);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {

            }
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String page = "http://192.168.35.164:8080/0401/dormlogin.jsp";
                                    HttpClient http = new DefaultHttpClient();

                                    ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();
                                    postData.add(new BasicNameValuePair("studentnumber", studentnumber.getText().toString()));
                                    postData.add(new BasicNameValuePair("pwd", password.getText().toString()));
                                    UrlEncodedFormEntity request = new UrlEncodedFormEntity(postData, "utf-8");
                                    HttpPost httpPost = new HttpPost(page);
                                    httpPost.setEntity(request);

                                    HttpResponse response = http.execute(httpPost);
                                    String body = EntityUtils.toString(response.getEntity());
                                    JSONObject jsonObject = new JSONObject(body);
                                    JSONArray jArray = (JSONArray) jsonObject.get("sendData");
                                    Log.i("MyTag", "jArray:" + jArray);
                                    if (jArray.length() > 0) {
                                        JSONObject row = jArray.getJSONObject(0);
                                        name = row.getString("name");
                                        phone_number = row.getString("phone_num");
                                        result = name + "님 환영합니다.";
                                        Toast.makeText(LoginActivity.this, result, Toast.LENGTH_LONG).show();
                                        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent1);

                                    } else {
                                        result = "아이디 또는 비밀번호가 일치하지 않습니다.";
                                        Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {

                                }
                            }
                        });
                    }
                });th.start();
            }
        });
    }

    private void requestSMSPermission() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)|| !ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS ))  {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS}, MY_PERMISSION_REQUEST_CODE);
            return;
        }

        Snackbar.make(mLayout, R.string.common_signin_button_text, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.common_signin_button_text, new View.OnClickListener() {
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS}, MY_PERMISSION_REQUEST_CODE);
                    }
                })
                .show();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    random_num = String.valueOf(rand.nextInt(8999)+1000).toString();
                    txtPhoneNo = phone_number;
                    txtMessage = "인증번호는 " + random_num;
                    Log.d("random_num", random_num);
                    Log.v("tag명 ", String.valueOf(random_num));
                    if (txtPhoneNo.length() > 0 && txtMessage.length() > 0)
                        sendSMS(txtPhoneNo, txtMessage);
                    else
                        Toast.makeText(getBaseContext(),
                                "일치하지 않음",
                                Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "SMS 접근 권한 없음");
                }
                break;
        }
    }

    private void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
        Intent intent1 = new Intent(getApplicationContext(), SMScertifyActivity.class);
        startActivity(intent1);
    }
}
