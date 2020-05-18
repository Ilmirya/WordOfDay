package com.yanturin.oneword.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.yanturin.oneword.R;
import com.yanturin.oneword.ui.common.ListFavoriteFragmentPagerAdapter;

public class FavoriteFragment extends Fragment {
    PagerAdapter pagerAdapter;
    View root;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_list_or_favorite, container, false);

        ViewPager pager = root.findViewById(R.id.pagerFavorite);

        pagerAdapter = new ListFavoriteFragmentPagerAdapter(getFragmentManager(), root.getContext());
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(1);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                pagerAdapter.notifyDataSetChanged();
                if(position == 0){
                    Navigation.findNavController(root).navigate(R.id.nav_list);
                }else{
                    Navigation.findNavController(root).navigate(R.id.nav_favorite);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        TabLayout tabLayout = root.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
        return root;
    }
}