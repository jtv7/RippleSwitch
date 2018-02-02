package com.jtv7.rippleswitchlib;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Checkable;

/**
 * Created by jtv7 on 2/1/18.
 */

public class RippleSwitch extends View implements Checkable {

    private static final int UNCHECKED_COLOR = Color.parseColor("#2C2C2C");
    private static final int CHECKED_COLOR = Color.WHITE;

    private static final int DEFAULT_WIDTH = 80;
    private static final int DEFAULT_HEIGHT = 40;

    private int uncheckedColor = UNCHECKED_COLOR;
    private int checkedColor = CHECKED_COLOR;

    // Animator
    private ValueAnimator rippleAnimation;
    private float knobScaleAnimatedValueRight = 0;
    private float knobScaleAnimatedValueLeft = 0;

    private boolean mChecked = false;
    private boolean isAnimating = false;

    private static final int mDuration = 300;
    private static int backgroundColor = CHECKED_COLOR;

    private RectF rectF;

    private OnCheckedChangeListener listener;

    public RippleSwitch(Context context) {
        super(context);
        init(null);
    }

    public RippleSwitch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RippleSwitch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attr) {
        if (attr != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attr, R.styleable.RippleSwitch);
            mChecked = ta.getBoolean(R.styleable.RippleSwitch_rs_checked, false);
            checkedColor = ta.getColor(R.styleable.RippleSwitch_rs_checked_color, CHECKED_COLOR);
            uncheckedColor = ta.getColor(R.styleable.RippleSwitch_rs_unchecked_color, UNCHECKED_COLOR);
            ta.recycle();
        }

        ensureCorrectValues();

        rectF = new RectF();

        setClickable(true);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

    }

    private void ensureCorrectValues() {
        if (mChecked) {
            backgroundColor = uncheckedColor;
        } else {
            backgroundColor = checkedColor;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getSize(widthMeasureSpec, dp2px(DEFAULT_WIDTH));
        int height = getSize(heightMeasureSpec, dp2px(DEFAULT_HEIGHT));

        setMeasuredDimension(width, height);
    }

    private int dp2px(float dpValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int getSize(int measureSpec, int fallbackSize) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.AT_MOST:
                return Math.min(size, fallbackSize);
            case MeasureSpec.EXACTLY:
                return size;
            case MeasureSpec.UNSPECIFIED:
                return fallbackSize;
            default:
                return size;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        rectF.set(0, 0, getWidth(), getHeight());
        RippleSwitchUtil.drawSwitch(canvas, rectF, RippleSwitchUtil.ResizeType.AspectFit, uncheckedColor, checkedColor, backgroundColor, knobScaleAnimatedValueLeft, knobScaleAnimatedValueLeft, knobScaleAnimatedValueRight, knobScaleAnimatedValueRight);
    }

    private ValueAnimator getScaleAnimator() {
        return ValueAnimator.ofFloat(0.7f, 3f);
    }

    private void setAnimatedState(final boolean isChecked) {

        rippleAnimation = getScaleAnimator();
        rippleAnimation.setInterpolator(new LinearInterpolator());
        rippleAnimation.setDuration(mDuration);

        rippleAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                if (isChecked) {
                    knobScaleAnimatedValueLeft = (float) animation.getAnimatedValue();
                } else {
                    knobScaleAnimatedValueRight = (float) animation.getAnimatedValue();
                }

                // Could be done is onAnimationEnd, but I want to invalidate() here
                if ((float) animation.getAnimatedValue() == 3) {
                    if (isChecked) {
                        backgroundColor = uncheckedColor;
                        knobScaleAnimatedValueLeft = 0;
                    } else {
                        //right side knob, color will move towards the left
                        backgroundColor = checkedColor;
                        knobScaleAnimatedValueRight = 0;
                    }
                }

                invalidate();
            }
        });

        rippleAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        rippleAnimation.start();
    }

    public interface OnCheckedChangeListener {
        void onCheckChanged(boolean checked);
    }

    @Override
    public boolean performClick() {

        if (!isAnimating) {
            if (mChecked) {
                setAnimatedState(false);
                mChecked = false;
            } else {
                setAnimatedState(true);
                mChecked = true;
            }
        }
        if (listener != null) {
            listener.onCheckChanged(mChecked);
        }

        return super.performClick();
    }

    @Override
    public void setChecked(boolean checked) {
        this.mChecked = checked;
        ensureCorrectValues();
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        performClick();
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.listener = listener;
    }

    public int getCheckedColor() {
        return checkedColor;
    }

    public void setCheckedColor(int checkedColor) {
        this.checkedColor = checkedColor;
        ensureCorrectValues();
    }

    public int getUncheckedColor() {
        return uncheckedColor;
    }

    public void setUncheckedColor(int uncheckedColor) {
        this.uncheckedColor = uncheckedColor;
        ensureCorrectValues();
    }
}
