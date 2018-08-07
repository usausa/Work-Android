package usausa.github.io.work.view.binding;

import android.support.annotation.NonNull;
import android.view.View;

import usausa.github.io.work.R;
import usausa.github.io.work.view.AppViewBase;
import usausa.github.io.work.view.ViewId;

public class BindingMiscView extends AppViewBase {

    //--------------------------------------------------------------------------------
    // Layout
    //--------------------------------------------------------------------------------

    @Override
    protected int getViewId() {
        return R.layout.view_binding_misc;
    }

    //--------------------------------------------------------------------------------
    // Initialize
    //--------------------------------------------------------------------------------

    @Override
    protected void onInitialize(@NonNull final View view) {
    }

    //--------------------------------------------------------------------------------
    // Function
    //--------------------------------------------------------------------------------

    @Override
    public void executeFunction1() {
        getNavigator().navigate(ViewId.MENU);
    }
}
