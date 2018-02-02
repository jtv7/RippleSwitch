package com.jtv7.rippleswitchlib;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import java.util.Stack;



/**
 * Created by jtv7 on 2/1/18.
 *
 * @author jtv7
 */
public class RippleSwitchUtil {
    
    // Resizing
    public enum ResizeType {
        AspectFit,
        AspectFill,
        Stretch,
        Center,
    }
    
    private static class Cache {
        private static Paint paint = new Paint();
        private static RectF originalFrame = new RectF(0f, 0f, 300f, 150f);
        private static RectF resizedFrame = new RectF();
        private static RectF group = new RectF();
        private static Path rectanglePath = new Path();
        private static RectF backgroundRect = new RectF();
        private static Path backgroundPath = new Path();
        private static RectF rightKnobResizeRect = new RectF();
        private static Path rightKnobResizePath = new Path();
        private static RectF leftKnobResizeRect = new RectF();
        private static Path leftKnobResizePath = new Path();
        private static RectF leftKnobRect = new RectF();
        private static Path leftKnobPath = new Path();
        private static RectF rightKnobRect = new RectF();
        private static Path rightKnobPath = new Path();
    }
    
    public static void drawSwitch(Canvas canvas, RectF targetFrame, ResizeType resizing, int buttonLeftColor, int buttonRightColor, int backgroundColor, float leftScaleX, float leftScaleY, float rightScaleX, float rightScaleY) {

        Stack<Matrix> currentTransformation = new Stack<Matrix>();
        currentTransformation.push(new Matrix());
        Paint paint = Cache.paint;
        
        // Resize to Target Frame
        canvas.save();
        RectF resizedFrame = Cache.resizedFrame;
        RippleSwitchUtil.resize(resizing, Cache.originalFrame, targetFrame, resizedFrame);
        canvas.translate(resizedFrame.left, resizedFrame.top);
        canvas.scale(resizedFrame.width() / 300f, resizedFrame.height() / 150f);
        
        // Group
        {
            RectF group = Cache.group;
            group.set(0f, 0f, 300f, 150f);
            canvas.save();
            
            // Rectangle
            Path rectanglePath = Cache.rectanglePath;
            rectanglePath.reset();
            rectanglePath.addRoundRect(group, 75f, 75f, Path.Direction.CW);
            canvas.clipPath(rectanglePath);
            
            // Background
            RectF backgroundRect = Cache.backgroundRect;
            backgroundRect.set(0f, 0f, 300f, 150f);
            Path backgroundPath = Cache.backgroundPath;
            backgroundPath.reset();
            backgroundPath.addRect(backgroundRect, Path.Direction.CW);
            
            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(backgroundColor);
            canvas.drawPath(backgroundPath, paint);
            
            // RightKnobResize
            canvas.save();
            canvas.translate(75f, 75f);
            currentTransformation.peek().postTranslate(75f, 75f);
            canvas.scale(leftScaleX, leftScaleY);
            currentTransformation.peek().postScale(leftScaleX, leftScaleY);
            RectF rightKnobResizeRect = Cache.rightKnobResizeRect;
            rightKnobResizeRect.set(-75f, -75f, 75f, 75f);
            Path rightKnobResizePath = Cache.rightKnobResizePath;
            rightKnobResizePath.reset();
            rightKnobResizePath.addOval(rightKnobResizeRect, Path.Direction.CW);
            
            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(buttonLeftColor);
            canvas.drawPath(rightKnobResizePath, paint);
            canvas.restore();
            
            // LeftKnobResize
            canvas.save();
            canvas.translate(225f, 75f);
            currentTransformation.peek().postTranslate(225f, 75f);
            canvas.scale(rightScaleX, rightScaleY);
            currentTransformation.peek().postScale(rightScaleX, rightScaleY);
            RectF leftKnobResizeRect = Cache.leftKnobResizeRect;
            leftKnobResizeRect.set(-75f, -75f, 75f, 75f);
            Path leftKnobResizePath = Cache.leftKnobResizePath;
            leftKnobResizePath.reset();
            leftKnobResizePath.addOval(leftKnobResizeRect, Path.Direction.CW);
            
            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(buttonRightColor);
            canvas.drawPath(leftKnobResizePath, paint);
            canvas.restore();
            
            canvas.restore();
        }
        
        // LeftKnob
        canvas.save();
        canvas.translate(75f, 75f);
        currentTransformation.peek().postTranslate(75f, 75f);
        canvas.scale(0.7f, 0.7f);
        currentTransformation.peek().postScale(0.7f, 0.7f);
        RectF leftKnobRect = Cache.leftKnobRect;
        leftKnobRect.set(-75f, -75f, 75f, 75f);
        Path leftKnobPath = Cache.leftKnobPath;
        leftKnobPath.reset();
        leftKnobPath.addOval(leftKnobRect, Path.Direction.CW);
        
        paint.reset();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(buttonLeftColor);
        canvas.drawPath(leftKnobPath, paint);
        canvas.restore();
        
        // RightKnob
        canvas.save();
        canvas.translate(225f, 75f);
        currentTransformation.peek().postTranslate(225f, 75f);
        canvas.scale(0.7f, 0.7f);
        currentTransformation.peek().postScale(0.7f, 0.7f);
        RectF rightKnobRect = Cache.rightKnobRect;
        rightKnobRect.set(-75f, -75f, 75f, 75f);
        Path rightKnobPath = Cache.rightKnobPath;
        rightKnobPath.reset();
        rightKnobPath.addOval(rightKnobRect, Path.Direction.CW);
        
        paint.reset();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(buttonRightColor);
        canvas.drawPath(rightKnobPath, paint);
        canvas.restore();
        
        canvas.restore();
    }
    
    
    // Resizing
    private static void resize(ResizeType behavior, RectF rect, RectF target, RectF result) {
        if (rect.equals(target) || target == null) {
            result.set(rect);
            return;
        }
        
        if (behavior == ResizeType.Stretch) {
            result.set(target);
            return;
        }
        
        float xRatio = Math.abs(target.width() / rect.width());
        float yRatio = Math.abs(target.height() / rect.height());
        float scale = 0f;
        
        switch (behavior) {
            case AspectFit: {
                scale = Math.min(xRatio, yRatio);
                break;
            }
            case AspectFill: {
                scale = Math.max(xRatio, yRatio);
                break;
            }
            case Center: {
                scale = 1f;
                break;
            }
        }
        
        float newWidth = Math.abs(rect.width() * scale);
        float newHeight = Math.abs(rect.height() * scale);
        result.set(target.centerX() - newWidth / 2,
            target.centerY() - newHeight / 2,
            target.centerX() + newWidth / 2,
            target.centerY() + newHeight / 2);
    }
    
    
}