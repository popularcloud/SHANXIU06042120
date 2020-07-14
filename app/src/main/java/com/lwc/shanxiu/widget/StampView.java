package com.lwc.shanxiu.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.utils.DisplayUtil;

public class StampView extends View {

    private static final int TEXT_SIZE = 22;  //文字大小
    private static final int OUTSIDE_RING_WIDTH = 10; //圆环宽度
    private static final float TEXT_ANGLE = 220f;  //文字扇形排列的角度
    private static final int RADIUS = 68;  //印章半径
    private static final int SPACING = 0;  //图片与文字间距
    private static final float STAMP_ROTATE = 15f;  //印章旋转角度
    
    //private static final int TEXT_COLOR = R.color.red_ff; //文字颜色
    //private static final int CIRCLE_COLOR = R.color.red_ff; //圆环颜色
    
    private String mText;
    private Bitmap source;
    
    private final Paint mTextPaint = new Paint();
    private final Paint mCirclePaint = new Paint();
    
    public StampView(Context context) {
        super(context);
        init();
    }
    
    public StampView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public StampView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    
    private void init() {
        mTextPaint.setColor(getResources().getColor(R.color.red_f3a));
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mCirclePaint.setColor(getResources().getColor(R.color.red_f3a));
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setAntiAlias(true);
        
        source = BitmapFactory.decodeResource(getResources(), R.drawable.ic_start);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        // 获取宽高
        int width = this.getWidth();
        int height = this.getHeight();
        
        mTextPaint.setTextSize(Utils.dip2px(getContext(),TEXT_SIZE));
        
        float textY = height / 2 - DisplayUtil.dip2px(getContext(),RADIUS) + OUTSIDE_RING_WIDTH + TEXT_SIZE;
        mText = mText != null ? mText : "";
        // 把文字拆成字符数组
        char[] chs = mText.toCharArray();
        
        // 画圆环
        mCirclePaint.setStrokeWidth(OUTSIDE_RING_WIDTH);
        canvas.drawCircle(width / 2, height / 2, DisplayUtil.dip2px(getContext(),RADIUS) - OUTSIDE_RING_WIDTH / 2, mCirclePaint);
        
        canvas.save();
        canvas.rotate(STAMP_ROTATE, width / 2, height / 2);

        // 中间圆形位图的半径
        int radius = DisplayUtil.dip2px(getContext(),RADIUS) - OUTSIDE_RING_WIDTH - TEXT_SIZE - SPACING;
        Bitmap image = createCircleImage(source, Utils.dip2px(getContext(),40));
        canvas.drawBitmap(image, (width - Utils.dip2px(getContext(),40)) / 2, (height - Utils.dip2px(getContext(),40)) / 2,
                mCirclePaint);
        image.recycle();

        canvas.rotate(-TEXT_ANGLE / 2, width / 2, height / 2);
        // 每个文字间的角度间隔
        float spaceAngle = TEXT_ANGLE / (chs.length - 1);
        for(int i = 0; i < chs.length; i++) {
            String s = String.valueOf(chs[i]);
            canvas.drawText(s, width / 2, textY+Utils.dip2px(getContext(),15), mTextPaint);
            canvas.rotate(spaceAngle, width / 2, height / 2);
        }
        canvas.restore();
    }
    
    /**
     * 创建圆形位图
     * @param source 原图片位图
     * @param diameter 圆形位图的直径
     * @return
     */
    private Bitmap createCircleImage(Bitmap source, int diameter) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
                
        //int width = source.getWidth();
        //int height = source.getHeight();
        Bitmap clipBitmap = source;
      /*  if(width > height) {
            int x = (width - height) / 2;
            int y = 0;
            clipBitmap = Bitmap.createBitmap(source, x, y, height, height);
        } else if(width < height) {
            int x = 0;
            int y = (height - width) / 2;
            clipBitmap = Bitmap.createBitmap(source, x, y, width, width);
        } else {
            clipBitmap = source;
        }*/

       // clipBitmap = source;
        
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(clipBitmap, diameter, diameter, true);
        Bitmap outputBitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(outputBitmap);
      //  canvas.drawCircle(diameter / 2, diameter / 2, diameter / 2 + 5, paint);
       // paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        paint.setColor(getResources().getColor(R.color.red_f3a));
        canvas.drawBitmap(scaledBitmap, 0, 0, paint);

//        source.recycle();
       // clipBitmap.recycle();
       scaledBitmap.recycle();
        return outputBitmap;
    }
    
    public void setText(String text) {
        mText = text;
    }

    public String getText() {
        return mText;
    }
    
    public void setTextColor(int textColor) {
        mTextPaint.setColor(textColor);
    }
    
    public void setCircleColor(int circleColor) {
        mCirclePaint.setColor(circleColor);
    }
    
    public void setBitmap(int id) {
        source = BitmapFactory.decodeResource(getResources(), id);
    }

    public Bitmap getTransparentBitmap(Bitmap sourceImg, int number){
        int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];

        sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0, sourceImg

                .getWidth(), sourceImg.getHeight());// 获得图片的ARGB值

        number = number * 255 / 100;

        for (int i = 0; i < argb.length; i++) {

            argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);

        }

        sourceImg = Bitmap.createBitmap(argb, sourceImg.getWidth(), sourceImg

                .getHeight(), Bitmap.Config.ARGB_8888);

        return sourceImg;
    }
}