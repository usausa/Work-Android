package usausa.github.io.work.view.helper;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ListView;

public final class BindingHelper {

    @BindingAdapter("android:visibility")
    public static void setVisibility(final View view, final boolean value) {
        view.setVisibility(value ? View.VISIBLE : View.INVISIBLE);
    }

    @BindingAdapter("app:selectCommand")
    public static void setSelectCommand(final ListView listView, final SelectedCommand command) {
        listView.setOnItemClickListener((parent, view, position, id) -> command.execute(position));
    }

    private BindingHelper() {
    }
}
