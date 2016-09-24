package com.firebase.uidemo.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.firebase.uidemo.R;
import com.firebase.uidemo.chat.model.UserChat;

import java.util.List;

public class ChatListAdapter extends ArrayAdapter {

    List<UserChat> mItems;
    LayoutInflater mInflate;

    public ChatListAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        mInflate = LayoutInflater.from(context);
        mItems = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View newView;
        if (convertView == null) {
            newView = mInflate.inflate(R.layout.chat_list_item, parent, false);
        } else {
            newView = convertView;
        }
        UserChat item = mItems.get(position);
        TextView titleView = (TextView) newView.findViewById(R.id.chatTitle);
        titleView.setText(item.getTitle());
        TextView messageView = (TextView) newView.findViewById(R.id.chatLastMessage);
        messageView.setText(item.getLastMessage());
        return newView;
    }
}
