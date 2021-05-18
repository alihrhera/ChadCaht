package com.hrhera.cahdcaht.ui.home.register;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.hrhera.cahdcaht.R;
import com.hrhera.cahdcaht.ui.MainActivity;
import com.hrhera.cahdcaht.ui.login.LogeinFrag;
import com.hrhera.cahdcaht.utl.DataMannger;
import com.hrhera.cahdcaht.data.model.OnUserData;
import com.hrhera.cahdcaht.data.model.User;

import java.util.Calendar;

public class RegisterFrag extends Fragment implements OnUserData {

    private User user;
    EditText phone ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_register, container, false);

        ((MainActivity)getActivity()).hidActionBar();

        phone = root.findViewById(R.id.phone);
        EditText name = root.findViewById(R.id.name);
        EditText pass = root.findViewById(R.id.password);
        EditText cPass = root.findViewById(R.id.ConfirmPassword);


        root.findViewById(R.id.Done).setOnClickListener(v -> {
            user = new User();
            if (TextUtils.isEmpty(name.getText())) {
                name.setError(getString(R.string.this_is_requir));
                return;
            }
            String strName = name.getText().toString();
            if (TextUtils.isEmpty(phone.getText())) {
                phone.setError(getString(R.string.this_is_requir));
                return;
            }
            String strPhone = phone.getText().toString();

            if (TextUtils.isEmpty(pass.getText())) {
                pass.setError(getString(R.string.this_is_requir));
                return;
            }
            String strPass = pass.getText().toString();
            if (TextUtils.isEmpty(cPass.getText())) {
                cPass.setError(getString(R.string.this_is_requir));
            } else if (!strPass.equals(cPass.getText().toString())) {
                cPass.setError("Error -> Password and confirm must be Equal");
                return;
            }
            user.setId(strPhone);
            user.setJoinData(Calendar.getInstance().getTimeInMillis());
            user.setPhone(strPhone);
            user.setName(strName);
            user.setPassword(strPass);
            DataMannger.getInstance()
                    .CheckIfUser(user,this);

        });

        return root;
    }


    @Override
    public void onDone() {
        ((MainActivity)getActivity()).attachFrag(new LogeinFrag());
    }

    @Override
    public void onFail(String Msg) {
        phone.setError(Msg);
    }
}