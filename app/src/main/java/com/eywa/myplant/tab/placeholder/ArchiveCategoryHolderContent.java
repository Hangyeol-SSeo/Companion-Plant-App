package com.eywa.myplant.tab.placeholder;

import com.eywa.myplant.tab.adapter.ArchiveRecyclerViewAdapter;

public class ArchiveCategoryHolderContent {
    public final java.lang.String categoryName;
    public final ArchiveRecyclerViewAdapter archiveRecyclerViewAdapter;

    public ArchiveRecyclerViewAdapter getArchiveRecyclerViewAdapter() {
        return archiveRecyclerViewAdapter;
    }

    public ArchiveCategoryHolderContent(java.lang.String categoryName, ArchiveRecyclerViewAdapter archiveRecyclerViewAdapter) {
        this.categoryName = categoryName;
        this.archiveRecyclerViewAdapter = archiveRecyclerViewAdapter;
    }
}
