package murmur.pagefoldlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class PageFoldLayout extends FrameLayout {
    private int mWidth, mHeight;
    private int HEIGHT_LIMIT;
    private float mPointX;
    private float mPointY;
    private float mDegree;
    private float mPercent = 0.0f;
    private boolean isShort;
    //private Paint mPaint;
    private Path mPath;
    private Path mPathFoldandNext;


    public PageFoldLayout(Context context) {
        super(context);
    }

    public PageFoldLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PageFoldLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void updatePoint(float percent){
        mPercent = percent;
        if(percent < 0){
            percent += 1;
        }
        float angle = 180 * percent;
        mPointX = mWidth * 2 * angle / 180;
        mPointY = mHeight - (float)Math.sin(Math.toRadians(angle)) * HEIGHT_LIMIT;
        //Log.d("kanna", "update " + mPointX + " " + mPointY + " " + percent + " " + angle);
        invalidate();
    }

    private void initPaint(){
        /*
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);*/

        mPath = new Path();
        mPathFoldandNext = new Path();
    }


    protected void onSizeChanged (int w, int h, int oldw, int oldh){
        initPaint();
        mWidth = w;
        mHeight = h;
        HEIGHT_LIMIT = h >> 2;
    }
    private void drawView(Canvas canvas){
        if(mPercent > 0) {
            //upper region
            canvas.save();
            canvas.clipRect(0, 0, mWidth, mHeight);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                canvas.clipPath(mPathFoldandNext, Region.Op.DIFFERENCE);
            } else {
                canvas.clipOutPath(mPathFoldandNext);
            }
            //canvas.drawColor(Color.WHITE);
            super.dispatchDraw(canvas);
            canvas.restore();


            //fold region
            canvas.save();
            canvas.clipPath(mPath);
            //canvas.clipPath(mPathFoldandNext);

            //cos@ = sizeShort - mPointX / sizeShort
            if (isShort) {
                canvas.translate(mPointX, mPointY);
                canvas.rotate(mDegree);
                canvas.scale(1, -1);
                canvas.translate(0, -mHeight);
            }
            //sin@ = mHeight - mPointY / sizeShort
            else {
                canvas.translate(mPointX, mPointY);
                canvas.rotate(-mDegree);
                canvas.scale(-1, 1);
                canvas.translate(0, -mHeight);
            }


            super.dispatchDraw(canvas);
            //canvas.drawColor(Color.WHITE);
            canvas.restore();
        }
        else{
            canvas.save();
            canvas.clipPath(mPathFoldandNext);
            //canvas.clipRegion(regionFold, Region.Op.DIFFERENCE);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                canvas.clipPath(mPath, Region.Op.DIFFERENCE);
            } else {
                canvas.clipOutPath(mPath);
            }
            super.dispatchDraw(canvas);
            canvas.restore();
        }
    }




    private void dispatchDrawUsingBitmap(Canvas canvas) {
        mPath.reset();
        mPathFoldandNext.reset();
        canvas.drawColor(Color.TRANSPARENT);

        float TX = mHeight - mPointY;
        float OX = mPointX;
        float temp = (float) (Math.pow(TX, 2) + Math.pow(OX, 2));

        float sizeShort = temp/(2 * OX);
        float sizeLong = temp/(2 * TX);

        if(sizeShort > sizeLong){
            isShort = true;
            float sin = (sizeShort - mPointX) / sizeShort;
            mDegree =  (float)(Math.acos(sin) / Math.PI * 180);
            //Log.d("kanna","get "+ mDegree);
        }
        else{
            isShort = false;
            float sin = (mHeight - mPointY) / sizeShort;
            mDegree = (float)(Math.asin(sin) / Math.PI * 180);
        }


        mPath.moveTo(mPointX, mPointY);
        mPathFoldandNext.moveTo(0, mHeight);

        if(sizeLong > mHeight){
            float an = sizeLong - mHeight;
            float nm = an/(sizeLong - TX) * OX;
            float nq = an/sizeLong * sizeShort;
            mPath.lineTo(nm, 0);
            mPath.lineTo(nq, 0);
            mPath.lineTo(sizeShort, mHeight);
            mPath.close();

            mPathFoldandNext.lineTo(0, 0);
            mPathFoldandNext.lineTo(nm,0);
            mPathFoldandNext.lineTo(mPointX, mPointY);
            mPathFoldandNext.lineTo(sizeShort, mHeight);
            mPathFoldandNext.close();


        }
        else {
            mPath.lineTo(0, mHeight - sizeLong);
            mPath.lineTo(sizeShort, mHeight);
            mPath.close();


            mPathFoldandNext.lineTo(0, mHeight - sizeLong);
            mPathFoldandNext.lineTo(mPointX, mPointY);
            mPathFoldandNext.lineTo(sizeShort, mHeight);
            mPathFoldandNext.close();
        }
        drawView(canvas);
        /*
        mPaint.setColor(Color.BLACK);
        canvas.drawPath(mPath, mPaint);

        mPaint.setColor(Color.RED);
        canvas.drawPath(mPathFoldandNext, mPaint);*/
    }
    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (getWidth() <= 0 || getHeight() <= 0 || mPercent == 0) {
            super.dispatchDraw(canvas);
        } else {
            dispatchDrawUsingBitmap(canvas);
        }
    }
    /*
    public boolean onTouchEvent (MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_UP){
            float x = event.getX();
            Log.d("kanna","get progress " + (x / getWidth()));
            updatePoint(x / getWidth());
        }
        return true;
    }*/
}
