package com.example.anhtuan.quanlyphongtro.adapter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.anhtuan.quanlyphongtro.R;

import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentImage.newIntance(position + 1);
    }

    @Override
    public int getCount() {
        return 3;
    }

    public static class FragmentImage extends Fragment {

        @BindView(R.id.rl_fragment_image)
        RelativeLayout relativeLayout;

        public static FragmentImage newIntance(int color) {
            FragmentImage fragmentImage = new FragmentImage();
            Bundle bundle = new Bundle();
            bundle.putInt("COLOR", color);
            fragmentImage.setArguments(bundle);
            return fragmentImage;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.items_image_phongtro_fragment, container, false);
            ButterKnife.bind(this, view);
            switch (getArguments().getInt("COLOR")) {
                case 1:
                    relativeLayout.setBackgroundColor(Color.GREEN);
                    break;
                case 2:
                    relativeLayout.setBackgroundColor(Color.RED);
                    break;
                case 3:
                    relativeLayout.setBackgroundColor(Color.YELLOW);
                    break;
                default:
                    relativeLayout.setBackgroundColor(Color.GREEN);
                    break;
            }
            return view;
        }
    }
}
