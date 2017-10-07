package com.example.ahlstrom.chatapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Ahlstrom on 5.10.2017.
 */


public class Message {

    private String sender;
    private String senderId;
    private String timeOrNotificationInfo;
    private String msg;
    private int type;

    public Message(String sndrId, String sndr, String timeStampOrNotifivationInfo, String message) {

        this.senderId = sndrId;
        this.sender = sndr;
        this.timeOrNotificationInfo = timeStampOrNotifivationInfo;
        this.msg = message;
        this.type = 0;

    }

    public String getSenderId(){
        return this.senderId;
    }

    public String getSender(){
        return this.sender;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getTime() {
        return this.timeOrNotificationInfo;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType(){
        return this.type;
    }
}
