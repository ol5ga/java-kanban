package http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import manager.Managers;
import manager.TaskManager;
import manager.http.HttpTaskManager;
import manager.http.HttpTaskServer;
import manager.http.KVServer;
import org.junit.jupiter.api.*;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class HttpTaskServerTest {
    KVServer kvServer;
    HttpTaskServer server;

    TaskManager taskManager;
    Gson gson;
    protected Task task;
    protected Subtask subtask;
    protected Epic epic;

//    public HttpTaskServerTest() throws IOException, InterruptedException {
//        kvServer = new KVServer();
//
//    }



    @AfterEach
    public void setDown() throws IOException {
        kvServer.stop();
    }

    @BeforeEach
    public void start() throws IOException, InterruptedException {
//        taskManager = new HttpTaskManager();
        server = new HttpTaskServer(taskManager);
        server.start();
        gson = Managers.getGson();
        task = new Task("Task 1", "description 1", LocalDateTime.of(2023, 01, 28, 22, 30), 15);
        int taskId = taskManager.addNewTask(task);
        epic = new Epic("Epic 1", "epic-description 1");
        int epicId = taskManager.addNewEpic(epic);
        subtask = new Subtask("Subtask 1", "sub-description 1", 2, LocalDateTime.of(2023, 01, 31, 13, 25), 25);
        int subtask1Id = taskManager.addNewSubtask(subtask);
    }

    @AfterEach
    public void stop() {
        server.stop();
    }


    @Test
    void getTasks() throws IOException, InterruptedException {
            HttpClient client = HttpClient.newHttpClient();
            URI url = URI.create("http://localhost:8080/tasks/task");
            HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            final List<Task> tasks = gson.fromJson(response.body(), new TypeToken<ArrayList<Task>>() {
            }.getType());
            assertNotNull(tasks, "Задачи на возвращаются");
            assertEquals(1, tasks.size(), "Не верное количество задач");
            assertEquals(task, tasks.get(0), "Задачи не совпадают");
        }
//    @Test
//    public void handler() throws IOException, InterruptedException {
//        Task task1 = new Task("Task 1", "description 1", LocalDateTime.now(),15);
//       int task1Id = taskManager.addNewTask(task1);
//       String json = gson.toJson(taskManager.getAllTasks());
//        HttpClient httpClient = HttpClient.newHttpClient();
//        URI url = URI.create("http://localhost:8080/");
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(url + "tasks"))
//                .POST(HttpRequest.BodyPublishers.ofString(json))
//                .build();
//        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//    }
}
