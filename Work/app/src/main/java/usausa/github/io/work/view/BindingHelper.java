package usausa.github.io.work.view;

import android.databinding.BindingAdapter;
import android.view.View;

public final class BindingHelper {

    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, boolean value) {
        view.setVisibility(value ? View.VISIBLE : View.INVISIBLE);
    }

    private BindingHelper() {
    }
}
