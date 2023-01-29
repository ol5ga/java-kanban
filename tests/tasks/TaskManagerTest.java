package tasks;

import manager.InMemoryTaskManager;
import manager.ManagerSaveException;
import manager.TaskManager;

import org.junit.jupiter.api.Test;
import tasks.*;

import java.time.LocalDateTime;
import java.util.List;

public abstract class TaskManagerTest <T extends TaskManager> {
    protected T taskManager;
    protected Task task;
    protected Epic epic;
    protected Subtask subtask;



    protected void initTasks() throws ManagerSaveException {
        Task task1 = new Task("Task 1", "description 1", LocalDateTime.of(2023,01,28,22,30),15);
        int task1Id = taskManager.addNewTask(task1);
    }

    @Test
    void getTasks() throws ManagerSaveException {
        final List<Task> tasks = taskManager.getAllTasks();
    }
}
