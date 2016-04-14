package com.kdc.yongheshen.keduchi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * 作者： shenyonghe689 on 16/4/13.
 */
public class HorizontalScaleScrollView extends BaseScaleView {

    public HorizontalScaleScrollView(Context context) {
        super(context);
    }

    public HorizontalScaleScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalScaleScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HorizontalScaleScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void initVar() {
        mRectWidth = (mMax - mMin) * mScaleMargin;
        mRectHeight = mScaleHeight * 8;
        mScaleMaxHeight = mScaleHeight * 3;

        // 设置layoutParams
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(mRectWidth, mRectHeight);
        this.setLayoutParams(lp);
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth()/2;
        if (mDefauteValue<=mMax && mDefauteValue>=mMin)
        {
            smoothScrollBy(-width+mDefauteValue*mScaleMargin, 0);
        }
        else {
            System.out.println("HorizontalScaleScrollView 初始值不对！");
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height=MeasureSpec.makeMeasureSpec(mRectHeight, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, height);
        //获取组件宽度
        mScaleScrollViewRange = getMeasuredWidth();
        //中间值，当前值
        mTempScale = mScaleScrollViewRange / mScaleMargin / 2 + mMin;
        mMidCountScale = mScaleScrollViewRange / mScaleMargin / 2 + mMin;
    }

    //画中间线  替换图
    @Override
    protected void onDrawLine(Canvas canvas, Paint paint) {
        canvas.drawLine(0, mRectHeight-mScaleHeight*2, mRectWidth, mRectHeight-mScaleHeight*2, paint);
    }

    public void ScrollTo(int scla){
        System.out.println(scla);
        int x = (int) ((scla-mCountScale)* mScaleMargin);
        System.out.println(x);
        smoothScrollBy(x, 0);
        postInvalidate();
    }

    //长短分割线
    @Override
    protected void onDrawScale(Canvas canvas, Paint paint) {

        paint.setTextSize(mRectHeight / 8);

        for (int i = 0, k = mMin; i <= mMax - mMin; i++) {
            if (i % mSystemScal == 0) { //整值
                canvas.drawLine(i * mScaleMargin, mRectHeight, i * mScaleMargin, mRectHeight -
                        mScaleHeight*4, paint);
                //整值文字
                canvas.drawText(String.valueOf((int)(k*mScaleValue+Config.VALUE)), i * mScaleMargin, mRectHeight - mScaleMaxHeight - 40, paint);
                k += mSystemScal;
            } else {
                canvas.drawLine(i * mScaleMargin, mRectHeight-mScaleHeight, i * mScaleMargin, mRectHeight - mScaleHeight*3, paint);
            }
        }

    }

    // 指针 换成图片
    @Override
    protected void onDrawPointer(Canvas canvas, Paint paint) {

        paint.setColor(Color.RED);

        //每一屏幕刻度的个数/2
        int countScale = mScaleScrollViewRange / mScaleMargin / 2;
        //根据滑动的距离，计算指针的位置【指针始终位于屏幕中间】
        int finalX = mScroller.getFinalX();

        //滑动的刻度
        int tmpCountScale = (int) Math.rint((double) finalX / (double) mScaleMargin); //四舍五入取整
        //总刻度
        mCountScale = tmpCountScale + countScale + mMin;
        if (mScrollListener != null) { //回调方法
            mScrollListener.onScaleScroll(mCountScale);
        }
        canvas.drawLine(countScale * mScaleMargin + finalX, mRectHeight,
                countScale * mScaleMargin + finalX, mRectHeight - mScaleMaxHeight - mScaleHeight, paint);
       // canvas.drawBitmap();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mScroller != null && !mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mScrollLastX = x;
                return true;
            case MotionEvent.ACTION_MOVE:
                int dataX = mScrollLastX - x;
                if (mCountScale - mTempScale < 0) { //向右边滑动
                    if (mCountScale <= mMin && dataX <= 0) //禁止继续向右滑动
                        return super.onTouchEvent(event);
                } else if (mCountScale - mTempScale > 0) { //向左边滑动
                    if (mCountScale >= mMax && dataX >= 0) //禁止继续向左滑动
                        return super.onTouchEvent(event);
                }
                smoothScrollBy(dataX, 0);
                mScrollLastX = x;
                postInvalidate();
                mTempScale = mCountScale;
                return true;
            case MotionEvent.ACTION_UP:
                if (mCountScale < mMin) mCountScale = mMin;
                if (mCountScale > mMax) mCountScale = mMax;
                int finalX = (mCountScale - mMidCountScale) * mScaleMargin;
                mScroller.setFinalX(finalX); //纠正指针位置
                postInvalidate();
                return true;
        }
        return super.onTouchEvent(event);
    }

}
