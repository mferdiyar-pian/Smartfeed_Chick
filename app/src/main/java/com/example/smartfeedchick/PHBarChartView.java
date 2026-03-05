package com.example.smartfeedchick;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

public class PHBarChartView extends View {

    private Paint barPaint, highlightPaint, textPaint;
    // pH values normalized (7.0=baseline), bars represent pH readings
    private float[] barValues = {0.55f, 0.60f, 0.58f, 0.65f, 0.70f, 0.68f, 0.72f, 0.75f, 0.60f, 0.55f, 0.65f, 0.80f};
    private int highlightIndex = 11; // last bar highlighted

    public PHBarChartView(Context context) { super(context); init(); }
    public PHBarChartView(Context context, AttributeSet attrs) { super(context, attrs); init(); }
    public PHBarChartView(Context context, AttributeSet attrs, int def) { super(context, attrs, def); init(); }

    private void init() {
        barPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        barPaint.setColor(Color.parseColor("#C8E6C9"));
        barPaint.setStyle(Paint.Style.FILL);

        highlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        highlightPaint.setColor(Color.parseColor("#2E7D32"));
        highlightPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.parseColor("#CCCCCC"));
        textPaint.setTextSize(22f);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth(), h = getHeight();
        int n = barValues.length;
        float totalGap = w * 0.3f;
        float barWidth = (w - totalGap) / (float) n;
        float gap = totalGap / (float)(n + 1);
        float cornerRadius = 6f;

        for (int i = 0; i < n; i++) {
            float left = gap + i * (barWidth + gap);
            float barH = h * barValues[i];
            float top = h - barH;
            float right = left + barWidth;

            RectF rect = new RectF(left, top, right, h);
            canvas.drawRoundRect(rect, cornerRadius, cornerRadius, i == highlightIndex ? highlightPaint : barPaint);
        }
    }
}