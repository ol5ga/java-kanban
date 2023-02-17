package Http;

import manager.ManagerSaveException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private final String url;
    private final String apiToken;

    public KVTaskClient(int port) throws IOException, InterruptedException {
        url = "http://localhost:" + port + "/";
        apiToken = register(url);
    }

    private String register(String url) throws IOException, InterruptedException {
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + "register"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() !=200){
                throw new ManagerSaveException("Произошла ошибка " + response.statusCode());
            }
            System.out.println(response.body());
            return response.body();
        } catch (IOException  |InterruptedException e) {
            throw new RuntimeException(e);

        }
    }
    public void put(String key, String json){
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + "save/" + key + "?API_TOKEN=" + apiToken))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            System.out.println(response.body());
            if (response.statusCode() !=200){
                throw new ManagerSaveException("Произошла ошибка " + response.statusCode());
            }
        } catch (IOException  |InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String load(String key){
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + "load/" + key + "?API_TOKEN=" + apiToken))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() !=200){
                throw new ManagerSaveException("Произошла ошибка " + response.statusCode());
            }
            System.out.println(response.body());
            return response.body();
        } catch (IOException  |InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
