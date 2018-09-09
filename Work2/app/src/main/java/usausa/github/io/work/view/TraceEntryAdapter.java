package usausa.github.io.work.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import usausa.github.io.work.R;
import usausa.github.io.work.databinding.ItemEntryBinding;
import usausa.github.io.work.model.TraceEntry;

public class TraceEntryAdapter extends ArrayAdapter<TraceEntry> {

    public TraceEntryAdapter(@NonNull final Context context, final List<TraceEntry> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull final ViewGroup parent) {
        ItemEntryBinding binding;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            binding = DataBindingUtil.inflate(inflater, R.layout.item_entry, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ItemEntryBinding)convertView.getTag();
        }

        binding.setItem(getItem(position));

        return binding.getRoot();
    }
}
