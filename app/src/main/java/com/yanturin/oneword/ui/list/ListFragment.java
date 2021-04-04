package com.yanturin.oneword.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.yanturin.oneword.R;
import com.yanturin.oneword.ui.common.ListFavoriteFragmentPagerAdapter;

public class ListFragment extends Fragment {
    private PagerAdapter _pagerAdapter;
    private View _view;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_list_or_favorite, container, false);

        ViewPager pager = _view.findViewById(R.id.pagerFavorite);
        _pagerAdapter = new ListFavoriteFragmentPagerAdapter(getFragmentManager(), _view.getContext());
        pager.setAdapter(_pagerAdapter);
        pager.setCurrentItem(0);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                _pagerAdapter.notifyDataSetChanged();
                if(position == 0){
                    Navigation.findNavController(_view).navigate(R.id.nav_list);
                }else{
                    Navigation.findNavController(_view).navigate(R.id.nav_favorite);
                }
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        TabLayout tabLayout = _view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

        return _view;
    }

}