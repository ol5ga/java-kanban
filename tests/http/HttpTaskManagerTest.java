package http;

import com.google.gson.Gson;
import manager.http.HttpTaskManager;
import manager.Managers;
import manager.TaskManager;
import manager.http.KVServer;
import manager.http.KVTaskClient;
import org.junit.jupiter.api.*;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskManagerTest;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {
    KVServer kvServer;
    Gson gson;
    KVTaskClient client;
    protected TaskManager taskManager;
    protected Task task;
    protected Subtask subtask;
    protected Epic epic;



    @BeforeEach
    public void start() throws IOException, InterruptedException {
        kvServer = Managers.getDefaultKVServer();
        client = new KVTaskClient(8078);
        gson = Managers.getGson();
        taskManager = new HttpTaskManager(KVServer.PORT);
//        Task task1 = new Task("Task 1", "description 1", LocalDateTime.now(),15);
//        int task1Id = taskManager.addNewTask(task1);
        initTasks();

    }

    @AfterEach
    public void setDown() {
        kvServer.stop();

    }

    @Test
    public void load() throws IOException, InterruptedException {
        Task task1 = new Task("Task 1", "description 1", LocalDateTime.now(),15);
        int task1Id = taskManager.addNewTask(task1);
        //taskManager.getTask(task.getId());
//  taskManager.getSubtask(subtask.getId());
//  taskManager.getEpic(epic.getId());
//        HttpTaskManager manager = new HttpTaskManager(KVServer.PORT);
//        String key = "tasks";
//        HttpClient httpClient = HttpClient.newHttpClient();
//        URI url = URI.create("http://localhost:8078/");
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(url + "load/" + key + "?API_TOKEN=DEBUG"))
//                        .GET()
//                        .build();
//        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//        assertTrue(taskManager.getAllTasks().isEmpty(), "Вовращает не пустой список");
//        assertTrue(taskManager.getPrioritizedTasks().isEmpty(),"Возвращает не пустой список");
//        assertTrue(taskManager.getHistory().isEmpty(),"Возвращает не пустой список истории");

//        Task task1 = new Task("Task 1", "description 1", LocalDateTime.now(),15);
//        int task1Id = taskManager.addNewTask(task1);
//        String json = gson.toJson(taskManager.getAllTasks());
//        client.put("tasks",json);
//        client.load("tasks");

//        assertFalse(taskManager.getAllTasks().isEmpty(), "Вовращает пустой список");
//        assertFalse(taskManager.getPrioritizedTasks().isEmpty(),"Возвращает пустой список");
//        assertFalse(taskManager.getHistory().isEmpty(),"Возвращает пустой список истории");


        HttpTaskManager manager2 = new HttpTaskManager(KVServer.PORT,true);

     //   assertEquals(1,manager2.getAllTasks().size(), "Возвращает неверный список");
        assertNotNull(manager2.getPrioritizedTasks(),"Возвращает пустой список");
        assertEquals(taskManager.getHistory().size(),manager2.getHistory().size(), "Возвращает неверную историю");
    }
}
