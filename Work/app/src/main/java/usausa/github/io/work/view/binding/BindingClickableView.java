package usausa.github.io.work.view.binding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import usausa.github.io.work.R;
import usausa.github.io.work.databinding.ItemBindingClickableBinding;
import usausa.github.io.work.service.data.DataEntity;
import usausa.github.io.work.view.AppViewBase;
import usausa.github.io.work.view.ViewId;
import usausa.github.io.work.view.helper.ItemClickHandler;

public class BindingClickableView extends AppViewBase implements ItemClickHandler<String> {

    public final ObservableBoolean executing = new ObservableBoolean();

    public final ObservableArrayList<DataEntity> list = new ObservableArrayList<>();

    //--------------------------------------------------------------------------------
    // Layout
    //--------------------------------------------------------------------------------

    @Override
    protected int getViewId() {
        return R.layout.view_binding_clickable;
    }

    //--------------------------------------------------------------------------------
    // Initialize
    //--------------------------------------------------------------------------------

    @Override
    protected void onInitialize() {
        list.addAll(getDataService().queryEntityList(20, 0));
    }

    //--------------------------------------------------------------------------------
    // Function
    //--------------------------------------------------------------------------------

    @Override
    public void executeFunction1() {
        getNavigator().navigate(ViewId.MENU);
    }

    @Override
    public void executeFunction4() {
        int id = list.size() + 1;
        DataEntity entity = new DataEntity();
        entity.setId(String.valueOf(id));
        entity.setName(String.format(Locale.getDefault(), "New--%d", id));
        list.add(0, entity);
    }

    //--------------------------------------------------------------------------------
    // Event
    //--------------------------------------------------------------------------------

    @Override
    public void onClickItem(final String id) {
        Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();
    }

    //--------------------------------------------------------------------------------
    // Adaptor
    //--------------------------------------------------------------------------------

    private static final class ListViewAdaptor extends ArrayAdapter<DataEntity> {

        private final ItemClickHandler<String> handler;

        public ListViewAdaptor(@NonNull final Context context, final List<DataEntity> objects, final ItemClickHandler<String> handler) {
            super(context, 0, objects);
            this.handler = handler;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
            ItemBindingClickableBinding binding;

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                binding = DataBindingUtil.inflate(inflater, R.layout.item_binding_clickable, parent, false);

                convertView = binding.getRoot();
                convertView.setTag(binding);
            } else {
                binding = (ItemBindingClickableBinding)convertView.getTag();
            }

            binding.setItem(getItem(position));
            binding.setHandler(handler);

            return binding.getRoot();
        }
    }

    @BindingAdapter({"list_binding_clickable", "item_click_handler"})
    public static void setList(final ListView listView, final List<DataEntity> objects, final ItemClickHandler<String> handler) {
        ListViewAdaptor adaptor = (ListViewAdaptor)listView.getAdapter();
        if (adaptor == null) {
            adaptor = new ListViewAdaptor(listView.getContext(), objects, handler);
            listView.setAdapter(adaptor);
        }

        adaptor.notifyDataSetChanged();
    }
}
