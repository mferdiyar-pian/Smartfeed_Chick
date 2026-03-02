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

public class LineChartView extends View {

    private Paint linePaint, fillPaint, dotPaint, textPaint;
    private Path linePath, fillPath;

    // Sample data points (normalized 0-1)
    private float[] dataPoints = {0.4f, 0.55f, 0.35f, 0.6f, 0.45f, 0.7f, 0.5f, 0.65f, 0.4f, 0.75f};

    public LineChartView(Context context) {
        super(context);
        init();
    }

    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.parseColor("#4CAF50"));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(3f);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStrokeJoin(Paint.Join.ROUND);

        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setStyle(Paint.Style.FILL);

        dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dotPaint.setColor(Color.parseColor("#2E7D32"));
        dotPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.parseColor("#BBBBBB"));
        textPaint.setTextSize(28f);
        textPaint.setTextAlign(Paint.Align.CENTER);

        linePath = new Path();
        fillPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int paddingH = getPaddingLeft();
        int paddingV = getPaddingTop();
        int chartWidth = width - paddingH * 2;
        int chartHeight = height - paddingV * 2;

        float stepX = chartWidth / (float)(dataPoints.length - 1);

        // Build smooth line path using bezier curves
        linePath.reset();
        fillPath.reset();

        float x0 = paddingH;
        float y0 = paddingV + chartHeight * (1 - dataPoints[0]);

        linePath.moveTo(x0, y0);
        fillPath.moveTo(x0, paddingV + chartHeight); // start at bottom
        fillPath.lineTo(x0, y0);

        for (int i = 1; i < dataPoints.length; i++) {
            float x1 = paddingH + i * stepX;
            float y1 = paddingV + chartHeight * (1 - dataPoints[i]);

            // Smooth curve using control points
            float cpX = (x0 + x1) / 2f;
            linePath.cubicTo(cpX, y0, cpX, y1, x1, y1);
            fillPath.cubicTo(cpX, y0, cpX, y1, x1, y1);

            x0 = x1;
            y0 = y1;
        }

        // Close fill path to bottom
        fillPath.lineTo(paddingH + chartWidth, paddingV + chartHeight);
        fillPath.close();

        // Gradient fill
        LinearGradient gradient = new LinearGradient(
                0, paddingV, 0, paddingV + chartHeight,
                Color.parseColor("#664CAF50"),
                Color.parseColor("#004CAF50"),
                Shader.TileMode.CLAMP
        );
        fillPaint.setShader(gradient);

        canvas.drawPath(fillPath, fillPaint);
        canvas.drawPath(linePath, linePaint);

        // Draw last data point dot
        float lastX = paddingH + (dataPoints.length - 1) * stepX;
        float lastY = paddingV + chartHeight * (1 - dataPoints[dataPoints.length - 1]);
        canvas.drawCircle(lastX, lastY, 6f, dotPaint);
    }

    public void setDataPoints(float[] points) {
        this.dataPoints = points;
        invalidate();
    }
}