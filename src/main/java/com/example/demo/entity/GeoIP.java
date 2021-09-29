package com.example.demo.entity;

public class GeoIP {
    private String ipAddress;
    private String country;
    private String city;
    private String latitude;
    private String longitude;

    public GeoIP() {

    }

    public GeoIP(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public GeoIP(String ipAddress, String country) {
        this.ipAddress = ipAddress;
        this.country = country;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
