import java.util.HashMap;
import java.util.Objects;
import java.lang.Override;


public class TaskManager {

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    public int getId = 0;

    public HashMap<Integer, Task> getAllTasks() {
        return tasks;
    }

    public void deleteAllTasks(){
        tasks.clear();
    }

    public Task getTask(int id) {
        Task idTask = tasks.get(id);
        return idTask;
    }
    public int addNewTask(Task task) {
       final int id = ++getId;
        task.setId(id);
        tasks.put(id,task);
        return id;
    }

    public void updateTask(Task updateTask) {
        for (Task task: tasks.values()) {
            if (updateTask.id == task.id) {
                task.name = updateTask.name;
                task.description = updateTask.description;
                task.status = updateTask.status;
            }
        }
    }

    public void deleteTask(int id){
       tasks.remove(id);
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public void deleteAllEpics(){
        epics.clear();
    }

    public Epic getEpic(int id) {
        Epic idEpic = epics.get(id);
        return idEpic;
    }

    public int addNewEpic(Epic epic) {
        final int id = ++getId;
        epic.setId(id);
        epics.put(id,epic);
        return id;
    }

    public void updateEpic(Epic updateEpic) {
        for (Task epic: epics.values()) {
            if (updateEpic.id == epic.id) {
                epic.name = updateEpic.name;
                epic.description = updateEpic.description;
                epic.status = updateEpic.status;
            }
        }
    }

    public void deleteEpic(int id){
        epics.remove(id);
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public void deleteAllSubtasks(){
        subtasks.clear();
    }

    public Subtask getSubtask(int id) {
        Subtask idSubtask = subtasks.get(id);
        return idSubtask;
    }

    public int addNewSubtasks(Subtask subtask) {
        final int id = ++getId;
        subtask.setId(id);
        subtasks.put(id,subtask);
        Epic subEpic = getEpic(subtask.epicId);
        subEpic.subtaskId.add(subtask.id);
        return id;
    }

    public void updateSubtask(Subtask updateSubtasks) {
        for (Subtask subtask: subtasks.values()) {
            if (updateSubtasks.id == subtask.id) {
                subtask.name = updateSubtasks.name;
                subtask.description = updateSubtasks.description;
                subtask.status = updateSubtasks.status;
            }
        }
    }

    public void deleteSubtask(int id){
        subtasks.remove(id);
        Subtask deleteSubtask = getSubtask(id);
        Epic subEpic = getEpic(deleteSubtask.epicId);
        subEpic.subtaskId.remove(deleteSubtask.id);
    }


}
