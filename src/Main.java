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
        Task task1 = new Task("Task 1", "description 1", LocalDateTime.of(2023,01,28,22,30),15);
        int task1Id = manager.addNewTask(task1);
        Epic epic1 = new Epic("Epic 1", "epic-description 1");
        int epic1Id = manager.addNewEpic(epic1);

//        Task task2 = new Task("Task 2", "description 2",LocalDateTime.of(2023,01,28,23,00),15);
//
//        int task2Id = manager.addNewTask(task2);
//        task1.setStatus(TaskStatus.NEW);
//        task2.setStatus(TaskStatus.IN_PROGRESS);
//
        Subtask subtask1 = new Subtask("Subtask 1", "sub-description 1", 2,LocalDateTime.of(2023,01,29,13,25),25);
        int subtask1Id = manager.addNewSubtask(subtask1);
        subtask1.setStatus(TaskStatus.DONE);
//        Epic epic2 = new Epic("Epic 2", "epic-description 2");
//        int epic2Id = manager.addNewEpic(epic2);



//        Subtask subtask2 = new Subtask("Subtask 2", "sub-description 2", 5, LocalDateTime.now(),10);
        Subtask subtask3 = new Subtask("Subtask 3","sub-description 3", 2,LocalDateTime.of(2023,01,29,00,15),10);

      //  int subtask2Id = manager.addNewSubtask(subtask2);
        int subtask3Id = manager.addNewSubtask(subtask3);

        //System.out.println(manager.getPrioritizedTasks());
       // Subtask subtask2 = new Subtask(4,"Subtask 1", TaskStatus.IN_PROGRESS,"sub-description 1", 2,LocalDateTime.of(2023,02,01,13,00),30);
        // int subtask2Id = manager.addNewSubtask(subtask2);






        TaskStatus status = manager.updateEpicStatus(epic1);
        System.out.println(status);
        System.out.println(manager.getEpic(2));
        System.out.println(manager.getSubtask(3));

    }
//     File file = new File("resources/task.csv");
//    public static void testSaving () throws ManagerSaveException {
//        TaskManager manager = Managers.getDefaultFile();
//        Task task1 = new Task("Task 1", "description 1");
//        int task1Id = manager.addNewTask(task1);
//        Epic epic1 = new Epic("Epic 1", "epic-description 1");
//        int epic1Id = manager.addNewEpic(epic1);
//
//        Task task2 = new Task("Task 2", "description 2");
//        int task2Id = manager.addNewTask(task2);
//        task1.setStatus(TaskStatus.NEW);
//        task2.setStatus(TaskStatus.IN_PROGRESS);
//
//        manager.getTask(1);
//        manager.getTask(3);
//
//        Subtask subtask1 = new Subtask("Subtask 1", "sub-description 1", 2);
//        int subtask1Id = manager.addNewSubtask(subtask1);
//
//        Epic epic2 = new Epic("Epic 2", "epic-description 2");
//        int epic2Id = manager.addNewEpic(epic2);
//
//
//        Subtask subtask2 = new Subtask("Subtask 2", "sub-description 2", 5);
//        Subtask subtask3 = new Subtask("Subtask 3","sub-description 3", 2);
//
//        int subtask2Id = manager.addNewSubtask(subtask2);
//        int subtask3Id = manager.addNewSubtask(subtask3);
//        }
//
//    public static void testLoading(File file) throws IOException, ManagerSaveException {
//
//        FileBackedTasksManager manager = FileBackedTasksManager.loadFromFile(file);
//
//        manager.getTask(3);
//        manager.getTask(1);
//        manager.getSubtask(6);
//        manager.getEpic(2);
//        manager.getSubtask(7);
//        manager.getEpic(5);
//        manager.getSubtask(4);
//
//        System.out.println(manager.getHistory());
//        System.out.println(manager.getAllEpics());
//        System.out.println(manager.getHistory());
//        manager.deleteEpic(4);
//        System.out.println(manager.getHistory());
//
//    }

    }




