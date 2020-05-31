package com.yanturin.oneword.ui.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yanturin.oneword.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class SettingFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_setting, container, false);

        return root;
    }
}
