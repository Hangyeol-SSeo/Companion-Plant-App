package com.eywa.myplant.tab;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eywa.myplant.R;
import com.eywa.myplant.tab.placeholder.PlaceholderContent;
import com.eywa.myplant.tab.placeholder.PlaceholderContent.PlaceholderItem;
import com.eywa.myplant.databinding.FragmentItemBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;
    private boolean selectionMode = false;
    private final Set<PlaceholderItem> selectedItems = new HashSet<>();

    public MyItemRecyclerViewAdapter(List<PlaceholderContent.PlaceholderItem> items) {
        mValues = new ArrayList<>(items);
    }

    public void addItem(PlaceholderContent.PlaceholderItem item) {
        mValues.add(item);
        notifyDataSetChanged();
    }

    public void removeItem(PlaceholderContent.PlaceholderItem item) {
        mValues.remove(item);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.plantImage.setImageURI(holder.mItem.plantImageUri);
        holder.nicknameTextView.setText(holder.mItem.nickname);
        holder.realnameTextView.setText(holder.mItem.realname);

        holder.moistureBar.setProgress((int)holder.mItem.soil_moisture);
        holder.sunlightBar.setProgress((int)holder.mItem.light_intensity);

        if (holder.moistureBar.getProgress() < 50 || holder.sunlightBar.getProgress() < 50) {
            holder.conditionFlag.setImageResource(R.drawable.fragment_item_statement_red);
        } else {
            holder.conditionFlag.setImageResource(R.drawable.fragment_item_statement_green);
        }

        holder.itemView.setOnClickListener(v -> {
            if (selectionMode) {
                toggleSelection(holder.mItem);
                holder.itemView.setBackgroundColor(selectedItems.contains(holder.mItem) ? Color.GRAY : Color.WHITE);
            }
        });
        holder.itemView.setBackgroundColor(selectedItems.contains(holder.mItem) ? Color.GRAY : Color.WHITE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView plantImage;
        public final TextView nicknameTextView;
        public final TextView realnameTextView;
        public PlaceholderItem mItem;
        public ProgressBar moistureBar;
        public ProgressBar sunlightBar;
        public ImageView conditionFlag;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            plantImage = binding.listPlantImage;
            nicknameTextView = binding.listPlantNickname;
            realnameTextView = binding.listPlantRealname;
            moistureBar = binding.listMoistureBar;
            sunlightBar = binding.listSunlightBar;
            conditionFlag = binding.listConditionFlag;
        }
    }

    public void setSelectionMode(boolean enabled) {
        this.selectionMode = enabled;
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public boolean isSelectionMode() {
        return selectionMode;
    }

    public List<PlaceholderItem> getSelectedItems() {
        return new ArrayList<>(selectedItems);
    }

    private void toggleSelection(PlaceholderItem item) {
        if (selectedItems.contains(item)) {
            selectedItems.remove(item);
        } else {
            selectedItems.add(item);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}