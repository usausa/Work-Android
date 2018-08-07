package usausa.github.io.work.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import usausa.github.io.work.BR;

public class SelectedItem<T> extends BaseObservable {

    private final T value;

    private  boolean selected;

    @Bindable
    public T getValue() {
        return value;
    }

    @Bindable
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(final boolean selected) {
        this.selected = selected;
        notifyPropertyChanged(BR.selected);
    }

    public SelectedItem(final T value) {
        this.value = value;
    }
}
