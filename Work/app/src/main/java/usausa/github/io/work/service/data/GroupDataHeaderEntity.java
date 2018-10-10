package usausa.github.io.work.service.data;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import usausa.github.io.work.BR;

public class GroupDataHeaderEntity extends BaseObservable {

    private int selected;

    @Bindable
    public int getSelected() {
        return selected;
    }

    public void setSelected(final int selected) {
        this.selected = selected;
        notifyPropertyChanged(BR.selected);
    }
}
