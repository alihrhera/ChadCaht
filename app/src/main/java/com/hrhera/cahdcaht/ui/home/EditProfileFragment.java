package com.hrhera.cahdcaht.ui.home;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hrhera.cahdcaht.R;
import com.hrhera.cahdcaht.data.model.OnItmeClick;
import com.hrhera.cahdcaht.data.model.User;
import com.hrhera.cahdcaht.databinding.FragmentEditProfileBinding;
import com.hrhera.cahdcaht.ui.adapters.IconsAdapter;
import com.hrhera.cahdcaht.ui.adapters.MSpinnerAdapter;
import com.hrhera.cahdcaht.utl.CircleTransform;
import com.hrhera.cahdcaht.utl.DataMannger;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class EditProfileFragment extends Fragment {


    public EditProfileFragment() {
        // Required empty public constructor
    }

    FragmentEditProfileBinding xmlFile;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        xmlFile = FragmentEditProfileBinding.inflate(inflater, container, false);

        user = DataMannger.getInstance().getLoginUser();

        xmlFile.getUserNAme.setText(user.getName());
        xmlFile.getUserBio.setText(user.getBio());
        Picasso.get().load(DataMannger.getInstance().userIcons[user.getUserIconId()]).centerCrop().fit().transform(new CircleTransform()).into(xmlFile.getUserPhoto);

        MSpinnerAdapter adapter = new MSpinnerAdapter(getActivity(), android.R.layout.simple_list_item_1, Arrays.asList(DataMannger.getInstance().coversPhoto));

        xmlFile.getCoverPhoto.setAdapter(adapter);
        xmlFile.getCoverPhoto.setSelection(user.getCoverPhotoID());
        xmlFile.getCoverPhoto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user.setCoverPhotoID(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        xmlFile.getUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIconDialog();
            }
        });


        xmlFile.save.setOnClickListener(v -> {
            user.setName(xmlFile.getUserNAme.getText().toString());
            user.setBio(xmlFile.getUserBio.getText().toString());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("coverPhotoID", user.getCoverPhotoID());
            map.put("userIconId", user.getUserIconId());
            map.put("name", user.getName());
            map.put("bio", user.getBio());
            DataMannger.getInstance().updateProfile(user.getId(), map);
        });


        return xmlFile.getRoot();
    }


    private void showIconDialog() {
        new Dialog(getActivity()) {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                setContentView(R.layout.dialog_user_icons);
                Window window = getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                RecyclerView userIconsRecycler = findViewById(R.id.userIconsRecycler);
                IconsAdapter adapter = new IconsAdapter();
                userIconsRecycler.setLayoutManager(new GridLayoutManager(getContext(), 5));
                userIconsRecycler.setAdapter(adapter);
                adapter.setDataList(Arrays.asList(DataMannger.getInstance().userIcons));
                adapter.setOnClick(object -> {
                    user.setUserIconId(Integer.parseInt(object.toString()));
                    Picasso.get().load(DataMannger.getInstance().userIcons[user.getUserIconId()]).centerCrop().fit().transform(new CircleTransform()).into(xmlFile.getUserPhoto);
                    dismiss();
                });

            }
        }.show();


    }


}