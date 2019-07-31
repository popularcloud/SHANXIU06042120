package com.lwc.shanxiu.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.lwc.shanxiu.R;

public class DragListItem extends LinearLayout {
    private Context mContext;
    private View mHidenDragView;
    private LinearLayout mContentView;//将包裹实际的内容
    private LinearLayout mHidenLayout;
    private Scroller mScroller;
    private int mLastX, mLastY;
    private int mDragOutWidth;//完全侧滑出来的距离
    private double mfraction = 0.75;//触发自动侧滑的临界点
    private boolean isDrag = false;

    public DragListItem(Context context) {
        super(context);
        mContext = context;
        initView();
    }
    public DragListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }
    private void initView() {
        setOrientation(HORIZONTAL);

        //merge进来整个listItem，在这里可以自己定义删除按钮的显示的布局，随便按照的喜好修改都行
        mHidenDragView = View.inflate(mContext, R.layout.hide_drag_item, this);

        mContentView = (LinearLayout) mHidenDragView.findViewById(R.id.show_content_view);
        mHidenLayout = (LinearLayout) mHidenDragView.findViewById(R.id.hide_view);
        mScroller = new Scroller(mContext);

        //将隐藏的删除布局的宽度赋值给边界的值，根据自己的需要可以任意的修改
        mDragOutWidth = dip2px(mContext, 120);
    }
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 根据传递进来的事件，在此进行侧滑逻辑的判断，从而实现侧滑时删除按钮滑出的效果功能
     */
    public void onDragTouchEvent(MotionEvent event) {
        if (isDrag) {//手指在横向滑动时设置条目不可点击
            setClickable(false);
        } else {
            setClickable(true);
        }
        int x = (int) event.getX();
        int y = (int) event.getY();
        int scrollX = getScrollX();//手机屏幕左上角x轴的值 - view的左上角x轴的值
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                hsaMove = true;
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;

                //纵向的滑动大于横向的滑动时是不处罚侧滑效果的
                // 此处的加上100是为了让条目的侧滑更容易触发，根据自己的需要可以调整该值
                if (Math.abs(deltaX) + 100 < Math.abs(deltaY))
                {
                    break;
                }
                if (deltaX != 0) {//手指正在横向滑动
                    isDrag = true;
                    int newScrollX = scrollX - deltaX;//当这个值变小时，view视图向左滑动
                    if (newScrollX < 0) {//保持大于等于0，等于0时view左上角x值和屏幕左上角x值重合
                        newScrollX = 0;
                        setClickable(true);
                    } else if (newScrollX > mDragOutWidth) {//当到达隐藏布局的边界时 是不能再侧滑了
                        newScrollX = mDragOutWidth;
                    }
                    scrollTo(newScrollX, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                hsaMove = false;
            default:
                int finalScrollX = 0;

                //左滑到足够自动滑动的位置时可以自动滑出删除布局
                // ，否则就自动回缩隐藏删除布局
                if (scrollX > mDragOutWidth * mfraction) {
                    finalScrollX = mDragOutWidth;
                    autoScrollToX(finalScrollX, 500);
                } else {
                    rollBack();
                    isDrag = false;
                }
                break;
        }
        mLastX = x;
        mLastY = y;
    }
    private boolean hsaMove = false;//该条目是否已经监听过手势的滑动，用来作为判断是否进行条目左右滑动的条件之一
    public boolean isHsaMove() {
        return hsaMove;
    }
    public void setHsaMove(boolean hsaMove) {
        this.hsaMove = hsaMove;
    }
    public void setIsDrag(boolean isDrag) {
        this.isDrag = isDrag;
    }
    /**
     * 自动回滚到封闭状态
     */
    public void rollBack() {
        if (getScrollX() != 0) {
            autoScrollToX(0, 100);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    setClickable(true);
                    isDrag = false;//将状态置为false，没有侧滑出
                    hsaMove = false;//状态重置后将是否滑动过置为没有滑动过
                }
            }, 10);
        }
    }
    private void autoScrollToX(int finalX, int duration) {
        mScroller.startScroll(getScrollX(), 0, finalX - getScrollX(), 0, duration);
        invalidate();
    }
    public boolean getDragState() {
        return isDrag;
    }
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
    /**
     * 更改隐藏页的文字
     */
    public void setFirstHidenView(CharSequence charSequence) {
        TextView textView = (TextView) mHidenLayout.findViewById(R.id.hide_delete);
        textView.setText(charSequence);
    }
    /**
     * 给使用者添加隐藏页的视图（不仅仅是删除）
     */
    public void addHidenView(TextView view) {
        mHidenLayout.addView(view);
    }
    /**
     * 给使用者设置listItem的实际内容
     */
    public void setContentView(View view) {
        mContentView.addView(view);
    }
    public double getMfraction() {
        return mfraction;
    }
    public void setMfraction(double mfraction) {
        this.mfraction = mfraction;
    }
}