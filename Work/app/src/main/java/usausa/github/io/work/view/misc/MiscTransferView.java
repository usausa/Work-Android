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

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import usausa.github.io.work.R;
import usausa.github.io.work.databinding.ItemMiscTransferBinding;
import usausa.github.io.work.model.SelectedItem;
import usausa.github.io.work.service.transfer.DeviceInformation;
import usausa.github.io.work.view.AppViewBase;
import usausa.github.io.work.view.ViewId;

public class MiscTransferView extends AppViewBase {

    public enum State {
        NONE,
        DISCOVER,
        TRANSFER
    }

    public final ObservableField<State> executing = new ObservableField<>(State.NONE);

    public final ObservableArrayList<SelectedItem<DeviceInformation>> list = new ObservableArrayList<>();

    public final ObservableField<DeviceInformation> information = new ObservableField<>();

    public final ObservableBoolean selected = new ObservableBoolean();

    //--------------------------------------------------------------------------------
    // Layout
    //--------------------------------------------------------------------------------

    @Override
    protected int getViewId() {
        return R.layout.view_misc_transfer;
    }

    //--------------------------------------------------------------------------------
    // Initialize
    //--------------------------------------------------------------------------------

    @Override
    protected void onInitialize() {
        addDisposable(getTransferService().getConnectedObservable()
                .subscribe(x -> { /* ? */ }));
        addDisposable(getTransferService().getThisDeviceObservable()
                .subscribe(information::set));
        addDisposable(getTransferService().getPeerDeviceObservable()
                .subscribe(x -> {
                    selected.set(false);
                    list.clear();
                    list.addAll(Stream.of(x).map(SelectedItem::new).collect(Collectors.toList()));

                    executing.set(State.NONE);
                }));
        addDisposable(getTransferService().getConnectionObservable()
                .subscribe(x -> {

                    executing.set(State.NONE);
                }));

        getTransferService().start();

        getTransferService().discover();
        executing.set(State.DISCOVER);
    }

    @Override
    protected void onDispose() {
        getTransferService().stopDiscover();
        getTransferService().stop();
    }

    //--------------------------------------------------------------------------------
    // Event
    //--------------------------------------------------------------------------------

    public void selectList(final int position) {
        SelectedItem<DeviceInformation> item = list.get(position);
        item.setSelected(!item.isSelected());
        selected.set(item.isSelected());
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
        if (executing.get() == State.NONE) {
            selected.set(false);
            list.clear();
            getTransferService().discover();
            executing.set(State.DISCOVER);
        } else if (executing.get() == State.DISCOVER) {
            executing.set(State.NONE);
        }
    }

    @Override
    public void executeFunction3() {
    }

    @Override
    public void executeFunction4() {
        DeviceInformation device = Stream.of(list).filter(SelectedItem::isSelected).findFirst().get().getValue();
        executing.set(State.TRANSFER);
        getTransferService().transfer(device.getAddress());
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
