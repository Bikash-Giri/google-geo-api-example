package com.example.google_geo_test;

import java.util.List;
import java.util.Map;

public class PostRequestBody {
    private int homeMobileCountryCode;
    private int homeMobileNetworkCode;
    private String radioType;
    private String carrier;
    private boolean considerIp;
    private List<Map<String, Object>> cellTowers;
    private List<Map<String, Object>> wifiAccessPoints;

    public PostRequestBody(int homeMobileCountryCode, int homeMobileNetworkCode, String radioType, String carrier, boolean considerIp, List<Map<String, Object>> cellTowers, List<Map<String, Object>> wifiAccessPoints) {
        this.homeMobileCountryCode = homeMobileCountryCode;
        this.homeMobileNetworkCode = homeMobileNetworkCode;
        this.radioType = radioType;
        this.carrier = carrier;
        this.considerIp = considerIp;
        this.cellTowers = cellTowers;
        this.wifiAccessPoints = wifiAccessPoints;
    }

    // Getters and setters (or use Lombok annotations if preferred)
}