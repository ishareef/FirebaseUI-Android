package com.firebase.uidemo.chat.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.firebase.uidemo.chat.ChatListActivityFragment;
import com.firebase.uidemo.chat.ContactListActivityFragment;

/**
 * Created by ishareef on 10/1/16.
 */
public class ChatListPagerAdapter extends FragmentPagerAdapter {

    //TODO: can make this icons eventually
    private static final String[] tabTitles = new String[]{"chats","contacts"};
    private Context context;

    private ChatListActivityFragment mchatListFragment;
    private ContactListActivityFragment mContactListFragment;

    public ChatListPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return mchatListFragment;
        } else {
            return mContactListFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    public void setChatListFragment(ChatListActivityFragment chatListFragment) {
        mchatListFragment = chatListFragment;
    }

    public void setContactListFragment(ContactListActivityFragment contactListFragment) {
        mContactListFragment = contactListFragment;
    }
}

