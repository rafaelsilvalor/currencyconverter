package com.rafellor.currencyconverter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ExchangeRateClient {

    private static final String API_KEY = "50a68485c0cfeec20c557e17";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    public double getExchangeRate(String from, String to) throws IOException, InterruptedException {
        String url = BASE_URL + API_KEY + "/pair/" + from + "/" + to;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
        return json.get("conversion_rate").getAsDouble();
    }
}
