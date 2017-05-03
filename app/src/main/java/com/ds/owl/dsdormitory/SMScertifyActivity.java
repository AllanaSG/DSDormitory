package com.ds.owl.dsdormitory;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import static com.ds.owl.dsdormitory.LoginActivity.name;

public class SMScertifyActivity extends AppCompatActivity {
    TextView sms_text;

    String phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smscertify);

        sms_text = (TextView)findViewById(R.id.sms_text);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        try {

            String page = "http://192.168.35.61:8080/0401/getphone.jsp";
            HttpClient http = new DefaultHttpClient();

            ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();
            postData.add(new BasicNameValuePair("name", name.toString()));
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
                phone_number = row.getString("phone_num");
                Log.d("phonenum", phone_number);
            }

            String phone1 = phone_number.substring(0,3);
            String phone2 = phone_number.substring(3,7);
            String phone3 = phone_number.substring(7,11);
            sms_text.setText(phone1 + "-" + phone2 + "-" + phone3 + "으로 인증번호가 전송되었습니다.");

        }catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {

        }


        
    }
}
