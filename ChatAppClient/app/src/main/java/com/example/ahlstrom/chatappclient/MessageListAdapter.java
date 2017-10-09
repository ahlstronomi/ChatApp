package com.example.ahlstrom.chatappclient;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ahlstrom on 5.10.2017.
 * RecyclerView Adapter.
 */

public class MessageListAdapter extends RecyclerView.Adapter {

    public static final int SENT = 1;
    public static final int RECEIVED = 2;
    public static final int NOTIFICATION = 0;

    private String ownUserId;
    private Context msgContext;
    private List<Message> msgList;

    public MessageListAdapter(Context context, List<Message> messageList) {
        msgContext = context;
        msgList = messageList;
    }

    // Set UserID to recognize received messages sender
    public void setOwnUserId(String ownUserId) {
        this.ownUserId = ownUserId;
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    // Determine the type of the received message
    public int getItemViewType(int position) {

        Message message = msgList.get(position);

        if (message.getSenderId().equals(ownUserId)) {
            return SENT;
        } else if (message.getSenderId().equals("x")) {
            return NOTIFICATION;
        } else {
            return RECEIVED;
        }
    }

    // Inflate the message with correct layout according to the type of the message
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        if (viewType == SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        } else if (viewType == NOTIFICATION) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_notification, parent, false);
            return new NotificationHolder(view);
        }

        return null;
    }

    // Message is passed to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Message message = (Message) msgList.get(position);

        switch (holder.getItemViewType()) {
            case SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
                break;
            case NOTIFICATION:
                ((NotificationHolder) holder).bind(message);
        }

    }


    // These clases are used to hold the message with a correct layouts
    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {

        TextView messageText, timeText, nameText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            nameText = (TextView) itemView.findViewById(R.id.text_message_name);
        }

        void bind(Message message) {
            messageText.setText(message.getMsg());
            timeText.setText(message.getTime());
            nameText.setText(message.getSender());
        }
    }


    private class SentMessageHolder extends RecyclerView.ViewHolder {

        TextView messageText, timeText;

        public SentMessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        }

        void bind(Message message) {
            messageText.setText(message.getMsg());
            timeText.setText(message.getTime());
        }

    }

    private class NotificationHolder extends RecyclerView.ViewHolder {

        TextView messageText;

        public NotificationHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
        }

        void bind(Message message) {
            messageText.setText(message.getMsg());
        }


    }

}
