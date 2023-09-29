package com.eywa.myplant.tab.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.eywa.myplant.databinding.FragmentArchiveBinding;
import com.eywa.myplant.tab.placeholder.ArchiveHolderContent;

import org.w3c.dom.Text;

import java.util.List;

public class ArchiveRecyclerViewAdapter extends RecyclerView.Adapter<ArchiveRecyclerViewAdapter.ArchiveViewHolder> {
    private List<ArchiveHolderContent> items;

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
        }

        public void bind(ArchiveHolderContent item) {
            plantImage.setImageURI(item.imageUri);
            plantNameKor.setText(item.plantNameKor);
            species.setText(item.species);
            scientificName.setText(item.scientificName);
        }
    }
}


