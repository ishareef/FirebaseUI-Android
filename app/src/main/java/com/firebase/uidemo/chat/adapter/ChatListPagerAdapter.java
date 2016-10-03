package com.firebase.uidemo.chat.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.firebase.uidemo.chat.ChatListActivityFragment;

/**
 * Created by ishareef on 10/1/16.
 */
public class ChatListPagerAdapter extends FragmentPagerAdapter {

    //TODO: can make this icons eventually
    private static final String[] tabTitles = new String[]{"chats","contacts"};
    private Context context;

    public ChatListPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new ChatListActivityFragment();
        } else {

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
}

