package com.example.google_geo_test;

public class LocationResponseModel {
    private Location location;
    private double accuracy;

    // Getters and setters
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    @Override
    public String toString() {
        return "LocationResponseModel{" +
                "location=" + location +
                ", accuracy=" + accuracy +
                '}';
    }

    // Nested Location class
    public static class Location {
        private double lat;
        private double lng;

        // Getters and setters
        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        @Override
        public String toString() {
            return "Location{" +
                    "lat=" + lat +
                    ", lng=" + lng +
                    '}';
        }
    }
}
