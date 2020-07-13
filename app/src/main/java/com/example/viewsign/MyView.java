package com.example.viewsign;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.LinkedList;

public class MyView extends View {
    private LinkedList<LinkedList<HashMap<String,Float>>> lines ;
    private Paint paint;
    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.GREEN);
        lines = new LinkedList<>();
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(LinkedList<HashMap<String,Float>> line : lines) {
            for (int i = 1; i < line.size(); i++) {
                HashMap<String, Float> p0 = line.get(i - 1);
                HashMap<String, Float> p1 = line.get(i);
                canvas.drawLine(p0.get("X"), p0.get("Y"), p1.get("X"), p1.get("Y"), paint);
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            Log.v("test","down");
            SetFirstPoint(event);
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            Log.v("test","UP");
        }
        else if(event.getAction() == MotionEvent.ACTION_MOVE){
            Log.v("test","MOVE");
            SetMovePoint(event);
        }

        super.onTouchEvent(event);
        return true;
    }

    private void SetFirstPoint(MotionEvent event){
        LinkedList<HashMap<String,Float>> line = new LinkedList<>();
        Float ex = event.getX();
        Float ey = event.getY();
        HashMap<String,Float> point = new HashMap<>();
        point.put("X",ex);
        point.put("Y",ey);
        line.add(point);
        lines.add(line);
    }

    private void SetMovePoint(MotionEvent event){

        Float ex = event.getX();
        Float ey = event.getY();
        HashMap<String,Float> point = new HashMap<>();
        point.put("X",ex);
        point.put("Y",ey);
        lines.getLast().add(point);

        invalidate(); //重新繪製
    }

    public void Clear(){
        lines.clear();
        invalidate();
    }

    public  void undo(){
        if(lines.size()>0) {
            lines.remove(lines.getLast());
            invalidate();
        }
    }
}
