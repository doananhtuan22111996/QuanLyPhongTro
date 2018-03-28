package com.example.anhtuan.quanlyphongtro.detailpurchase.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.anhtuan.quanlyphongtro.R;
import com.example.anhtuan.quanlyphongtro.model.Purchase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailPurchaseViewPagerAdapter extends FragmentPagerAdapter {


    public DetailPurchaseViewPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        return FragmentImage.newIntance(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    public static class FragmentImage extends Fragment {

        private static Purchase purchase = new Purchase();
        private static List<String> image = new ArrayList<>();

        @BindView(R.id.rl_image_purchase)
        RelativeLayout relativeLayout;
        @BindView(R.id.img_detail_purchase)
        ImageView imgDetailPurchase;

        public static FragmentImage newIntance(int position) {
            image.add("https://anh.eva.vn//upload/2-2013/images/2013-04-25/1366856222-nhat-kim-anh-1.jpg");
            image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWes223ySFbjsH8zHeLHz1oEtQpsJ6AveR0Njq6Kk70QXD2IyQdQ");
            image.add("http://sohanews.sohacdn.com/2013/1380685959217.jpg");
            purchase.setImages(image);
            FragmentImage fragmentImage = new FragmentImage();
            Bundle bundle = new Bundle();
            bundle.putInt("IMAGE", position);
            fragmentImage.setArguments(bundle);
            return fragmentImage;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.items_image_fragpurchase, container, false);
            ButterKnife.bind(this, view);
            assert getArguments() != null;
            switch (getArguments().getInt("IMAGE")) {
                case 0:
                    Glide.with(view).load(purchase.getImages().get(0)).into(imgDetailPurchase);
                    break;
                case 1:
                    Glide.with(view).load(purchase.getImages().get(1)).into(imgDetailPurchase);
                    break;
                case 2:
                    Glide.with(view).load(purchase.getImages().get(2)).into(imgDetailPurchase);
                    break;
                default:
                    Glide.with(view).load(purchase.getImages().get(0)).into(imgDetailPurchase);
                    break;
            }
            return view;
        }

    }
}
