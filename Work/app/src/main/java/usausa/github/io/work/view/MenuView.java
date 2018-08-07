package usausa.github.io.work.view;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.view.View;

import usausa.github.io.work.R;

public class MenuView extends AppViewBase {

    //--------------------------------------------------------------------------------
    // Layout
    //--------------------------------------------------------------------------------

    @Override
    protected int getViewId() {
        return R.layout.view_menu;
    }

    @Override
    protected boolean isDisplayUser() {
        return false;
    }

    //--------------------------------------------------------------------------------
    // Initialize
    //--------------------------------------------------------------------------------

    //--------------------------------------------------------------------------------
    // Event
    //--------------------------------------------------------------------------------

    public void executeList() {
        getNavigator().navigate(ViewId.BINDING_LIST);
    }

    public void executeBinding() {
        getNavigator().navigate(ViewId.BINDING_MISC);
    }


    public final ObservableBoolean testEnable = new ObservableBoolean(true);

    @Override
    public void executeFunction1() {
        testEnable.set(!testEnable.get());
    }
}
