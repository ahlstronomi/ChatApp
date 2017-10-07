package com.example.ahlstrom.chatappclient;

/**
 * Created by Ahlstrom on 5.10.2017.
 */


public class User {

    private String username;

    public User(String setUsername){
        this.username = setUsername;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String u){
        this.username = u;
    }

}
