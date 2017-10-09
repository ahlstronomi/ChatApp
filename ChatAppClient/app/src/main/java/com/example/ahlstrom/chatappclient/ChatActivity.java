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

/**
 * Created by Ahlstrom
 */

public class ChatActivity extends AppCompatActivity {

    private Button btn;
    private EditText msgField;
    private Socket socket;
    private RecyclerView messagesView;

    private int socketNum = 1337;
    private String ipNum = "10.0.2.2";
    private String messages;
    private boolean hasUserId = false;
    private InputStream inputStream;
    private PrintStream outputStream;
    private Scanner in;
    private List<Message> messageList;
    private MessageListAdapter msgListAdapter;
    private String usrName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);

        btn = (Button) findViewById(R.id.button_chatbox_send);
        msgField = (EditText) findViewById(R.id.edittext_chatbox);
        messagesView = (RecyclerView) findViewById(R.id.reyclerview_message_list);

        messageList = new ArrayList<>();
        msgListAdapter = new MessageListAdapter(this, messageList);
        messagesView.setAdapter(msgListAdapter);

        messagesView.setLayoutManager(new LinearLayoutManager(this));

        // Get information from the LoginActivity
        Intent intent = getIntent();
        ipNum = intent.getStringExtra("ipNum");
        usrName = intent.getStringExtra("usrName");

        // Socket & message receiver thread. This thread opens the connection and listens the input stream for messages.
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    socket = new Socket(ipNum, socketNum);
                    outputStream = new PrintStream(socket.getOutputStream(), true);
                    in = new Scanner(socket.getInputStream());

                    // Log In
                    outputStream.println(usrName);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                while (true) {

                    messages = in.nextLine();
                    Log.d("Complete scanner in: ", messages);

                    if (!messages.isEmpty()) {

                        // Listen the clients own user id from the login notification, then never enters this If again.
                        if (!hasUserId) {
                            String[] splitted = messages.split("¢", 4);
                            if(splitted[1].equals("NOTIFICATION") && splitted[0].equals("x")) {
                                String timeOrNotificationInfo = splitted[2];
                                msgListAdapter.setOwnUserId(timeOrNotificationInfo);
                                hasUserId = true;
                            }
                        }

                        Message msg = new Message(messages);
                        UpdateView(msg);

                    }
                }
            }
        }).start();

        // Sending messages. Start a new thread for every new message sent
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
                        outputStream.println(s);
                    }
                }).start();
                msgField.getText().clear();
            }
        });
    }

    // Update UI with cool bubbles
    private void UpdateView(final Message msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(msg);
                msgListAdapter.notifyDataSetChanged();
                messagesView.scrollToPosition(messageList.size() - 1);

            }
        });
    }

}
