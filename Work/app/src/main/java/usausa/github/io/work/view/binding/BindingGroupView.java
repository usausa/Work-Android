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

import java.util.Arrays;
import java.util.List;

import usausa.github.io.work.R;
import usausa.github.io.work.databinding.ItemBindingGroupBinding;
import usausa.github.io.work.databinding.ItemBindingGroupHeaderBinding;
import usausa.github.io.work.model.GroupingHeader;
import usausa.github.io.work.model.GroupingItem;
import usausa.github.io.work.service.data.GroupDataEntity;
import usausa.github.io.work.view.AppViewBase;
import usausa.github.io.work.view.ViewId;

public class BindingGroupView extends AppViewBase {

    public final ObservableBoolean executing = new ObservableBoolean();

    public final ObservableArrayList<Object> list = new ObservableArrayList<>();

    //--------------------------------------------------------------------------------
    // Layout
    //--------------------------------------------------------------------------------

    @Override
    protected int getViewId() {
        return R.layout.view_binding_group;
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
        GroupDataEntity dataA1 = new GroupDataEntity();
        dataA1.setGroup("A");
        dataA1.setName("A-1");
        GroupDataEntity dataA2 = new GroupDataEntity();
        dataA2.setGroup("A");
        dataA2.setName("A-2");
        GroupDataEntity dataB1 = new GroupDataEntity();
        dataB1.setGroup("B");
        dataB1.setName("B-1");

        list.add(new GroupingHeader<>(Arrays.asList(new GroupingItem<>(dataA1), new GroupingItem<>(dataA2))));
        list.add(new GroupingHeader<>(Arrays.asList(new GroupingItem<>(dataB1))));
    }

    //--------------------------------------------------------------------------------
    // Event
    //--------------------------------------------------------------------------------

    public void selectList(final int position) {
        Object entry = list.get(position);
        if (entry instanceof GroupingHeader) {
            GroupingHeader header = ((GroupingHeader)entry);
            header.setExpanded(!header.isExpanded());
            if (header.isExpanded()) {
                list.addAll(position + 1, header.getItems());
            } else {
                list.subList(position + 1, position + 1 + header.getItems().size()).clear();
            }
        } else if (entry instanceof GroupingItem) {
            GroupingItem item = (GroupingItem)entry;
            item.setSelected(!item.isSelected());
        }
    }

    //--------------------------------------------------------------------------------
    // Adaptor
    //--------------------------------------------------------------------------------

    private static final class ListViewAdaptor extends ArrayAdapter<Object> {

        private static final int TYPE_HEADER= 0;
        private static final int TYPE_ITEM = 1;
        private static final int COUNT_TYPES = TYPE_ITEM + 1;

        public ListViewAdaptor(@NonNull final Context context, final List<Object> objects) {
            super(context, 0, objects);
        }

        @Override
        public int getItemViewType(int position) {
            Object entry = getItem(position);
            if (entry instanceof GroupingHeader) {
                return TYPE_HEADER;
            } else {
                return TYPE_ITEM;
            }
        }

        @Override
        public int getViewTypeCount() {
            return COUNT_TYPES;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
            Object entry = getItem(position);
            if (entry instanceof GroupingHeader) {
                GroupingHeader<GroupDataEntity> header = ((GroupingHeader<GroupDataEntity>)entry);
                ItemBindingGroupHeaderBinding binding;

                if (convertView != null) {
                    binding = (ItemBindingGroupHeaderBinding)convertView.getTag();
                } else {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    binding = DataBindingUtil.inflate(inflater, R.layout.item_binding_group_header, parent, false);
                    convertView = binding.getRoot();
                    convertView.setTag(binding);
                }

                binding.setItem(header);
                return binding.getRoot();
            } else {
                GroupingItem<GroupDataEntity> item = (GroupingItem<GroupDataEntity>)entry;
                ItemBindingGroupBinding binding;

                if (convertView != null) {
                    binding = (ItemBindingGroupBinding)convertView.getTag();
                } else {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    binding = DataBindingUtil.inflate(inflater, R.layout.item_binding_group, parent, false);
                    convertView = binding.getRoot();
                    convertView.setTag(binding);
                }

                binding.setItem(item);
                return binding.getRoot();
            }
        }
    }

    @BindingAdapter("list_binding_group")
    public static void setList(final ListView listView, final List<Object> objects) {
        ListViewAdaptor adaptor = (ListViewAdaptor)listView.getAdapter();
        if (adaptor == null) {
            adaptor = new ListViewAdaptor(listView.getContext(), objects);
            listView.setAdapter(adaptor);
        }

        adaptor.notifyDataSetChanged();
    }
}
