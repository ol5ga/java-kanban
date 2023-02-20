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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {
    private KVServer kvServer;

    @BeforeEach
    public void setUp() throws IOException, InterruptedException {
        kvServer = Managers.getDefaultKVServer();
        taskManager = new HttpTaskManager(KVServer.PORT);
        emptyManager = new HttpTaskManager(KVServer.PORT);
        initTasks();
    }

    @AfterEach
    protected void setDown(){
        kvServer.stop();
    }

    @Test
    public void load() throws IOException, InterruptedException {
        taskManager.getTask(task.getId());
        taskManager.getSubtask(subtask.getId());
        taskManager.getEpic(epic.getId());
        HttpTaskManager taskManager2 = new HttpTaskManager(KVServer.PORT, true);
        final List<Task> tasks = taskManager2.getAllTasks();
        assertNotNull(tasks, "Возвращает пустой список задач");
        assertEquals(1, tasks.size(),"Возврарает неверный список задач");
        final List<Epic> epics = taskManager.getAllEpics();
        assertNotNull(epics, "Возвращает пустой список эпиков");
        assertEquals(1, epics.size(), "Возвращает неверный список эпиков");
        final List<Subtask> subtasks = taskManager.getAllSubtasks();
        assertNotNull(subtasks, "Возвращает пустой список подчадач");
        assertEquals(1,subtasks.size(), "Возвращает неверный список подзадач");
        final List<Task> history = taskManager.getHistory();
        assertNotNull(history, "Возвращает пустой список истории");
        assertEquals(3,history.size(),"Возвращает неверный список истории");
    }
}
