package com.example.mangxahoigump.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PagerViewAdapter extends FragmentStateAdapter {


    public PagerViewAdapter(@NonNull FragmentActivity fragment) {
        super(fragment);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new FriendFragment();
            case 2:
                return new NotificationFragment();
            case 3:
                return new MenuFragment();
            default:
                return new HomeFragment();
        }
    }



    @Override
    public int getItemCount() {
        return 4;
    }
}
