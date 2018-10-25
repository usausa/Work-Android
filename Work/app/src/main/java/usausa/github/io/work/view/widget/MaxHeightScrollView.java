package usausa.github.io.work.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import usausa.github.io.work.R;

public class MaxHeightScrollView extends ScrollView {

    private final int defaultMaxHeight = 480;

    private int maxHeight = defaultMaxHeight;

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(final int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public MaxHeightScrollView(final Context context) {
        super(context, null);
    }

    public MaxHeightScrollView(final Context context, final AttributeSet attrs) {
        super(context, attrs, 0);
        initialize(context, attrs);
    }

    public MaxHeightScrollView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        initialize(context, attrs);
    }

    private void initialize(final Context context, final AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightScrollView);

        try {
            setMaxHeight(a.getDimensionPixelSize(R.styleable.MaxHeightScrollView_maxHeight, defaultMaxHeight));
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(maxHeight, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        try {
//            int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//            if (heightSize > maxHeight) {
//                heightSize = maxHeight;
//            }
//
//            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.AT_MOST);
//            getLayoutParams().height = heightSize;
//        } catch (Exception e) {
//            //
//        } finally {
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        }
//    }
}
