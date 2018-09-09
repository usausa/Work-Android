package usausa.github.io.work.view;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import usausa.github.io.work.model.TraceEntry;

public final class BindingHelper {

    @BindingAdapter("entry_color")
    public static void setEntryColor(final TextView view, final TraceEntry entry) {
        int color;
        switch (entry.getType()) {
            case ERROR:
                color = Color.argb(0xFF, 0xB9, 0x1D, 0x47);
                break;
            case WARN:
                color = Color.argb(0xFF, 0xE3, 0xA2, 0x1A);
                break;
            case DEBUG:
                color = Color.argb(0xFF, 0x2B, 0x57, 0x97);
                break;
            default:
                color = Color.argb(0xFF, 0x00, 0xA3, 0x00);
                break;
        }

        view.setTextColor(color);
    }

    @BindingAdapter("list_trace_entry")
    public static void setListEntryEdit(final ListView listView, final List<TraceEntry> objects) {
        TraceEntryAdapter adapter = (TraceEntryAdapter)listView.getAdapter();
        if (adapter == null) {
            adapter = new TraceEntryAdapter(listView.getContext(), objects);
            listView.setAdapter(adapter);
        }

        adapter.notifyDataSetChanged();
    }
    private BindingHelper() {
    }
}
