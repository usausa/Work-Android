package usausa.github.io.work.service;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import usausa.github.io.work.BR;

public class DataEntity extends BaseObservable {

    private String id;

    private String name;

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
}
