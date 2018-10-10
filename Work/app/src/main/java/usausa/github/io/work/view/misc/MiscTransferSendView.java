package usausa.github.io.work.view.misc;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import usausa.github.io.work.R;
import usausa.github.io.work.databinding.ItemMiscTransferBinding;
import usausa.github.io.work.model.SelectedItem;
import usausa.github.io.work.service.transfer.ConnectInformation;
import usausa.github.io.work.service.transfer.DeviceInformation;
import usausa.github.io.work.service.transfer.TransferService;
import usausa.github.io.work.view.AppViewBase;
import usausa.github.io.work.view.ViewId;

public class MiscTransferSendView extends AppViewBase {

    public final ObservableBoolean executing = new ObservableBoolean();

    public final ObservableField<DeviceInformation> information = new ObservableField<>();

    public final ObservableArrayList<SelectedItem<DeviceInformation>> list = new ObservableArrayList<>();

    public final ObservableBoolean executable = new ObservableBoolean();

    private SelectedItem<DeviceInformation> lastSelected;

    //--------------------------------------------------------------------------------
    // Layout
    //--------------------------------------------------------------------------------

    @Override
    protected int getViewId() {
        return R.layout.view_misc_transfer_send;
    }

    //--------------------------------------------------------------------------------
    // Initialize
    //--------------------------------------------------------------------------------

    @Override
    protected void onInitialize() {
        addDisposable(getTransferService().getThisDeviceObservable()
                .subscribe(information::set));
        addDisposable(getTransferService().getConnectObservable()
                .subscribe(this::startTransfer));
        addDisposable(getTransferService().getPeerDeviceObservable()
                .subscribe(x -> {
                    lastSelected = null;
                    executable.set(false);

                    list.clear();
                    Stream.of(x).map(SelectedItem::new).collect(Collectors.toCollection(() -> list));
                }));

        getTransferService().start();
        getTransferService().startDiscover();
    }

    @Override
    protected void onDispose() {
        getTransferService().stopDiscover();
        getTransferService().stop();
    }

    //--------------------------------------------------------------------------------
    // Event
    //--------------------------------------------------------------------------------

    public void onItemClick(final int position) {
        SelectedItem<DeviceInformation> item = list.get(position);
        if (lastSelected == item) {
            lastSelected.setSelected(!lastSelected.isSelected());
        } else {
            if (lastSelected != null) {
                lastSelected.setSelected(false);
            }

            item.setSelected(true);
            lastSelected = item;
        }

        executable.set(lastSelected.isSelected());
    }

    //--------------------------------------------------------------------------------
    // Function
    //--------------------------------------------------------------------------------

    @Override
    public void executeFunction1() {
        getNavigator().navigate(ViewId.MENU);
    }

    @Override
    public void executeFunction2() {
        getTransferService().stopDiscover();
        getTransferService().startDiscover();
    }

    @Override
    public void executeFunction3() {
    }

    @Override
    public void executeFunction4() {
        getTransferService().connect(lastSelected.getValue().getAddress());
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
        addDisposable(Single.fromCallable(() -> getTransferService().transfer(info.isOwner() ? null : info.getAddress(), TransferService.Operation.SEND))
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

    //--------------------------------------------------------------------------------
    // Adaptor
    //--------------------------------------------------------------------------------

    private static final class ListViewAdaptor extends ArrayAdapter<SelectedItem<DeviceInformation>> {

        public ListViewAdaptor(@NonNull final Context context, final List<SelectedItem<DeviceInformation>> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
            ItemMiscTransferBinding binding;

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                binding = DataBindingUtil.inflate(inflater, R.layout.item_misc_transfer, parent, false);

                convertView = binding.getRoot();
                convertView.setTag(binding);
            } else {
                binding = (ItemMiscTransferBinding)convertView.getTag();
            }

            binding.setItem(getItem(position));

            return binding.getRoot();
        }
    }

    @BindingAdapter("list_misc_transfer")
    public static void setList(final ListView listView, final List<SelectedItem<DeviceInformation>> objects) {
        ListViewAdaptor adaptor = (ListViewAdaptor)listView.getAdapter();
        if (adaptor == null) {
            adaptor = new ListViewAdaptor(listView.getContext(), objects);
            listView.setAdapter(adaptor);
        }

        adaptor.notifyDataSetChanged();
    }}
