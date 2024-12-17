package com.itsrizzoli.wikiava.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.itsrizzoli.wikiava.fragments.HomeFragment;
import com.itsrizzoli.wikiava.fragments.NuovaChiavataFragment;
import com.itsrizzoli.wikiava.fragments.PersoneFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new NuovaChiavataFragment();
            case 2:
                return new PersoneFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}