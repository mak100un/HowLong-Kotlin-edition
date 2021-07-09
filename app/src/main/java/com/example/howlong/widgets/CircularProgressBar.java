package com.example.howlong.widgets;

import com.example.howlong.R;
import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StyleRes;

/**
 * Circular progress bar
 * source - https://github.com/yuriy-budiyev/circular-progress-bar/blob/master/src/main/java/com/budiyev/android/circularprogressbar/CircularProgressBar.java
 */
public final class CircularProgressBar extends View {
    private static final float DEFAULT_SIZE_DP = 48f;
    private static final float DEFAULT_MAXIMUM = 100f;
    private static final float DEFAULT_PROGRESS = 0f;
    private static final float DEFAULT_FOREGROUND_STROKE_WIDTH_DP = 3f;
    private static final float DEFAULT_BACKGROUND_STROKE_WIDTH_DP = 1f;
    private static final float DEFAULT_START_ANGLE = 270f;
    private static final int DEFAULT_FOREGROUND_STROKE_CAP = 0;
    private static final int DEFAULT_FOREGROUND_STROKE_COLOR = Color.BLUE;
    private static final int DEFAULT_BACKGROUND_STROKE_COLOR = Color.BLACK;
    private static final boolean DEFAULT_DRAW_BACKGROUND_STROKE = false;
    private final RectF mDrawRect = new RectF();
    private final Paint mForegroundStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mBackgroundStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mDefaultSize = 0;
    private float mMaximum = 0f;
    private float mProgress = 0f;
    private float mStartAngle = 0f;
    private float mForegroundStrokeCapAngle = 0f;
    private boolean mDrawBackgroundStroke = false;
    private boolean mIndeterminateGrowMode = false;
    private boolean mVisible = false;

    public CircularProgressBar(@NonNull final Context context) {
        super(context);
        initialize(context, null, 0, 0);
    }

    public CircularProgressBar(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, 0, 0);
    }

    public CircularProgressBar(@NonNull final Context context, @Nullable final AttributeSet attrs,
                               final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public CircularProgressBar(@NonNull final Context context, @Nullable final AttributeSet attrs,
                               final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Get current progress value for non-indeterminate mode
     */
    public float getProgress() {
        return mProgress;
    }

    /**
     * Set current progress value for non-indeterminate mode
     */
    public void setProgress(final float progress) {
        setProgressInternal(progress);
    }

    /**
     * Maximum progress for non-indeterminate mode
     */
    public float getMaximum() {
        return mMaximum;
    }

    /**
     * Maximum progress for non-indeterminate mode
     */
    public void setMaximum(final float maximum) {
        mMaximum = maximum;
        invalidate();
    }

    /**
     * Start angle for non-indeterminate mode, between -360 and 360 degrees
     */
    @FloatRange(from = -360f, to = 360f)
    public float getStartAngle() {
        return mStartAngle;
    }

    /**
     * Start angle for non-indeterminate mode, between -360 and 360 degrees
     */
    public void setStartAngle(@FloatRange(from = -360f, to = 360f) final float angle) {
        if (angle < -360f || angle > 360f) {
            throw new IllegalArgumentException("Start angle value should be between -360 and 360 degrees (inclusive)");
        }
        mStartAngle = angle;
        invalidate();
    }

    /**
     * Foreground stroke cap
     */
    @NonNull
    public Paint.Cap getForegroundStrokeCap() {
        return mBackgroundStrokePaint.getStrokeCap();
    }

    /**
     * Foreground stroke cap
     */
    public void setForegroundStrokeCap(@NonNull final Paint.Cap cap) {
        //noinspection ConstantConditions
        if (cap == null) {
            throw new IllegalArgumentException("Cap can't be null");
        }
        mForegroundStrokePaint.setStrokeCap(cap);
        invalidateForegroundStrokeCapAngle();
        invalidate();
    }

    /**
     * Foreground stroke color
     */
    @ColorInt
    public int getForegroundStrokeColor() {
        return mForegroundStrokePaint.getColor();
    }

    /**
     * Foreground stroke color
     */
    public void setForegroundStrokeColor(@ColorInt final int color) {
        mForegroundStrokePaint.setColor(color);
        invalidate();
    }

    /**
     * Foreground stroke width (in pixels)
     */
    @FloatRange(from = 0f, to = Float.MAX_VALUE)
    public float getForegroundStrokeWidth() {
        return mForegroundStrokePaint.getStrokeWidth();
    }

    /**
     * Foreground stroke width (in pixels)
     */
    public void setForegroundStrokeWidth(@FloatRange(from = 0f, to = Float.MAX_VALUE) final float width) {
        if (width < 0f) {
            throw new IllegalArgumentException("Width can't be negative");
        }
        mForegroundStrokePaint.setStrokeWidth(width);
        invalidateDrawRect();
        invalidate();
    }

    /**
     * Background stroke color
     */
    @ColorInt
    public int getBackgroundStrokeColor() {
        return mBackgroundStrokePaint.getColor();
    }

    /**
     * Background stroke color
     */
    public void setBackgroundStrokeColor(@ColorInt final int color) {
        mBackgroundStrokePaint.setColor(color);
        invalidate();
    }

    /**
     * Background stroke width (in pixels)
     */
    @FloatRange(from = 0f, to = Float.MAX_VALUE)
    public float getBackgroundStrokeWidth() {
        return mBackgroundStrokePaint.getStrokeWidth();
    }

    /**
     * Background stroke width (in pixels)
     */
    public void setBackgroundStrokeWidth(@FloatRange(from = 0f, to = Float.MAX_VALUE) final float width) {
        if (width < 0f) {
            throw new IllegalArgumentException("Width can't be negative");
        }
        mBackgroundStrokePaint.setStrokeWidth(width);
        invalidateDrawRect();
        invalidate();
    }

    /**
     * Whether to draw background stroke
     */
    public boolean isDrawBackgroundStroke() {
        return mDrawBackgroundStroke;
    }

    /**
     * Whether to draw background stroke
     */
    public void setDrawBackgroundStroke(final boolean draw) {
        mDrawBackgroundStroke = draw;
        invalidateDrawRect();
        invalidate();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        if (mDrawBackgroundStroke) {
            canvas.drawOval(mDrawRect, mBackgroundStrokePaint);
        }
        float sweep;
        final float maximum = mMaximum;
        final float progress = mProgress;
        float start = mStartAngle;
        if (Math.abs(progress) < Math.abs(maximum)) {
            sweep = progress / maximum * 360f;
        } else {
            sweep = 360f;
        }
        final float capAngle = mForegroundStrokeCapAngle;
        if (capAngle != 0f && Math.abs(sweep) != 360f) {
            if (sweep > 0) {
                start += capAngle;
                sweep -= capAngle * 2f;
                if (sweep < 0.0001f) {
                    sweep = 0.0001f;
                }
            } else if (sweep < 0) {
                start -= capAngle;
                sweep += capAngle * 2f;
                if (sweep > -0.0001f) {
                    sweep = -0.0001f;
                }
            }
        }
        canvas.drawArc(mDrawRect, start, sweep, false, mForegroundStrokePaint);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        final int defaultSize = mDefaultSize;
        final int defaultWidth = Math.max(getSuggestedMinimumWidth(), defaultSize);
        final int defaultHeight = Math.max(getSuggestedMinimumHeight(), defaultSize);
        final int width;
        final int height;
        switch (widthMode) {
            case MeasureSpec.EXACTLY: {
                width = widthSize;
                break;
            }
            case MeasureSpec.AT_MOST: {
                width = Math.min(defaultWidth, widthSize);
                break;
            }
            case MeasureSpec.UNSPECIFIED:
            default: {
                width = defaultWidth;
                break;
            }
        }
        switch (heightMode) {
            case MeasureSpec.EXACTLY: {
                height = heightSize;
                break;
            }
            case MeasureSpec.AT_MOST: {
                height = Math.min(defaultHeight, heightSize);
                break;
            }
            case MeasureSpec.UNSPECIFIED:
            default: {
                height = defaultHeight;
                break;
            }
        }
        setMeasuredDimension(width, height);
        invalidateDrawRect(width, height);
    }

    @Override
    protected void onSizeChanged(final int width, final int height, final int oldWidth, final int oldHeight) {
        invalidateDrawRect(width, height);
    }

    private void initialize(@NonNull final Context context, @Nullable final AttributeSet attributeSet,
                            @AttrRes final int defStyleAttr, @StyleRes final int defStyleRes) {
        mForegroundStrokePaint.setStyle(Paint.Style.STROKE);
        mBackgroundStrokePaint.setStyle(Paint.Style.STROKE);
        final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        mDefaultSize = Math.round(DEFAULT_SIZE_DP * displayMetrics.density);
        if (attributeSet == null) {
            mMaximum = DEFAULT_MAXIMUM;
            mProgress = DEFAULT_PROGRESS;
            mStartAngle = DEFAULT_START_ANGLE;
            mDrawBackgroundStroke = DEFAULT_DRAW_BACKGROUND_STROKE;
            mForegroundStrokePaint.setColor(DEFAULT_FOREGROUND_STROKE_COLOR);
            mForegroundStrokePaint.setStrokeWidth(Math.round(DEFAULT_FOREGROUND_STROKE_WIDTH_DP * displayMetrics.density));
            mForegroundStrokePaint.setStrokeCap(getStrokeCap(DEFAULT_FOREGROUND_STROKE_CAP));
            mBackgroundStrokePaint.setColor(DEFAULT_BACKGROUND_STROKE_COLOR);
            mBackgroundStrokePaint.setStrokeWidth(Math.round(DEFAULT_BACKGROUND_STROKE_WIDTH_DP * displayMetrics.density));
        } else {
            TypedArray attributes = null;
            try {
                attributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.CircularProgressBar, defStyleAttr, defStyleRes);
                setMaximum(attributes.getFloat(R.styleable.CircularProgressBar_maximum, DEFAULT_MAXIMUM));
                setProgress(attributes.getFloat(R.styleable.CircularProgressBar_progress, DEFAULT_PROGRESS));
                setStartAngle(attributes.getFloat(R.styleable.CircularProgressBar_startAngle, DEFAULT_START_ANGLE));

                setForegroundStrokeColor(attributes.getColor(R.styleable.CircularProgressBar_foregroundStrokeColor, DEFAULT_FOREGROUND_STROKE_COLOR));
                setBackgroundStrokeColor(attributes.getColor(R.styleable.CircularProgressBar_backgroundStrokeColor, DEFAULT_BACKGROUND_STROKE_COLOR));
                setForegroundStrokeWidth(attributes.getDimension(R.styleable.CircularProgressBar_foregroundStrokeWidth, Math.round(DEFAULT_FOREGROUND_STROKE_WIDTH_DP * displayMetrics.density)));
                setForegroundStrokeCap(getStrokeCap(attributes.getInt(R.styleable.CircularProgressBar_foregroundStrokeCap, DEFAULT_FOREGROUND_STROKE_CAP)));
                setBackgroundStrokeWidth(attributes.getDimension(R.styleable.CircularProgressBar_backgroundStrokeWidth, Math.round(DEFAULT_BACKGROUND_STROKE_WIDTH_DP * displayMetrics.density)));
                setDrawBackgroundStroke(attributes.getBoolean(R.styleable.CircularProgressBar_drawBackgroundStroke, DEFAULT_DRAW_BACKGROUND_STROKE));
            } finally {
                if (attributes != null) {
                    attributes.recycle();
                }
            }
        }
    }

    private void invalidateDrawRect() {
        final int width = getWidth();
        final int height = getHeight();
        if (width > 0 && height > 0) {
            invalidateDrawRect(width, height);
        }
    }

    private void invalidateDrawRect(final int width, final int height) {
        final float thickness;
        if (mDrawBackgroundStroke) {
            thickness = Math.max(mForegroundStrokePaint.getStrokeWidth(), mBackgroundStrokePaint.getStrokeWidth());
        } else {
            thickness = mForegroundStrokePaint.getStrokeWidth();
        }
        if (width > height) {
            final float offset = (width - height) / 2f;
            mDrawRect.set(offset + thickness / 2f + 1f, thickness / 2f + 1f, width - offset - thickness / 2f - 1f,
                    height - thickness / 2f - 1f);
        } else if (width < height) {
            final float offset = (height - width) / 2f;
            mDrawRect.set(thickness / 2f + 1f, offset + thickness / 2f + 1f, width - thickness / 2f - 1f,
                    height - offset - thickness / 2f - 1f);
        } else {
            mDrawRect.set(thickness / 2f + 1f, thickness / 2f + 1f, width - thickness / 2f - 1f,
                    height - thickness / 2f - 1f);
        }
        invalidateForegroundStrokeCapAngle();
    }

    private void invalidateForegroundStrokeCapAngle() {
        final Paint.Cap strokeCap = mForegroundStrokePaint.getStrokeCap();
        if (strokeCap == null) {
            mForegroundStrokeCapAngle = 0f;
            return;
        }
        switch (strokeCap) {
            case SQUARE:
            case ROUND: {
                final float r = mDrawRect.width() / 2f;
                if (r != 0) {
                    mForegroundStrokeCapAngle = 90f * mForegroundStrokePaint.getStrokeWidth() / (float) Math.PI / r;
                } else {
                    mForegroundStrokeCapAngle = 0f;
                }
                break;
            }
            case BUTT:
            default: {
                mForegroundStrokeCapAngle = 0f;
                break;
            }
        }
    }

    private void setProgressInternal(final float progress) {
        mProgress = progress > mMaximum ? progress - mMaximum : progress;
        invalidate();
    }

    @NonNull
    private static Paint.Cap getStrokeCap(final int value) {
        switch (value) {
            case 2: {
                return Paint.Cap.SQUARE;
            }
            case 1: {
                return Paint.Cap.ROUND;
            }
            case 0:
            default: {
                return Paint.Cap.BUTT;
            }
        }
    }
}