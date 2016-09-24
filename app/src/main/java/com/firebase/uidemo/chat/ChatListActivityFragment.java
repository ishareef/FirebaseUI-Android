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

    public ChatListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat_list, container, false);

        List<UserChat> listItems = new ArrayList<>();
        listItems.add(new UserChat("Kshira Nadarajan", "Hi sweetie pie, how are you?"));
        listItems.add(new UserChat("Nadeem Ahmed", "This is really cool stuff, bro"));
        listItems.add(new UserChat("Mom", "what's for dinner?"));
        listItems.add(new UserChat("Dad", "Why is this taking so long!"));
        listItems.add(new UserChat("Boss", "You an idiot. Hurry up"));
        listItems.add(new UserChat("Book Club", "Its the best book that I have ever read!"));

        ChatListAdapter adapter = new ChatListAdapter(getActivity(), R.layout.fragment_chat_list, listItems);

        ListView listView = (ListView) rootView.findViewById(R.id.chatListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        return rootView;
    }
}
