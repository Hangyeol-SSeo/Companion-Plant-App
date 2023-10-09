package com.eywa.myplant.tab.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eywa.myplant.R;
import com.eywa.myplant.tab.placeholder.ArchiveCategoryHolderContent;

import java.util.ArrayList;
import java.util.List;

public class ArchiveCategoryRecyclerViewAdapter extends RecyclerView.Adapter<ArchiveCategoryRecyclerViewAdapter.ArchiveCategoryViewHolder> {
    private final List<ArchiveCategoryHolderContent> categories;

    public ArchiveCategoryRecyclerViewAdapter(List<ArchiveCategoryHolderContent> categories) {
        this.categories = categories;
    }

    @Override
    public ArchiveCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_archive_category, parent, false);
        return new ArchiveCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArchiveCategoryViewHolder holder, int position) {
        ArchiveCategoryHolderContent category = categories.get(position);
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void filter(String query) {
        for (ArchiveCategoryHolderContent category : categories) {
            category.getArchiveRecyclerViewAdapter().getFilter().filter(query);
        }
    }

    class ArchiveCategoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView categoryTitle;
        private final RecyclerView archiveRecyclerView;
        private final ArchiveRecyclerViewAdapter archiveRecyclerViewAdapter;

        public ArchiveCategoryViewHolder(View itemView) {
            super(itemView);
            categoryTitle = itemView.findViewById(R.id.archive_category_index);
            archiveRecyclerView = itemView.findViewById(R.id.archive_category_recycler_list);
            archiveRecyclerViewAdapter = new ArchiveRecyclerViewAdapter(new ArrayList<>());  // Initialize with empty list
            archiveRecyclerView.setAdapter(archiveRecyclerViewAdapter);
            archiveRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }

        public void bind(ArchiveCategoryHolderContent category) {
            categoryTitle.setText(category.categoryName.toString());
            archiveRecyclerViewAdapter.setItems(category.archiveRecyclerViewAdapter.getItems());  // Assume getItems() returns the list of items
        }
    }
}
