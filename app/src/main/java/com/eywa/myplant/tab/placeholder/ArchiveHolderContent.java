package com.eywa.myplant.tab.placeholder;

import android.net.Uri;

public class ArchiveHolderContent {
    public final Uri imageUri;
    public final Uri pageUri;
    public final String plantNameKor;
    public final String species;
    public final String scientificName;

    // 물, 조도, 일조량, 습도
    public final int temperature_min, temperature_max; // 생육 온도 최소 / 최대 (단위: 섭씨)
    public final int humidity_min, humidity_max; // 습도 최소 / 최대 (단위: %)
    public final int light_intensity_min, light_intensity_max; // 일조량 최소 / 최대 (단위: 시간)
    public final int soil_moisture_limit; // 물을 줘야 할 때 (흙이 마른 정도) (단위: cm)

    public ArchiveHolderContent (Uri imageUri, Uri pageUri, String plantNameKor, String species, String scientificName,
                                 int temperature_min, int temperature_max,
                                 int humidity_min, int humidity_max,
                                 int light_intensity_min, int light_intensity_max, int soil_moisture_limit) {
        this.imageUri = imageUri;
        this.pageUri = pageUri;
        this.plantNameKor = plantNameKor;
        this.species = species;
        this.scientificName = scientificName;

        this.temperature_min = temperature_min;
        this.temperature_max = temperature_max;
        this.humidity_min = humidity_min;
        this.humidity_max = humidity_max;
        this.light_intensity_min = light_intensity_min;
        this.light_intensity_max = light_intensity_max;
        this.soil_moisture_limit = soil_moisture_limit;
    }
}
