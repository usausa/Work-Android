package usausa.github.io.work.model;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import java.util.Date;

public class TraceStorage {

    private final ObservableArrayList<TraceEntry> entries = new ObservableArrayList<>();

    public ObservableList<TraceEntry> getEntries() {
        return entries;
    }

    public void AddEntry(final EventType type, final String message) {
        TraceEntry entry = new TraceEntry(new Date(), type, message);
        entries.add(entry);
    }
}
