package com.example.anhtuan.quanlyphongtro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimTro extends AppCompatActivity {

    @BindView(R.id.lnl_dangtin_timtro)
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_tro);
        ButterKnife.bind(this);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimTro.this, Detail_PhongTro.class);
                startActivity(intent);
            }
        });
    }
}
