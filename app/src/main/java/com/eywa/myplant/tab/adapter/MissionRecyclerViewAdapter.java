package com.eywa.myplant.tab.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.eywa.myplant.databinding.FragmentMissionBinding;
import com.eywa.myplant.tab.MissionDetail;
import com.eywa.myplant.tab.PlantDetail;
import com.eywa.myplant.tab.placeholder.MissionHolderContent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MissionRecyclerViewAdapter extends RecyclerView.Adapter<MissionRecyclerViewAdapter.ViewHolder> {
    private final List<MissionHolderContent.MissionHolderItem> mValues;

    public MissionRecyclerViewAdapter(List<MissionHolderContent.MissionHolderItem> items) {
        mValues = new ArrayList<>(items);
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
        holder.missionTargetTime.setText(holder.mItem.time);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MissionDetail.class);
            intent.putExtra("missionId", holder.mItem.missionId);
            v.getContext().startActivity(intent);
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView missionImage;
        public final TextView missionName;
        public final TextView missionTargetPoint;
        public final TextView missionTargetTime;
        public MissionHolderContent.MissionHolderItem mItem;

        public ViewHolder(FragmentMissionBinding binding) {
            super(binding.getRoot());
            missionImage = binding.missionImage;
            missionName = binding.missionName;
            missionTargetPoint = binding.missionTargetPoint;
            missionTargetTime = binding.missionTargetTime;
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
