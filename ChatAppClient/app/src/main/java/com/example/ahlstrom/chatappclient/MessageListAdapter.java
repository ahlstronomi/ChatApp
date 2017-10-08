package com.example.ahlstrom.chatappclient;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ahlstrom on 5.10.2017.
 */

public class MessageListAdapter extends RecyclerView.Adapter {

    public static final int SENT = 1;
    public static final int RECEIVED = 2;
    public static final int NOTIFICATION = 0;

    private Context msgContext;
    List<Message> msgList;

    ChatActivity chtAct = new ChatActivity();


    public MessageListAdapter(Context context, List<Message> messageList){
        Log.d("MessageListAdapter: ", "Creating");
        msgContext = context;
        msgList = messageList;
        Log.d("MessageListAdapter: ", "Created");
    }


    @Override
    public int getItemCount() {
        return msgList.size();
    }


    public int getItemViewType(int position) {
        Log.d("getItemViewType: ", "IN HERE");

        Message message = msgList.get(position);

        if (message.getSenderId().equals(chtAct.getUserId())) {
            Log.d("getItemViewType: ", "SENT");
            return SENT;
        } else if(message.getSenderId().equals("x")){
            Log.d("getItemViewType ", "NOTIFICATION");
            return NOTIFICATION;
        } else{
            Log.d("getItemViewType ", "RECEIVED");
            return RECEIVED;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        Log.d("onCreateViewHolder: ", "IN HERE");

        if (viewType == SENT) {
            Log.d("onCreateViewHolder: ", "inflating SENT message");
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == RECEIVED) {
            Log.d("onCreateViewHolder: ", "inflating RECIVED message");
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        } else if (viewType == NOTIFICATION) {
            Log.d("onCreateViewHolder: ", "inflating NOTIFICATION message");
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_notification, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Log.d("onBindViewHolder: ", "IN HERE");

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



    // PRIVATE CLASSES -----------------------------------------------------------------------------

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            nameText = (TextView) itemView.findViewById(R.id.text_message_name);

            Log.d("ReceivedMessageHolder: ", "Created");

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

            Log.d("SentMessageHolder: ", "Created");

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

            Log.d("NotificationHolder: ", "Created");

        }

        void bind(Message message) {
            messageText.setText(message.getMsg());

        }


    }

}
