package com.ds.owl.dsdormitory;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.ds.owl.dsdormitory.LoginActivity.phone_number;
import static com.ds.owl.dsdormitory.LoginActivity.random_num;

public class SMScertifyActivity extends AppCompatActivity {
    TextView sms_text;
    EditText sms_input;
    String user_input;
    Button sms_btn;

    String phone1;
    String phone2;
    String phone3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smscertify);

        sms_text = (TextView)findViewById(R.id.sms_text);
        sms_input = (EditText)findViewById(R.id.sms_input);
        user_input = sms_input.getText().toString();
        sms_btn = (Button)findViewById(R.id.sms_btn);

            phone1 = phone_number.substring(0,3);
            phone2 = phone_number.substring(3,7);
            phone3 = phone_number.substring(7,11);
            sms_text.setText(phone1 + "-" + phone2 + "-" + phone3 + "으로 인증번호가 전송되었습니다.");


        sms_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user_input = sms_input.getText().toString();
                if(random_num.equals(user_input)) {
                    Toast.makeText(SMScertifyActivity.this, "인증이 완료되었습니다!", Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent1);
                } else if(random_num != user_input) {
                    Log.d("random_num", random_num);
                    Log.d("user_input", user_input);

                    Toast.makeText(SMScertifyActivity.this, "인증번호가 일치하지 않습니다", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


}
