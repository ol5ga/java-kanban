import java.util.HashMap;

public class Main {

    public static void main(String[] args) {


    TaskManager manager = new TaskManager();

    Task task1 = new Task("Task 1", "description 1", "New");
    Task task2 = new Task("Task 2", "description 2", "IN_PROGRESS");
    int task1Id = manager.addNewTask(task1);
    int task2Id = manager.addNewTask(task2);
    Epic epic1 = new Epic("Epic 1", "epicdescription 1","NEW");
    int epic1Id = manager.addNewEpic(epic1);
    System.out.println(epic1Id);
    Subtask subtask1 = new Subtask("Subtask 1", "descriptionsub 1", "NEW", 3);
    int subtask1Id = manager.addNewSubtasks(subtask1);
    System.out.println(subtask1Id);
    //System.out.println(task1Id);
    //System.out.println(task2Id);
   System.out.println(manager.getAllTasks());
    //manager.deleteAllTasks();
        Task task2New = new Task(2,"Task 2","description 2", "DONE");
        manager.updateTask(task2New);
    //System.out.println(manager.getTask(2));
    manager.deleteTask(1);
    System.out.println(manager.getAllTasks());
    }
}

