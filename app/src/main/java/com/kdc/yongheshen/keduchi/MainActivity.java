package com.kdc.yongheshen.keduchi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener
{

    TextView mTvHorizontalScale;
    private int tmp;
    HorizontalScaleScrollView scaleScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Config.VALUE = 5000;
        mTvHorizontalScale = (TextView) findViewById(R.id.horizontalScaleValue);

        scaleScrollView = (HorizontalScaleScrollView) findViewById(R.id.horizontalScale);
        scaleScrollView.setOnScrollListener(new HorizontalScaleScrollView.OnScrollListener()
        {

            @Override
            public void onScaleScroll(int scale)
            {
                tmp = scale;
                mTvHorizontalScale.setText("" + (scale * scaleScrollView.mScaleValue + Config.VALUE));
            }

        });
        initViews();
    }

    private void initViews(){
        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(this);
        Button sub = (Button) findViewById(R.id.sub);
        sub.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.add:
                System.out.println("onclick");
                scaleScrollView.ScrollTo(2);
                break;
            case R.id.sub:
                scaleScrollView.ScrollTo(7);
                break;
            default:
                break;
        }
    }
}

