package usausa.github.io.work.model;

public class SelectedItem<T> {

    private final T item;

    private  boolean selected;

    public T getItem() {
        return item;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(final boolean selected) {
        this.selected = selected;
    }

    public SelectedItem(final T item) {
        this.item = item;
    }
}
