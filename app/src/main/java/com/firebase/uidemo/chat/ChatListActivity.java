package com.firebase.uidemo.chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import com.firebase.uidemo.R;
import com.firebase.uidemo.chat.adapter.ChatListPagerAdapter;
import com.firebase.uidemo.chat.model.User;
import com.firebase.uidemo.chat.model.UserChat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatListActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener {

    private final String TAG = ChatListActivity.class.getSimpleName();

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ChatListActivityFragment mChatListFragment;
    private ContactListActivityFragment mContactListFragment;
    private boolean mLoggedIn;

    //TODO: change all error logging to debug logging where applicable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        //TODO: enable this for better compatibility
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(this);

        //keep reference to database
        mDatabase = FirebaseDatabase.getInstance().getReference();


        /*
        * Set in fragment
        *
        * mChatListAdapter = new ChatListAdapter(this, R.layout.activity_chat_list, new ArrayList<UserChat>());
        mChatListAdapter.setNotifyOnChange(true);
        ListView listView = (ListView) findViewById(R.id.chatListView);
        listView.setAdapter(mChatListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/

        mChatListFragment = new ChatListActivityFragment();
        mContactListFragment = new ContactListActivityFragment();

        ChatListPagerAdapter adapter = new ChatListPagerAdapter(getSupportFragmentManager(),
                ChatListActivity.this);

        adapter.setChatListFragment(mChatListFragment);
        adapter.setContactListFragment(mContactListFragment);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_chat_list, menu);
        return true;
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if (mAuth.getCurrentUser() != null && !mLoggedIn) {
            FirebaseUser user = mAuth.getCurrentUser();
            Log.e(TAG, "User logged in: " + user.getEmail());
            //TODO: listeners can be moved to their own classes
            mDatabase.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot == null
                            || dataSnapshot.getValue() == null
                            || !dataSnapshot.hasChildren()) {
                        //first time user
                        setupFirstTimeUser();
                    } else {
                        populateChatList(dataSnapshot.child("chats"));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //TODO: add detailed error logging as well
                    Log.e(TAG, "error while accessing database: " + databaseError.getMessage());
                }
            });

            mDatabase.child("user_list").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            mLoggedIn = true;
        } else {
            //TODO: redirect to sign-in activity
            //Create Intent and launch activity here;
            Log.e(TAG, "Error! No user is signed in!");
        }
    }

    public void populateChatList(DataSnapshot dataSnapshot) {
        Log.e(TAG, "Found existing chat list for user");
        List<UserChat> userChats = new ArrayList<>();
        if (dataSnapshot != null) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                UserChat userChat = snapshot.getValue(UserChat.class);
                if (userChat != null) {
                    userChats.add(userChat);
                }
            }
        }
        if (userChats.size() < 2) {
            userChats.add(new UserChat("1", "Donald Drumpf", "I'm a major moron"));
        }
        mChatListFragment.setAll(userChats);
    }


    public void setupFirstTimeUser() {
        //TODO: use completion listeners here to handle errors
        final FirebaseUser user = mAuth.getCurrentUser();
        Log.e(TAG, "Setting up user for first time use: " + user.getUid() + " " + user.getEmail());
        //Add entry in user database
        mDatabase.child("users").child(user.getUid()).setValue(new User(user.getUid(), user.getDisplayName(), new ArrayList<UserChat>()));
        //Add entry in user_list database
        mDatabase.child("user_list").child(user.getUid()).setValue(user.getEmail());
    }

    /*@Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        List<UserChat> userChats = new ArrayList<>();
        if (dataSnapshot == null || dataSnapshot.getValue() == null || !dataSnapshot.hasChildren()) {
            Log.e(TAG, "User chats query returned empty snapshot");
            //TODO: HACK!! Remove when actual chat can be added
            final FirebaseUser user = mAuth.getCurrentUser();

            mDatabase.child("chats").push().setValue(new Chat("Kshira", "Hello sweetie pie!"), new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Log.e(TAG, "Error while creating chat object: " + databaseError.getMessage());
                    } else {
                        String key = databaseReference.getKey();
                        mDatabase.child("chat_messages").child(key).push().setValue(new ChatMessage(user.getUid(), user.getDisplayName(), "Hello sweetie pie!"));

                        List<UserChat> remoteChats = new ArrayList<>();
                        remoteChats.add(new UserChat(key, "Kshira", "Hello sweetie pie!"));
                        mDatabase.child("users").child(user.getUid()).setValue(new User(user.getUid(), user.getDisplayName(), remoteChats));
                    }
                }
            });
        } else {
        }
        mChatListAdapter.addAll(userChats);
    }*/
}
