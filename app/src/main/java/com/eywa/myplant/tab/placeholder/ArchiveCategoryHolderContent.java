package com.eywa.myplant.tab.placeholder;

import com.eywa.myplant.tab.adapter.ArchiveRecyclerViewAdapter;

public class ArchiveCategoryHolderContent {
    public final String categoryName;
    public final ArchiveRecyclerViewAdapter archiveRecyclerViewAdapter;

    public ArchiveRecyclerViewAdapter getArchiveRecyclerViewAdapter() {
        return archiveRecyclerViewAdapter;
    }

    public ArchiveCategoryHolderContent(String categoryName, ArchiveRecyclerViewAdapter archiveRecyclerViewAdapter) {
        this.categoryName = categoryName;
        this.archiveRecyclerViewAdapter = archiveRecyclerViewAdapter;
    }
}
