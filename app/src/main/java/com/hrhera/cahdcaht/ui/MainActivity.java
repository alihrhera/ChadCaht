package com.hrhera.cahdcaht.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hrhera.cahdcaht.R;
import com.hrhera.cahdcaht.ui.home.HomeFrag;
import com.hrhera.cahdcaht.ui.login.LogeinFrag;
import com.hrhera.cahdcaht.utl.DataMannger;
import com.hrhera.cahdcaht.data.model.AppMethod;
import com.hrhera.cahdcaht.data.model.OnUserData;
import com.hrhera.cahdcaht.data.model.User;

public class MainActivity extends AppCompatActivity implements AppMethod {

    private Dialog loadingView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hidActionBar();
        OnUserData onUserData = new OnUserData() {
            @Override
            public void onDone() {
                attachFrag(new HomeFrag());
            }

            @Override
            public void onFail(String Msg) {
                Toast.makeText(MainActivity.this, Msg, Toast.LENGTH_SHORT).show();
                attachFrag(new LogeinFrag());
            }
        };


        loadingView=new Dialog(MainActivity.this){
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.dialog_loading);
                setCancelable(false);
            }
        };


        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    if (DataMannger.getInstance().isRemember(MainActivity.this)) {
                        String phone = DataMannger.getInstance().getPhone();
                        String password = DataMannger.getInstance().getPass();
                        User user = new User();
                        user.setPhone(phone);
                        user.setPassword(password);
                        DataMannger.getInstance().startLogin(user, onUserData, MainActivity.this);
                        return;
                    }
                    sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                runOnUiThread(() -> attachFrag(new LogeinFrag()));
            }
        }.start();


        DataMannger.getInstance().setAppMethod(this);

    }


    public void attachFrag(Fragment fragment) {
        findViewById(R.id.splashScreen).setVisibility(View.GONE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_conttiner, fragment).commit();

    }


    public void setTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
            actionBar.setTitle(title);
        }


    }

    public void hidActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }


    public void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
    }


    @Override
    public void loading() {
        if (loadingView != null) {
            loadingView.show();
        }
    }

    @Override
    public void normal() {
        if (loadingView != null && loadingView.isShowing()) {
            loadingView.dismiss();
        }
    }


}
