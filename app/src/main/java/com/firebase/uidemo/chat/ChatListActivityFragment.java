package com.firebase.uidemo.chat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.uidemo.R;
import com.firebase.uidemo.chat.adapter.ChatListAdapter;
import com.firebase.uidemo.chat.model.UserChat;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChatListActivityFragment extends Fragment {


    private ChatListAdapter mChatListAdapter;

    public ChatListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat_list, container, false);

        mChatListAdapter = new ChatListAdapter(getActivity(), R.layout.fragment_chat_list, new ArrayList<UserChat>());
        mChatListAdapter.setNotifyOnChange(true);

        ListView listView = (ListView) rootView.findViewById(R.id.chatListView);
        listView.setAdapter(mChatListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        return rootView;
    }

    public void setAll(List<UserChat> userChats) {
        mChatListAdapter.addAll(userChats);
    }
}
