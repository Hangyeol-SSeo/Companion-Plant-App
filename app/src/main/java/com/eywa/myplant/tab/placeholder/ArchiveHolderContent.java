package com.eywa.myplant.tab.placeholder;

import android.net.Uri;

public class ArchiveHolderContent {
    public final Uri imageUri;
    public final Uri pageUri;
    public final String plantNameKor;
    public final String species;
    public final String scientificName;
    // 습도, 온도 등 조건 추가

    public ArchiveHolderContent (Uri imageUri, Uri pageUri, String plantNameKor, String species, String scientificName) {
        this.imageUri = imageUri;
        this.pageUri = pageUri;
        this.plantNameKor = plantNameKor;
        this.species = species;
        this.scientificName = scientificName;
    }
}
