package com.example.smartfeedchick;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class MonitoringChartView extends View {

    private Paint linePaint, fillPaint, dotPaint, gridPaint;
    private Path linePath, fillPath;
    private String colorMode = "green"; // "green" or "orange"

    // Temperature-like data (smooth rise and fall)
    private float[] greenData = {0.45f, 0.50f, 0.48f, 0.55f, 0.60f, 0.58f, 0.65f, 0.70f, 0.68f, 0.72f, 0.75f, 0.73f};

    // Ammonia-like data (gradual increase, trending up)
    private float[] orangeData = {0.20f, 0.22f, 0.25f, 0.28f, 0.30f, 0.35f, 0.40f, 0.45f, 0.50f, 0.55f, 0.60f, 0.65f};

    public MonitoringChartView(Context context) {
        super(context);
        init();
    }

    public MonitoringChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Read tag for color mode
        if (getTag() != null) colorMode = getTag().toString();
        init();
    }

    public MonitoringChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (getTag() != null) colorMode = getTag().toString();
        init();
    }

    private void init() {
        String lineColor = colorMode.equals("orange") ? "#FF9800" : "#4CAF50";
        String fillTop   = colorMode.equals("orange") ? "#44FF9800" : "#444CAF50";
        String dotColor  = colorMode.equals("orange") ? "#E65100" : "#2E7D32";

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.parseColor(lineColor));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(2.8f);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStrokeJoin(Paint.Join.ROUND);

        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setStyle(Paint.Style.FILL);

        dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dotPaint.setColor(Color.parseColor(dotColor));
        dotPaint.setStyle(Paint.Style.FILL);

        gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gridPaint.setColor(Color.parseColor("#F0F0F0"));
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setStrokeWidth(1f);

        linePath = new Path();
        fillPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float[] data = colorMode.equals("orange") ? orangeData : greenData;
        String lineColor = colorMode.equals("orange") ? "#FF9800" : "#4CAF50";
        String fillTop   = colorMode.equals("orange") ? "#33FF9800" : "#334CAF50";

        int width = getWidth();
        int height = getHeight();
        int pH = getPaddingLeft();
        int pV = getPaddingTop();
        int cW = width - pH * 2;
        int cH = height - pV * 2;

        // Draw horizontal grid lines
        for (int i = 1; i < 4; i++) {
            float y = pV + cH * (i / 4.0f);
            canvas.drawLine(pH, y, pH + cW, y, gridPaint);
        }

        float stepX = cW / (float)(data.length - 1);
        linePath.reset();
        fillPath.reset();

        float x0 = pH;
        float y0 = pV + cH * (1 - data[0]);

        linePath.moveTo(x0, y0);
        fillPath.moveTo(x0, pV + cH);
        fillPath.lineTo(x0, y0);

        for (int i = 1; i < data.length; i++) {
            float x1 = pH + i * stepX;
            float y1 = pV + cH * (1 - data[i]);
            float cpX = (x0 + x1) / 2f;
            linePath.cubicTo(cpX, y0, cpX, y1, x1, y1);
            fillPath.cubicTo(cpX, y0, cpX, y1, x1, y1);
            x0 = x1;
            y0 = y1;
        }

        fillPath.lineTo(pH + cW, pV + cH);
        fillPath.close();

        // Gradient fill
        LinearGradient gradient = new LinearGradient(
                0, pV, 0, pV + cH,
                Color.parseColor(fillTop),
                Color.parseColor("#004CAF50"),
                Shader.TileMode.CLAMP
        );
        if (colorMode.equals("orange")) {
            gradient = new LinearGradient(
                    0, pV, 0, pV + cH,
                    Color.parseColor("#33FF9800"),
                    Color.parseColor("#00FF9800"),
                    Shader.TileMode.CLAMP
            );
        }
        fillPaint.setShader(gradient);

        canvas.drawPath(fillPath, fillPaint);
        canvas.drawPath(linePath, linePaint);

        // Draw last dot
        float lastX = pH + (data.length - 1) * stepX;
        float lastY = pV + cH * (1 - data[data.length - 1]);

        // White outer ring
        Paint whiteDot = new Paint(Paint.ANTI_ALIAS_FLAG);
        whiteDot.setColor(Color.WHITE);
        whiteDot.setStyle(Paint.Style.FILL);
        canvas.drawCircle(lastX, lastY, 7f, whiteDot);
        canvas.drawCircle(lastX, lastY, 5f, dotPaint);
    }
}