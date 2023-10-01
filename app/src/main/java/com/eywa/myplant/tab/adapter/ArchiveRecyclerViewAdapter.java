package com.eywa.myplant.tab.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eywa.myplant.databinding.FragmentArchiveBinding;
import com.eywa.myplant.tab.ArchiveFragment;
import com.eywa.myplant.tab.placeholder.ArchiveHolderContent;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ArchiveRecyclerViewAdapter extends RecyclerView.Adapter<ArchiveRecyclerViewAdapter.ArchiveViewHolder> implements Filterable {
    private List<ArchiveHolderContent> items;
    private List<ArchiveHolderContent> itemsFiltered;  // 필터링된 항목을 유지하는 리스트

    public ArchiveRecyclerViewAdapter(List<ArchiveHolderContent> items) {
        this.items = items;
    }

    @Override
    public ArchiveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FragmentArchiveBinding binding = FragmentArchiveBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ArchiveViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ArchiveViewHolder holder, int position) {
        ArchiveHolderContent item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    itemsFiltered = items;  // 필터링되지 않은 전체 목록
                } else {
                    List<ArchiveHolderContent> filteredList = new ArrayList<>();
                    for (ArchiveHolderContent row : items) {
                        if (row.plantNameKor.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    itemsFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = itemsFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                itemsFiltered = (ArrayList<ArchiveHolderContent>) results.values;
                notifyDataSetChanged();  // 데이터가 변경되었음을 알리고 UI를 갱신합니다.
            }
        };
    }

    public void setItems(List<ArchiveHolderContent> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public List<ArchiveHolderContent> getItems() {
        return this.items;
    }

    class ArchiveViewHolder extends RecyclerView.ViewHolder {
        public final ImageView plantImage;
        public final TextView plantNameKor;
        public final TextView species;
        public final TextView scientificName;

        public ArchiveViewHolder(FragmentArchiveBinding binding) {
            super(binding.getRoot());
            plantImage = binding.archivePlantImage;
            plantNameKor = binding.archivePlantKor;
            species = binding.archivePlantSpecies;
            scientificName = binding.archivePlantScientific;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        ArchiveHolderContent item = items.get(position);
                        // Access the webView from ArchiveFragment and load the URL
                        ArchiveFragment.webView.loadUrl(item.pageUri.toString());
                        ArchiveFragment.webView.setVisibility(View.VISIBLE);
                        ArchiveFragment.linearLayout.setVisibility(View.GONE);
                    }
                }
            });
        }

        public void bind(ArchiveHolderContent item) {
            Glide.with(plantImage.getContext())
                    .load(item.imageUri)
                    .into(plantImage);
            plantNameKor.setText(item.plantNameKor);
            species.setText(item.species);
            scientificName.setText(item.scientificName);
        }
    }
}


