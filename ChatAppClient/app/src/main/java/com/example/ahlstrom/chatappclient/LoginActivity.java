package com.example.ahlstrom.chatappclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * Created by Ahlstrom
 * <p>
 * This Activity asks for user to give a Username and IP-number to log in to the chat server.
 */

public class LoginActivity extends AppCompatActivity {

    Button inbtn;
    EditText usrField;
    EditText ipField;
    private String usrName;
    private String ipNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inbtn = (Button) findViewById(R.id.signInButton);
        usrField = (EditText) findViewById(R.id.usernameField);
        ipField = (EditText) findViewById(R.id.ipnumField);

        inbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (usrField.getText().length() <= 9) {
                    setIp(ipField.getText().toString());
                    setUsrName(usrField.getText().toString());

                    Intent intent = new Intent(getApplicationContext(), ChatActivity.class);

                    intent.putExtra("ipNum", ipNum);
                    intent.putExtra("usrName", usrName);

                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Username can't be more than 9 charachters", Toast.LENGTH_SHORT).show();
                    usrField.getText().clear();
                }

            }
        });
    }

    private void setIp(String ip) {
        ipNum = ip;
    }

    private void setUsrName(String usr) {
        usrName = usr;
    }

}
