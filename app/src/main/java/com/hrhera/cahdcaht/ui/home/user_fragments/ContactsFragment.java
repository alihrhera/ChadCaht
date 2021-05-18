package com.hrhera.cahdcaht.ui.home.user_fragments;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hrhera.cahdcaht.R;
import com.hrhera.cahdcaht.utl.DataMannger;
import com.hrhera.cahdcaht.ui.adapters.UserForChatAdapter;
import com.hrhera.cahdcaht.data.model.AppUser;
import com.hrhera.cahdcaht.data.model.ChatUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ContactsFragment extends Fragment {

    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_contacts, container, false);
        getAllContacts();
        RecyclerView recyclerView = root.findViewById(R.id.allContacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        UserForChatAdapter adapter = new UserForChatAdapter();
        recyclerView.setAdapter(adapter);

        DataMannger.getInstance().getAllUserForChat(list -> {
            adapter.setList((List<ChatUser>) list);

        });


        return root;


    }


    final int RequestCode = 1000;

    private void getAllContacts() {
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {

            /// you have permission
            loadContacts();
            return;
        }

        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, RequestCode);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RequestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                /// you have permission
                getAllContacts();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    List<AppUser> appUserList = new ArrayList<>();

    private void loadContacts() {
        appUserList = new ArrayList<>();
        ContentResolver cr = Objects.requireNonNull(getContext()).getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur.moveToNext()) {
                AppUser appUsers = new AppUser();
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                appUsers.setName(name);
                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        appUsers.getPhones().add(phoneNo);
                        //Log.i(TAG, "Name: " + name);
                        //Log.i(TAG, "Phone Number: " + phoneNo);
                    }
                    pCur.close();
                }
                appUserList.add(appUsers);

            }
        }
        DataMannger.getInstance().setAppUserList(appUserList);
        if (cur != null) {
            cur.close();
        }

    }


}