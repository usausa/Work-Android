package usausa.github.io.work.service;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import usausa.github.io.work.BR;

public class GroupDataEntity extends BaseObservable {

    private String group;

    private String name;

    @Bindable
    public String getGroup() {
        return group;
    }

    public void setGroup(final String group) {
        this.group = group;
        notifyPropertyChanged(BR.group);
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
