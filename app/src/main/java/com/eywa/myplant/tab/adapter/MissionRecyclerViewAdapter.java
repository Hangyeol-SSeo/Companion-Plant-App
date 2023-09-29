package com.eywa.myplant.tab.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.eywa.myplant.databinding.FragmentMissionBinding;
import com.eywa.myplant.tab.placeholder.MissionHolderContent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MissionRecyclerViewAdapter extends RecyclerView.Adapter<MissionRecyclerViewAdapter.ViewHolder> {
    private final List<MissionHolderContent.MissionHolderItem> mValues;
    private boolean selectionMode = false;
    private final Set<MissionHolderContent.MissionHolderItem> selectedItems = new HashSet<>();

    public MissionRecyclerViewAdapter(List<MissionHolderContent.MissionHolderItem> items) {
        mValues = new ArrayList<>(items);
    }

    public void addItem(MissionHolderContent.MissionHolderItem item) {
        mValues.add(item);
        notifyDataSetChanged();
    }

    public void removeItem(MissionHolderContent.MissionHolderItem item) {
        mValues.remove(item);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentMissionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.missionName.setText(holder.mItem.missionName);
        holder.missionImage.setText(holder.mItem.missionIcon);
        holder.missionTargetPoint.setText(String.valueOf(holder.mItem.missionPoint));
        holder.missionTargetPlant.setText(holder.mItem.plantname);

        holder.itemView.setOnClickListener(v -> {
            if (selectionMode) {
                toggleSelection(holder.mItem);
                holder.itemView.setBackgroundColor(selectedItems.contains(holder.mItem) ? Color.GRAY : Color.WHITE);
            }
        });
        holder.itemView.setBackgroundColor(selectedItems.contains(holder.mItem) ? Color.GRAY : Color.WHITE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView missionImage;
        public final TextView missionName;
        public final TextView missionTargetPoint;
        public final TextView missionTargetPlant;
        public MissionHolderContent.MissionHolderItem mItem;

        public ViewHolder(FragmentMissionBinding binding) {
            super(binding.getRoot());
            missionImage = binding.missionImage;
            missionName = binding.missionName;
            missionTargetPoint = binding.missionTargetPoint;
            missionTargetPlant = binding.missionTargetPlant;
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

    public List<MissionHolderContent.MissionHolderItem> getSelectedItems() {
        return new ArrayList<>(selectedItems);
    }

    private void toggleSelection(MissionHolderContent.MissionHolderItem item) {
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
