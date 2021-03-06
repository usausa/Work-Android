package usausa.github.io.work.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import usausa.github.io.work.BR;

public class GroupingItem<THeader, TValue> extends BaseObservable {

    private enum GroupingItemType {
        SINGLE,
        HEADER,
        CHILD
    }

    private final GroupingItemType itemType;

    private final TValue value;

    private final THeader headerValue;

    private final List<GroupingItem<THeader, TValue>> children;

    private boolean expanded;

    private boolean selected;

    private boolean visible;

    public boolean isHeader() {
        return GroupingItemType.HEADER.equals(itemType);
    }

    public boolean isChild() {
        return GroupingItemType.CHILD.equals(itemType);
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(final boolean expanded) {
        this.expanded = expanded;
        for (GroupingItem<THeader, TValue> child : children) {
            child.setVisible(expanded);
        }
    }

    @Bindable
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(final boolean selected) {
        this.selected = selected;
        notifyPropertyChanged(BR.selected);
    }

    @Bindable
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(final boolean visible) {
        this.visible = visible;
        notifyPropertyChanged(BR.visible);
    }

    @Bindable
    public TValue getValue() {
        return value;
    }

    public THeader getHeaderValue() {
        return headerValue;
    }

    private GroupingItem(final GroupingItemType itemType, final TValue value, final THeader headerValue, final List<GroupingItem<THeader, TValue>> children) {
        this.itemType = itemType;
        this.value = value;
        this.headerValue = headerValue;
        this.children = children;
        this.visible = !isChild();
    }

    public static <THeader, TValue> GroupingItem<THeader, TValue> MakeHeader(THeader header, final List<TValue> children) {
        return new GroupingItem<>(GroupingItemType.HEADER, children.get(0), header, Stream.of(children).map(x -> MakeChild(x, header)).collect(Collectors.toList()));
    }

    private static <THeader, TValue> GroupingItem<THeader, TValue> MakeChild(TValue value, THeader headerValue) {
        return new GroupingItem<>(GroupingItemType.CHILD, value, headerValue, null);
    }

    public static <THeader, TValue> GroupingItem<THeader, TValue> MakeSingle(TValue value) {
        return new GroupingItem<>(GroupingItemType.SINGLE, value, null, null);
    }

    @SuppressWarnings("unchecked")
    public Stream<GroupingItem<THeader, TValue>> stream() {
        if (isHeader()) {
            return Stream.concat(Stream.of(this), Stream.of(children));
        } else {
            return Stream.of(this);
        }
    }
}
