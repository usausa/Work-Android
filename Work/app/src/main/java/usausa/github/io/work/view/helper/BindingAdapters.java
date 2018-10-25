package usausa.github.io.work.view.helper;

import android.annotation.SuppressLint;
import android.databinding.BindingAdapter;
import android.view.MotionEvent;
import android.view.View;

public final class BindingAdapters {

    @BindingAdapter("visibility")
    public static void setVisibility(final View view, final boolean value) {
        view.setVisibility(value ? View.VISIBLE : View.INVISIBLE);
    }

    @BindingAdapter("using")
    public static void setUsing(final View view, final boolean value) {
        view.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    @SuppressLint("ClickableViewAccessibility")
    @BindingAdapter("nested_scroll")
    public static void setNestedScroll(final View view, final boolean value) {
        view.setOnTouchListener((v, event) -> {
            v.getParent().requestDisallowInterceptTouchEvent(value);
            return false;
        });
    }

    private BindingAdapters() {
    }
}
