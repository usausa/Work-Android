package usausa.github.io.work.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import usausa.github.io.work.BR;

public class GroupingHeader<T> extends BaseObservable {

    private final T value;

    private final List<GroupingItem<T>> items;

    private boolean expanded;

    @Bindable
    public T getValue() {
        return value;
    }

    @Bindable
    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(final boolean expanded) {
        this.expanded = expanded;
        notifyPropertyChanged(BR.expanded);
    }

    public List<GroupingItem<T>> getItems() {
        return items;
    }

    public GroupingHeader(final Collection<GroupingItem<T>> items) {
        this.items = new ArrayList<>(items);
        this.value = this.items.get(0).getValue();
    }
}
