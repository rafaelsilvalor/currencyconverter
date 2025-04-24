package com.rafellor.currencyconverter.infrastructure.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rafellor.currencyconverter.domain.ExchangeRateService;
import com.rafellor.currencyconverter.infrastructure.config.ConfigLoader;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateClient implements ExchangeRateService {
    private final String apikey;

    public ExchangeRateClient(ConfigLoader configLoader) {
        this.apikey = configLoader.get("API_KEY");
    }

    @Override
    public double getExchangeRate(String from, String to) {
        String url = "https://v6.exchangerate-api.com/v6/" + apikey + "/pair/" + from + "/" + to;
        System.out.println("-- Fetching exchange rate from API");
        System.out.println("-- URL: " + url);

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("-- API Response: " + response.body());

            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
            return json.get("conversion_rate").getAsDouble();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("!! Failed to fetch exchange rate", e);
        }
    }

    public List<String> getSupportedCodes() {
        String url = "https://v6.exchangerate-api.com/v6/" + apikey + "/codes";
        System.out.println("-- Fetching supported currency codes...");
        System.out.println("-- URL: " + url);

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

            List<String> codes = new ArrayList<>();
            for (var codePair : json.getAsJsonArray("supported_codes")) {
                var arr = codePair.getAsJsonArray();
                String code = arr.get(0).getAsString();
                String name = arr.get(1).getAsString();
                codes.add(String.format("%-5s : %s", code, name));
            }

            return codes;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("!! Failed to fetch supported currency codes", e);
        }
    }
}
