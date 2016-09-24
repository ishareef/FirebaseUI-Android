package com.firebase.uidemo.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.uidemo.R;
import com.firebase.uidemo.chat.adapter.ChatListAdapter;
import com.firebase.uidemo.chat.model.UserChat;
import com.firebase.uidemo.database.ChatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatListActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener, ValueEventListener {

    private final String TAG = ChatListActivity.class.getSimpleName();

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ChatListAdapter mChatListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(this);

        //keep reference to database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mChatListAdapter = new ChatListAdapter(this, R.layout.activity_chat_list, new ArrayList<UserChat>());
        mChatListAdapter.setNotifyOnChange(true);
        ListView listView = (ListView) findViewById(R.id.chatListView);
        listView.setAdapter(mChatListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserChat userChat = (UserChat) mChatListAdapter.getItem(position);
                Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
                intent.putExtra("chat_id", userChat.getKey());
                intent.putExtra("chat_title", userChat.getTitle());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if (mAuth.getCurrentUser() != null) {
            FirebaseUser user = mAuth.getCurrentUser();
            Log.e(TAG, "User logged in: " + user.getEmail());
            mDatabase.child("users").child(user.getUid()).child("user_chats")
                    .addListenerForSingleValueEvent(this);
        } else {
            //TODO: redirect to sign-in activity
            //Create Intent and launch activity here;
            Log.e(TAG, "Error! No user is signed in!");
        }
    }

    //TODO: should this be on another thread? Is it already on another thread?
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        List<UserChat> userChats = new ArrayList<>();
        if (dataSnapshot == null || dataSnapshot.getValue() == null || !dataSnapshot.hasChildren()) {
            Log.e(TAG, "User chats query returned empty snapshot");
            userChats.add(new UserChat("Kshira Nadarajan", "Hi sweetie pie, how are you?"));
            userChats.add(new UserChat("Nadeem Ahmed", "This is really cool stuff, bro"));
            userChats.add(new UserChat("Mom", "what's for dinner?"));
            userChats.add(new UserChat("Dad", "Why is this taking so long!"));
            userChats.add(new UserChat("Boss", "You an idiot. Hurry up"));
            userChats.add(new UserChat("Book Club", "Its the best book that I have ever read!"));
            //create a child for this user

        }
        Log.e(TAG, "Found dataSnapshot for users");
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            UserChat userChat = snapshot.getValue(UserChat.class);
            if (userChat != null) {
                userChats.add(userChat);
            }
        }
        mChatListAdapter.addAll(userChats);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        //TODO: add detailed error logging as well
        Log.e(TAG, "error while accesssing database: " + databaseError.getMessage());
    }
}
