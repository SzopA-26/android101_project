package com.example.myapplication.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.example.myapplication.R;

@SuppressLint("ViewConstructor")
public class MyRectangle extends View {
    Paint paint = new Paint();
    float[] graphValue;

    public MyRectangle(Context context, float[] graphValue) {
        super(context);
        this.graphValue = graphValue;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(6);
        canvas.drawLine(150,1003,950,1003, paint);
        canvas.drawLine(150,1003,150,197, paint);

        float max = 100;

        float radio = 0;
        if (max > 0) {
            radio = 800/max;
        }
        for (int i=0; i<graphValue.length; i++) {
            paint.setColor(Color.parseColor("#009606"));
            if (i > 0) {
                paint.setColor(Color.RED);
            }
            canvas.drawRect(300+(300*i), 1000, 300+(300*i)+150, 1000-(graphValue[i]*radio), paint);
        }

    }
}
