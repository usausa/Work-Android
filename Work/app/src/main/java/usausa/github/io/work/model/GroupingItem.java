package usausa.github.io.work.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.List;

import usausa.github.io.work.BR;

public class GroupingItem<T> extends BaseObservable {

    private final GroupingItemType itemType;

    private final T value;

    private final List<GroupingItem<T>> children;

    private boolean expanded;

    private boolean selected;

    public boolean isHeader() {
        return GroupingItemType.HEADER.equals(itemType);
    }

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

    @Bindable
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(final boolean selected) {
        this.selected = selected;
        notifyPropertyChanged(BR.selected);
    }

    public List<GroupingItem<T>> getChildren() {
        return children;
    }

    public GroupingItem(final GroupingItemType itemType, final T value, final List<GroupingItem<T>> children) {
        this.itemType = itemType;
        this.value = value;
        this.children = children;
    }

    public static <T> GroupingItem MakeHeader(final List<GroupingItem<T>> children) {
        return new GroupingItem<T>(GroupingItemType.HEADER, children.get(0).getValue(), children);
    }

    public static <T> GroupingItem MakeChild(T value) {
        return new GroupingItem<T>(GroupingItemType.CHILD, value, null);
    }

    public static <T> GroupingItem MakeSingle(T value) {
        return new GroupingItem<T>(GroupingItemType.SINGLE, value, null);
    }
}
