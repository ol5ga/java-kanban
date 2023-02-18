import Http.KVServer;
import Http.KVTaskClient;
import com.google.gson.Gson;
import manager.*;
import tasks.Epic;
import tasks.Task;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class Main {

    public static void main(String[] args) throws ManagerSaveException, IOException, InterruptedException {

        KVServer kvServer = new KVServer();
        kvServer.start();
        HttpTaskServer server = new HttpTaskServer();
        server.start();
    }
        public static void testHttp() throws IOException, InterruptedException {

        KVTaskClient client = new KVTaskClient(8078);
        Gson gson = Managers.getGson();
        HttpClient httpClient = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8078/tasks?API_TOKEN=DEBUG");

        TaskManager manager = Managers.getDefaultHttp();
        Task task1 = new Task("Task 1", "description 1",LocalDateTime.now(),15);
        int task1Id = manager.addNewTask(task1);
        Task task2 = new Task("Task 2", "description 2", LocalDateTime.of(2023,2,8,20,00),30);
        int task2Id = manager.addNewTask(task2);
        String json = gson.toJson(manager.getAllTasks());
        client.put("tasks",json);
        client.load("tasks");

        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        }


    public static void testSaving()  {
        TaskManager manager = Managers.getDefaultFile();
        Task task1 = new Task("Task 1", "description 1",LocalDateTime.now(),15);
        int task1Id = manager.addNewTask(task1);
        Epic epic1 = new Epic("Epic 1", "epic-description 1");
        int epic1Id = manager.addNewEpic(epic1);

        Task task2 = new Task("Task 2", "description 2", LocalDateTime.of(2023,2,8,20,00),30);
        int task2Id = manager.addNewTask(task2);

        Epic epic2 = new Epic("Epic 2", "epic-description 2");
        int epic2Id = manager.addNewEpic(epic2);

        }

    public static void testLoading(File file) throws IOException, ManagerSaveException {

        FileBackedTasksManager manager = FileBackedTasksManager.loadFromFile(file);

        manager.getSubtask(3);
        manager.getTask(1);

        manager.getEpic(2);


        System.out.println(manager.getHistory());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getHistory());
        manager.deleteEpic(2);
        System.out.println(manager.getHistory());

    }

    }




