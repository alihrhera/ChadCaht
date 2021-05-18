package com.hrhera.cahdcaht.ui.home.view_fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hrhera.cahdcaht.R;
import com.hrhera.cahdcaht.data.model.AppUser;

import java.util.ArrayList;
import java.util.List;


public class ContactsFrag extends Fragment {


    public ContactsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.frag_contacttes_frage, container, false);

        checkPr();

        return root;
    }
    private final int permissionCode=500;
    void checkPr(){
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED){
            // u have the perm
            getContactList();
            return;
        }
        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},permissionCode);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==permissionCode&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            // u have the perm
            getContactList();

        }

    }

    List <AppUser>appUsersList=new ArrayList<>();

    private void getContactList() {
        ContentResolver cr = getContext().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                AppUser appUsers=new AppUser();
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

                appUsersList.add(appUsers);
            }
        }
        if (cur != null) {
            cur.close();
        }
        Log.e("Test",appUsersList.size()+""+ appUsersList.get(0).getName()+" "+appUsersList.get(0).getPhones().get(0));
    }


}