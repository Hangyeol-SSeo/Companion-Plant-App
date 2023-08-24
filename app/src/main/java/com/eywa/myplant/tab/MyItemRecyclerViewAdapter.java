package com.eywa.myplant.tab;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eywa.myplant.R;
import com.eywa.myplant.tab.placeholder.PlaceholderContent.PlaceholderItem;
import com.eywa.myplant.databinding.FragmentItemBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;

    public MyItemRecyclerViewAdapter(List<PlaceholderItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.nicknameTextView.setText(holder.mItem.nickname);
        holder.realnameTextView.setText(holder.mItem.realname);

        //holder.moistureBar.setProgress(51);
        //holder.sunlightBar.setProgress(51);

        if (holder.moistureBar.getProgress() < 50 || holder.sunlightBar.getProgress() < 50) {
            holder.conditionFlag.setImageResource(R.drawable.fragment_item_statement_red);
        } else {
            holder.conditionFlag.setImageResource(R.drawable.fragment_item_statement_green);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView nicknameTextView;
        public final TextView realnameTextView;
        public PlaceholderItem mItem;
        public ProgressBar moistureBar;
        public ProgressBar sunlightBar;
        public ImageView conditionFlag;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            nicknameTextView = binding.listPlantNickname;
            realnameTextView = binding.listPlantRealname;
            moistureBar = binding.listMoistureBar;
            sunlightBar = binding.listSunlightBar;
            conditionFlag = binding.listConditionFlag;
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}