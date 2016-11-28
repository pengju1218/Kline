package com.csdn.kline;

import android.view.View;
import android.widget.ImageView;

import com.csdn.kline.base.BaseActivity;
import com.csdn.kline.trans.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/23.
 */

public class MainAcitity extends BaseActivity {
    private android.widget.ImageView loadroundimg;
    private String img_url = "http://d.hiphotos.baidu.com/image/pic/item/2e2eb9389b504fc2065e2bd2e1dde71191ef6de0.jpg";

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        this.loadroundimg = (ImageView) findViewById(R.id.load_round_img);
        Picasso.with(MainAcitity.this).load(img_url)
                .transform(new CircleTransform(MainAcitity.this)).into(loadroundimg);
        loadroundimg.setOnClickListener(this);

    }

    @Override
    public void widgetClick(View v) {

        if (v.getId() == R.id.load_round_img) {

            Map<String, Object> map = new HashMap<>();
            map.put("ok", "我们都是中国人");
            map.put("intel", 200);
            startParamesActiviyt(Test.class, map);
        }
    }
}
