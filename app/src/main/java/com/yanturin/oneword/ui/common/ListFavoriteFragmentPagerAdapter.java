package com.yanturin.oneword.ui.common;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
//класс для скроллинга между избранными и изученными словами
public class ListFavoriteFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private Context _context;
    public ListFavoriteFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        _context = context;
    }

    public int getItemPosition (Object object) { return POSITION_NONE; }

    @Override
    public Fragment getItem(int position) {
        return ListFavoriteFragment.newInstance(position, _context);
    }

    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "Изученные слова";
        }else{
            return "Избранное";
        }
    }
}
