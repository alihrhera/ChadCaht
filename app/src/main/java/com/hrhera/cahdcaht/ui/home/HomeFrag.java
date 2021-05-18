package com.hrhera.cahdcaht.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.hrhera.cahdcaht.R;
import com.hrhera.cahdcaht.ui.MainActivity;
import com.hrhera.cahdcaht.ui.home.user_fragments.ContactsFragment;
import com.hrhera.cahdcaht.ui.home.user_fragments.GroupsFragment;
import com.hrhera.cahdcaht.ui.home.user_fragments.UserChat;
import com.hrhera.cahdcaht.ui.login.LogeinFrag;
import com.hrhera.cahdcaht.utl.DataMannger;

import java.util.ArrayList;
import java.util.List;


public class HomeFrag extends Fragment {


    public HomeFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    MainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = (MainActivity) getActivity();
        assert activity != null;
        activity.showActionBar();
        View root = inflater.inflate(R.layout.frag_home, container, false);
        ViewPager viewPager = root.findViewById(R.id.viewPager);
        TabLayout tabLayout = root.findViewById(R.id.tapLayout);
        tabLayout.setupWithViewPager(viewPager);
        MViewPagerAdapter adapter = new MViewPagerAdapter(getChildFragmentManager());
        List<ViewSetup> list = new ArrayList<>();
        list.add(new ViewSetup(new UserChat(), "Chats"));
        list.add(new ViewSetup(new GroupsFragment(), "Groups"));
        adapter.setList(list);
        viewPager.setAdapter(adapter);

        return root;


    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cont) {
            activity.attachFrag(new ContactsFragment());
        } else if (id == R.id.logOut) {
            DataMannger.getInstance().setRememberMeStatus(activity, false);
            activity.attachFrag(new LogeinFrag());

        } else if (id == R.id.profile) {
            activity.attachFrag(new ProfileFragment());
        }

        return super.onOptionsItemSelected(item);
    }

    class MViewPagerAdapter extends FragmentStatePagerAdapter {
        private List<ViewSetup> list = new ArrayList<>();

        public void setList(List<ViewSetup> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        private MViewPagerAdapter(FragmentManager manager) {
            super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            return list.get(position).getFragment();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position).getTitle();
        }
    }

    static class ViewSetup {
        private Fragment fragment;
        private String title;

        public ViewSetup(Fragment fragment, String title) {
            this.fragment = fragment;
            this.title = title;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public String getTitle() {
            return title;
        }
    }


}
