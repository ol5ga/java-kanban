import manager.*;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws ManagerSaveException, IOException {
        File file = new File("resources/task.csv");

  TaskManager manager = Managers.getDefault();


/*
  FileBackedTasksManager manager1 = FileBackedTasksManager.loadFromFile(file);
 Task task1 = new Task("Task 1", "description 1");
        int task1Id = manager.addNewTask(task1);
        Epic epic1 = new Epic("Epic 1", "epic-description 1");
        int epic1Id = manager.addNewEpic(epic1);

    Task task2 = new Task("Task 2", "description 2");

        int task2Id = manager.addNewTask(task2);
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


        manager.getTask(3);
         manager.getTask(1);
        manager.getSubtask(6);
        manager.getEpic(2);
        manager.getSubtask(7);
        manager.getEpic(5);
        manager.getSubtask(4);



        System.out.println(manager1.getHistory());
        System.out.println(manager1.getAllEpics());
        System.out.println(manager.getHistory());
     manager.deleteEpic(4);
       System.out.println(manager.getHistory());


         Subtask subtask2N = new Subtask("Subtask 2n", "sub-description 2n", 1);
        int subtask1Id = manager.addNewSubtask(subtask1);
        int subtask2Id = manager.addNewSubtask(subtask2);
        int subtask3Id = manager.addNewSubtask(subtask3);
        subtask1.setStatus(TaskStatus.DONE);
        subtask2.setStatus(TaskStatus.DONE);
        subtask3.setStatus(TaskStatus.DONE);

        //System.out.println(manager.getEpicSubtasks(1));
        System.out.println(manager.getAllEpics());


    System.out.println(manager.getHistory());
    System.out.println(manager.getAllSubtasks());
    System.out.println(manager.getAllTasks());
    manager.deleteAllSubtasks();
    Subtask subtask2N = new Subtask("Subtask 2n", "sub-description 2n", 1);
    subtask2.setStatus("IN_PROGRESS");
    manager.deleteSubtask(3);
    System.out.println(manager.getAllSubtasks());
    System.out.println(manager.getAllEpics());

    manager.deleteAllSubtasks();
    manager.updateSubtask(subtask2N, 5);
    System.out.println(manager.getAllSubtasks());
    System.out.println(manager.getAllEpics());
    epic1.setStatus("New");

    manager.updateEpicStatus(epic1);
    System.out.println(manager.getAllEpics());

    System.out.println(task1Id);
    System.out.println(task2Id);
    manager.deleteAllTasks();
    Task task2New = new Task(6,"Task 2N","description 2N");
    manager.updateTask(task2New);
    System.out.println(manager.getTask(2));
    manager.deleteTask(1);
    System.out.println(manager.getAllTasks());
*/
    }


}

