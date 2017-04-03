package com.ds.owl.dsdormitory;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import static android.R.attr.id;

public class LoginActivity extends AppCompatActivity {

    public static AutoCompleteTextView studentnumber;
    public static EditText password;
    Button loginbutton;

    public static String name="";
    String result="";
    static final int GET_NAME = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        studentnumber = (AutoCompleteTextView)findViewById(R.id.sudent_number);
        password = (EditText)findViewById(R.id.password);
        loginbutton = (Button)findViewById(R.id.sign_in_button);

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
                                    String page = "http://192.168.35.169:8080/0401/dormlogin.jsp";
                                    HttpClient http = new DefaultHttpClient();

                                    ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();
                                    postData.add(new BasicNameValuePair("id", studentnumber.getText().toString()));
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
                                        result = name + "님 환영합니다.";
                                    } else {
                                        result = "아이디 또는 비밀번호가 일치하지 않습니다.";
                                    }
                                    Toast.makeText(LoginActivity.this, result, Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                   // intent.putExtra("INPUT_NAME", name);
                                    // setResult(RESULT_OK, intent);
                                    startActivity(intent);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {

                                }
                            }
                        });
                    }


                });
                th.start();
            }
        });
    }
}
