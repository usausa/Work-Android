package usausa.github.io.work.model;

public class SelectedItem<T> {

    private final T value;

    private  boolean selected;

    public T getValue() {
        return value;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(final boolean selected) {
        this.selected = selected;
    }

    public SelectedItem(final T value) {
        this.value = value;
    }
}
