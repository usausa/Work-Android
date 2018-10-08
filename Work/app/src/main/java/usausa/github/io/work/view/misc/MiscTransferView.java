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

import java.util.List;

import usausa.github.io.work.R;
import usausa.github.io.work.databinding.ItemMiscTransferBinding;
import usausa.github.io.work.service.transfer.DeviceInformation;
import usausa.github.io.work.view.AppViewBase;
import usausa.github.io.work.view.ViewId;
import usausa.github.io.work.view.helper.ItemClickHandler;

public class MiscTransferView extends AppViewBase implements ItemClickHandler<String> {

    public final ObservableBoolean executing = new ObservableBoolean();

    public final ObservableArrayList<DeviceInformation> list = new ObservableArrayList<>();

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
        addDisposable(getTransferService().getThisDeviceObservable()
                .subscribe(information::set));
        addDisposable(getTransferService().getPeerDeviceObservable()
                .subscribe(x -> {
                    // TODO
                    selected.set(false);
                    list.clear();
                    list.addAll(x);
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
//        SelectedItem<DeviceInformation> item = list.get(position);
//        item.setSelected(!item.isSelected());
//        selected.set(item.isSelected());
    }

    @Override
    public void onClickItem(String item) {
        getTransferService().connect(item);
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
        //getTransferService().transfer(device.getAddress());
    }

    //--------------------------------------------------------------------------------
    // Adaptor
    //--------------------------------------------------------------------------------

    private static final class ListViewAdaptor extends ArrayAdapter<DeviceInformation> {

        private final ItemClickHandler<String> handler;

        public ListViewAdaptor(@NonNull final Context context, final List<DeviceInformation> objects, final ItemClickHandler<String> handler) {
            super(context, 0, objects);
            this.handler = handler;
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
            binding.setHandler(handler);

            return binding.getRoot();
        }
    }

    @BindingAdapter({"list_misc_transfer", "item_click_handler"})
    public static void setList(final ListView listView, final List<DeviceInformation> objects, final ItemClickHandler<String> handler) {
        ListViewAdaptor adaptor = (ListViewAdaptor)listView.getAdapter();
        if (adaptor == null) {
            adaptor = new ListViewAdaptor(listView.getContext(), objects, handler);
            listView.setAdapter(adaptor);
        }

        adaptor.notifyDataSetChanged();
    }}
