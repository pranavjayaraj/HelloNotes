package com.pranavj7.android.hellonote.Utility;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

// Lines under each text for detail notea............................
public class RuledLineNote extends EditText {
    private Rect mRect;
    private Paint mPaint;

    public RuledLineNote(Context context, AttributeSet attrs) {
        super(context, attrs);

        mRect = new Rect();
        mPaint = new Paint();
        // define the style of line
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        // define the color of line
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int height = getHeight();
        int lHeight = getLineHeight();
        // the number of line
        int count = height / lHeight;
        if (getLineCount() > count) {
            // for long text with scrolling
            count = getLineCount();
        }
        Rect r = mRect;
        Paint paint = mPaint;

        // first line
        int baseline = getLineBounds(0, r);

        // NewDrawableActivity the remaining lines.
        for (int i = 0; i < count; i++) {
            canvas.drawLine(r.left, baseline + 1, r.right, baseline + 1, paint);
            // next line
            baseline += getLineHeight();
        }
        super.onDraw(canvas);
    }
}