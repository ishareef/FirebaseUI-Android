package com.firebase.uidemo.chat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.uidemo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContactListActivityFragment extends Fragment {

    private ArrayAdapter<String> mContactListAdapter;

    public ContactListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contacts_list, container, false);

        //Hard code for now, till values can be populated
        String[] names = new String[] {"Kshira Nadarajan", "Asima Shareef"};

        mContactListAdapter =
                new ArrayAdapter<String>(
                        getActivity(),
                        R.layout.contact_item,
                        R.id.list_item_contact_textview,
                        new ArrayList<String>(Arrays.asList(names)));

        mContactListAdapter.setNotifyOnChange(true);

        ListView listView = (ListView) rootView.findViewById(R.id.contactListView);
        listView.setAdapter(mContactListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        return rootView;
    }

    public void setAll(List<String> contacts) {
        mContactListAdapter.addAll(contacts);
    }

}
