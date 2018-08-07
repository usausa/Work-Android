package usausa.github.io.work.view.helper;

import android.databinding.BindingAdapter;
import android.view.View;

public final class BindingHelper {

    @BindingAdapter("visibility")
    public static void setVisibility(final View view, final boolean value) {
        view.setVisibility(value ? View.VISIBLE : View.INVISIBLE);
    }

    private BindingHelper() {
    }
}
