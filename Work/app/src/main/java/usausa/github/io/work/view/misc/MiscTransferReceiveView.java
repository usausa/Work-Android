package usausa.github.io.work.view.misc;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.widget.Toast;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import usausa.github.io.work.R;
import usausa.github.io.work.service.transfer.ConnectInformation;
import usausa.github.io.work.service.transfer.DeviceInformation;
import usausa.github.io.work.service.transfer.TransferService;
import usausa.github.io.work.view.AppViewBase;
import usausa.github.io.work.view.ViewId;

public class MiscTransferReceiveView extends AppViewBase {

    public final ObservableBoolean executing = new ObservableBoolean();

    public final ObservableField<DeviceInformation> information = new ObservableField<>();

    //--------------------------------------------------------------------------------
    // Layout
    //--------------------------------------------------------------------------------

    @Override
    protected int getViewId() {
        return R.layout.view_misc_transfer_receieve;
    }

    //--------------------------------------------------------------------------------
    // Initialize
    //--------------------------------------------------------------------------------

    @Override
    protected void onInitialize() {
        addDisposable(getTransferService().getThisDeviceObservable()
                .subscribe(information::set));
        addDisposable(getTransferService().getConnectObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::startTransfer));

        getTransferService().start();
        getTransferService().startDiscover();
    }

    @Override
    protected void onDispose() {
        getTransferService().stopDiscover();
        getTransferService().stop();
    }

    //--------------------------------------------------------------------------------
    // Function
    //--------------------------------------------------------------------------------

    @Override
    public void executeFunction1() {
        getNavigator().navigate(ViewId.MENU);
    }

    //--------------------------------------------------------------------------------
    // Helper
    //--------------------------------------------------------------------------------

    private void startTransfer(final ConnectInformation info) {
        if (!info.isSuccess()) {
            restart();
            return;
        }

        executing.set(true);
        addDisposable(Single.fromCallable(() -> getTransferService().transfer(info.isOwner() ? null : info.getAddress(), TransferService.Operation.RECEIVE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            if (result) {
                                getNavigator().navigate(ViewId.MENU);
                            } else {
                                executing.set(false);
                                restart();
                            }
                        },
                        t -> {
                            Timber.e(t);
                            Toast.makeText(getContext(), executing.toString(), Toast.LENGTH_LONG).show();

                            executing.set(false);
                            restart();
                        }));
    }

    private void restart() {
        getTransferService().stopDiscover();
        getTransferService().stop();

        getTransferService().start();
        getTransferService().startDiscover();
    }
}
