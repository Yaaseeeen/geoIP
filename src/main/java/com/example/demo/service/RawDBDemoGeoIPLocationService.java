package com.example.demo.service;

import com.example.demo.entity.GeoIP;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

@Service
public class RawDBDemoGeoIPLocationService {
    private DatabaseReader dbReader;

    public RawDBDemoGeoIPLocationService() throws IOException {
        File database = new File("src/main/resources/GeoLite2-Country.mmdb");
        dbReader = new DatabaseReader.Builder(database).build();
    }

    public GeoIP getLocation(String ip) throws IOException, GeoIp2Exception {
        InetAddress ipAddress = InetAddress.getByName(ip);
        CountryResponse response = dbReader.country(ipAddress);

        String countryName = response.getCountry().getName();
        return new GeoIP(ip, countryName);
    }
}
