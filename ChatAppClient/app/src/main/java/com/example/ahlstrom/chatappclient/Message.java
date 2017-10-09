package com.example.ahlstrom.chatappclient;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by Ahlstrom on 5.10.2017.
 * <p>
 * Class for all chat messages, sent, received and notifications.
 */


public class Message {

    private String sender;
    private String senderId;
    private String timeOrNotificationInfo;
    private String msg;


    public Message(String scannerIn) {

        String[] splitted = scannerIn.split("¢", 4);
        this.senderId = splitted[0];
        this.sender = splitted[1];
        this.timeOrNotificationInfo = splitted[2];
        this.msg = splitted[3];

    }


    public String getSenderId() {
        return this.senderId;
    }

    public String getSender() {
        return this.sender;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getTime() {
        return this.timeOrNotificationInfo;
    }

}
