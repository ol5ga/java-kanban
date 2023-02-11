import manager.*;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class Main {

    public static void main(String[] args) throws ManagerSaveException, IOException {
        TaskManager manager = Managers.getDefault();
       File file = new File("resources/emptyTest.csv");

    }

    public static void testSaving () throws ManagerSaveException {
        TaskManager manager = Managers.getDefaultFile();
        Task task1 = new Task("Task 1", "description 1",LocalDateTime.now(),15);
        int task1Id = manager.addNewTask(task1);
        Epic epic1 = new Epic("Epic 1", "epic-description 1");
        int epic1Id = manager.addNewEpic(epic1);

        Task task2 = new Task("Task 2", "description 2", LocalDateTime.of(2023,2,8,20,00),30);
        int task2Id = manager.addNewTask(task2);

        Task task1N = new Task(1,"Task 1", "description 1",LocalDateTime.of(2023,2,8,19,45),30);

        System.out.println(manager.getPrioritizedTasks());

        task1.setStatus(TaskStatus.NEW);
        task2.setStatus(TaskStatus.IN_PROGRESS);

        manager.getTask(1);
        manager.getTask(3);

        Subtask subtask1 = new Subtask("Subtask 1", "sub-description 1", 2);
        int subtask1Id = manager.addNewSubtask(subtask1);

        Epic epic2 = new Epic("Epic 2", "epic-description 2");
        int epic2Id = manager.addNewEpic(epic2);


        Subtask subtask2 = new Subtask("Subtask 2", "sub-description 2", 5);
        Subtask subtask3 = new Subtask("Subtask 3","sub-description 3", 2);

        int subtask2Id = manager.addNewSubtask(subtask2);
        int subtask3Id = manager.addNewSubtask(subtask3);
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




