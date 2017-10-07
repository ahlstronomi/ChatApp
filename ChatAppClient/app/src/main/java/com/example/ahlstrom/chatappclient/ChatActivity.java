package com.example.ahlstrom.chatappclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatActivity extends AppCompatActivity {

    private Button btn;
    private EditText msgField;
    private Socket socket;
    private RecyclerView messagesView;
    private int socketNum = 1337;
    private String ipNum = "10.0.2.2";
    private String usrname = "";
    private InputStream inputStream;
    private PrintStream outputStream;
    private String messages;
    private Scanner in;
    private MessageListAdapter msgListAdapter;
    private User user;
    public String ownUserId;
    private List messageList;
    private boolean hasUserId = false;
    private boolean messageSent = false;


    public static final int SENT = 1;
    public static final int RECEIVED = 2;
    public static final int NOTIFICATION = 0;


    LoginActivity logIn = new LoginActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messageList = new ArrayList();


        // TODO KUPLAT EI TULE RUUDULLE
        msgListAdapter = new MessageListAdapter(this, messageList);
        messagesView = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        // messagesView.setAdapter(msgListAdapter);
        messagesView.setLayoutManager(new LinearLayoutManager(this));


        btn = (Button) findViewById(R.id.button_chatbox_send);
        msgField = (EditText) findViewById(R.id.edittext_chatbox);

        Intent intent = getIntent();
        ipNum = intent.getStringExtra("ipNum");
        user = new User(intent.getStringExtra("usrName"));

        Log.d("Ip Number ", ipNum);
        Log.d("UserName ", user.getUsername());

        // SOCKET THREAD
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    socket = new Socket(ipNum, socketNum);
                    outputStream = new PrintStream(socket.getOutputStream(), true);
                    in = new Scanner(socket.getInputStream());

                    // TODO: Selvitä, miksi tämä ei toimi!!!
                    outputStream.println(user.getUsername());

                } catch (IOException e) {
                    e.printStackTrace();
                }


                while (true) {

                    messages = in.nextLine();

                    Log.d("InputStream", messages);

                    String[] splitted = messages.split("¢", 4);
                    String senderId = splitted[0];
                    String sender = splitted[1];
                    String timeOrNotficicationInfo = splitted[2];
                    String justMessage = splitted[3];

                    if (!hasUserId && splitted[1].equals("NOTIFICATION") && splitted[0].equals("x")) {
                        ownUserId = splitted[2];
                        Log.d("OWN USER ID ", ownUserId);
                        hasUserId = true;
                    }

                        Message msg;
                        msg = new Message(senderId, sender, timeOrNotficicationInfo, justMessage);
                        UpdateView(msg);


                }
            }
        }).start();

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final String s = msgField.getText().toString();

                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        try {
                            outputStream = new PrintStream(socket.getOutputStream(), true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.d("MESSAGE: ", s);

                        outputStream.println(s);
                    }
                }).start();
                msgField.getText().clear();

            }
        });

    }

    private void UpdateView(final Message msg) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(msg);
                msgListAdapter.notifyDataSetChanged();
                messagesView.scrollToPosition(messageList.size()-1);

            }
        });
    }


    public String getUserId() {
        return ownUserId;
    }
}
