package usausa.github.io.work.view.misc;

import android.databinding.ObservableBoolean;

import usausa.github.io.work.R;
import usausa.github.io.work.view.AppViewBase;
import usausa.github.io.work.view.ViewId;

public class MiscTransferView extends AppViewBase {

    //--------------------------------------------------------------------------------
    // Layout
    //--------------------------------------------------------------------------------

    @Override
    protected int getViewId() {
        return R.layout.view_misc_transfer;
    }

    //--------------------------------------------------------------------------------
    // Function
    //--------------------------------------------------------------------------------

    @Override
    public void executeFunction1() {
        getNavigator().navigate(ViewId.MENU);
    }
}
