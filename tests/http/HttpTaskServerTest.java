package http;

import Http.HttpTaskManager;
import Http.KVServer;
import com.google.gson.Gson;
import manager.HttpTaskServer;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.*;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HttpTaskServerTest {
    KVServer kvServer;
    HttpTaskServer server;

    TaskManager manager;
    Gson gson;

    public HttpTaskServerTest() throws IOException, InterruptedException {
        kvServer = new KVServer();
        server = new HttpTaskServer();
        manager = Managers.getDefaultHttp();
        gson = Managers.getGson();
    }

    @BeforeAll
    public void setUp() throws IOException {
        kvServer.start();
    }

    @AfterEach
    public void setDown() throws IOException {
        kvServer.stop();
    }

    @BeforeEach
    public void start() throws IOException, InterruptedException {
        server.start();
    }


    @AfterEach
    public void stop() {
        server.stop();
    }

    @Test
    public void handler() throws IOException, InterruptedException {
        Task task1 = new Task("Task 1", "description 1", LocalDateTime.now(),15);
       int task1Id = manager.addNewTask(task1);
       String json = gson.toJson(manager.getAllTasks());
        HttpClient httpClient = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "tasks"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
