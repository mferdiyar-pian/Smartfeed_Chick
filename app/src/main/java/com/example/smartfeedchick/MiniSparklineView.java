package com.example.smartfeedchick;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

public class MiniSparklineView extends View {

    private Paint linePaint;
    private Path linePath;
    private float[] data = {0.5f, 0.52f, 0.49f, 0.55f, 0.58f, 0.54f, 0.60f, 0.62f, 0.58f, 0.65f};
    private String colorMode = "green";

    public MiniSparklineView(Context context) { super(context); init(); }
    public MiniSparklineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (getTag() != null) colorMode = getTag().toString();
        init();
    }
    public MiniSparklineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (getTag() != null) colorMode = getTag().toString();
        init();
    }

    private void init() {
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.parseColor(colorMode.equals("orange") ? "#FF9800" : "#4CAF50"));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(2.5f);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStrokeJoin(Paint.Join.ROUND);
        linePath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth(), h = getHeight();
        float stepX = w / (float)(data.length - 1);
        linePath.reset();
        float x0 = 0, y0 = h * (1 - data[0]);
        linePath.moveTo(x0, y0);
        for (int i = 1; i < data.length; i++) {
            float x1 = i * stepX;
            float y1 = h * (1 - data[i]);
            float cx = (x0 + x1) / 2f;
            linePath.cubicTo(cx, y0, cx, y1, x1, y1);
            x0 = x1; y0 = y1;
        }
        canvas.drawPath(linePath, linePaint);
    }
}