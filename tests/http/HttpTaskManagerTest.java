package http;

import Http.HttpTaskManager;
import Http.KVServer;
import Http.KVTaskClient;
import com.google.gson.Gson;
import manager.HttpTaskServer;
import manager.Managers;
import org.junit.jupiter.api.*;
import tasks.Task;
import tasks.TaskManagerTest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {
    KVServer kvServer;
    Gson gson;
    KVTaskClient client;



    @BeforeAll
    public void setUp() throws IOException, InterruptedException {
        kvServer = new KVServer();
        client = new KVTaskClient(8078);
        gson = Managers.getGson();
    }

    @AfterEach
    public void setDown() {
        kvServer.stop();

    }

    @BeforeEach
    public void start() throws IOException, InterruptedException {
        kvServer.start();
        taskManager = new HttpTaskManager(KVServer.PORT);

    }

    @Test
    public void load() throws IOException, InterruptedException {

        HttpTaskManager manager = new HttpTaskManager(KVServer.PORT);
        String key = "tasks";
        HttpClient httpClient = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8078/");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "load/" + key + "?API_TOKEN=DEBUG"))
                        .GET()
                        .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertTrue(manager.getAllTasks().isEmpty(), "Вовращает не пустой список");
        assertTrue(manager.getPrioritizedTasks().isEmpty(),"Возвращает не пустой список");
        assertTrue(manager.getHistory().isEmpty(),"Возвращает не пустой список истории");

        Task task1 = new Task("Task 1", "description 1", LocalDateTime.now(),15);
        int task1Id = manager.addNewTask(task1);
        String json = gson.toJson(manager.getAllTasks());
        client.put("tasks",json);
        client.load("tasks");

        assertFalse(manager.getAllTasks().isEmpty(), "Вовращает пустой список");
        assertFalse(manager.getPrioritizedTasks().isEmpty(),"Возвращает пустой список");
        assertFalse(manager.getHistory().isEmpty(),"Возвращает пустой список истории");


        HttpTaskManager manager2 = new HttpTaskManager(KVServer.PORT,true);
        assertEquals(1,manager2.getAllTasks().size(), "Возвращает неверный список");
        assertNotNull(manager2.getPrioritizedTasks(),"Возвращает пустой список");
        assertEquals(taskManager.getHistory().size(),manager2.getHistory().size(), "Возвращает неверную историю");
    }
}
