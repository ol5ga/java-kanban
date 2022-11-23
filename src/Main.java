import manager.TaskManager;
import tasks.Epic;
import tasks.Task;
import tasks.Subtask;

public class Main {

    public static void main(String[] args) {


    TaskManager manager = new TaskManager();



   /* Epic epic1 = new Epic("Epic 1", "epic-description 1");
    int epic1Id = manager.addNewEpic(epic1);


    Subtask subtask1 = new Subtask("Subtask 1", "sub-description 1", 1);
    Subtask subtask2 = new Subtask("Subtask 2", "sub-description 2", 1);
    Subtask subtask3 = new Subtask("Subtask 3","sub-description 2", 1);
    //Subtask subtask2N = new Subtask("Subtask 2n", "sub-description 2n", 1);
        int subtask1Id = manager.addNewSubtask(subtask1);
        int subtask2Id = manager.addNewSubtask(subtask2);
        int subtask3Id = manager.addNewSubtask(subtask3);
        subtask1.setStatus("NEW");
        subtask2.setStatus("NEW");
        subtask3.setStatus("DONE");

    Task task1 = new Task("Task 1", "description 1");
    Task task2 = new Task("Task 2", "description 2");
    int task1Id = manager.addNewTask(task1);
    int task2Id = manager.addNewTask(task2);
    task1.setStatus("NEW");
    task2.setStatus("IN_PROGRESS");

    System.out.println(manager.getEpics());
    System.out.println(manager.getSubtasks());
    System.out.println(manager.getAllTasks());

    Subtask subtask2N = new Subtask("Subtask 2n", "sub-description 2n", 1);
    subtask2.setStatus("IN_PROGRESS");
    manager.deleteSubtask(3);
    System.out.println(manager.getSubtasks());
    System.out.println(manager.getEpics());

    manager.deleteAllSubtasks();
    manager.updateSubtask(subtask2N, 5);
    System.out.println(manager.getSubtasks());
    System.out.println(manager.getEpics());
    epic1.setStatus("New");

    manager.getEpicStatus(epic1);


    System.out.println(task1Id);
    System.out.println(task2Id);
    manager.deleteAllTasks();
    //Task task2New = new Task(2,"Task 2","description 2", "DONE");
    //manager.updateTask(task2New);
    System.out.println(manager.getTask(2));
    manager.deleteTask(1);
*/

    }


}

