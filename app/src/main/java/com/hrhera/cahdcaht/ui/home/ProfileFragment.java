package com.hrhera.cahdcaht.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.hrhera.cahdcaht.data.model.User;
import com.hrhera.cahdcaht.databinding.FragmentProfileBinding;
import com.hrhera.cahdcaht.ui.MainActivity;
import com.hrhera.cahdcaht.utl.CircleTransform;
import com.hrhera.cahdcaht.utl.DataMannger;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    FragmentProfileBinding xmlFile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        xmlFile = FragmentProfileBinding.inflate(inflater, container, false);

        User user = DataMannger.getInstance().getLoginUser();

        xmlFile.userNAme.setText(user.getName());
        xmlFile.userBio.setText(user.getBio());
        Picasso.get().load(DataMannger.getInstance().coversPhoto[user.getCoverPhotoID()]).centerCrop().fit().transform(new CircleTransform()).into(xmlFile.coverPhoto);
        Picasso.get().load(DataMannger.getInstance().userIcons[user.getUserIconId()]).centerCrop().fit().transform(new CircleTransform()).into(xmlFile.userPhoto);

        xmlFile.coverPhoto.setOnClickListener(v -> {
            ((MainActivity) getActivity()).attachFrag(new EditProfileFragment());
        });

        return xmlFile.getRoot();
    }
}