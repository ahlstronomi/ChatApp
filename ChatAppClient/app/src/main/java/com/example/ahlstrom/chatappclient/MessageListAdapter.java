package com.example.ahlstrom.chatappclient;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.ahlstrom.chatappclient.ChatActivity.NOTIFICATION;
import static com.example.ahlstrom.chatappclient.ChatActivity.RECEIVED;
import static com.example.ahlstrom.chatappclient.ChatActivity.SENT;

/**
 * Created by Ahlstrom on 5.10.2017.
 */

public class MessageListAdapter extends RecyclerView.Adapter {

    List<Message> msgList;
    private Context msgContext;

    ChatActivity chtAct = new ChatActivity();


    public MessageListAdapter(Context context, List<Message> messageList){
        this.msgContext = context;
        this.msgList = messageList;
    }


    public int getItemViewType(int position) {
        Message message = msgList.get(position);
        if (message.getSenderId().equals(chtAct.getUserId())) {
            return SENT;
        } else if(message.getSenderId().equals("x")){
            return NOTIFICATION;
        } else{
            return RECEIVED;
        }
    }

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
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

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

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    // PRIVATE CLASSES -----------------------------------------------------------------------------

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

        TextView messageText, timeText, nameText;

        public SentMessageHolder(View itemView) {

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
