package com.kdc.yongheshen.keduchi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity
{

    TextView mTvHorizontalScale;
    TextView mTvVerticalScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Config.VALUE = 5000;
        mTvHorizontalScale = (TextView) findViewById(R.id.horizontalScaleValue);

        HorizontalScaleScrollView scaleScrollView = (HorizontalScaleScrollView) findViewById(R.id.horizontalScale);
        scaleScrollView.setOnScrollListener(new HorizontalScaleScrollView.OnScrollListener() {
            @Override
            public void onScaleScroll(int scale) {
                mTvHorizontalScale.setText("" + (scale*1000+Config.VALUE));
            }


        });
        scaleScrollView.ScrollTo(13);
    }
}

