package manager;

import java.util.ArrayList;
import java.util.HashMap;
import tasks.Epic;
import tasks.Task;
import tasks.Subtask;
import java.util.Objects;

public class TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    public int getId = 0;

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> allTasks = new ArrayList<>();
        for (Integer id : tasks.keySet()) {
            allTasks.add(tasks.get(id));
        }
        return allTasks;
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
        Task task = tasks.get(updateTask.getId());
            if (!updateTask.equals(task)) {
                tasks.put(updateTask.getId(), updateTask);
            }
    }

    public void deleteTask(int id){
        tasks.remove(id);
    }

    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> allEpics = new ArrayList<>();
        for (Epic epic: epics.values()) {
            updateEpicStatus(epic);
        }
        for (Integer id : epics.keySet()) {
            allEpics.add(epics.get(id));
        }
        return allEpics;


    }

    public void deleteAllEpics(){

        epics.clear();
        deleteAllSubtasks();
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
        Epic epic = epics.get(updateEpic.getId());
            if(!updateEpic.equals(epic)){
                epics.put(updateEpic.getId(), updateEpic);
                }
    }

    public void deleteEpic(int id){
        Epic epic = getEpic(id);
        ArrayList <Integer> subs = epic.getSubtaskId();
        for (Integer subId : subs) {
            deleteSubtask(subId);
        }
        epics.remove(id);
    }
    public String updateEpicStatus(Epic epic){
        ArrayList <Integer> subs = epic.getSubtaskId();
        String stat = "NEW";
        if (subs.isEmpty()){
            return epic.getStatus();
        } else {
            Subtask first = getSubtask(subs.get(0));
            for (int i = 1; i < subs.size(); i++) {
                Subtask n = getSubtask(subs.get(i));
                if (!n.getStatus().equals(first.getStatus())){
                    stat = "IN_PROGRESS";
                    break;
                } else stat = first.getStatus();
            }
        }
        epic.setStatus(stat);
        return stat;

    }

    public ArrayList<Subtask> getAllSubtasks() {
        ArrayList<Subtask> allSubtasks = new ArrayList<>();
        for (Integer id : subtasks.keySet()) {
            allSubtasks.add(subtasks.get(id));
        }
        return allSubtasks;

    }

    public void deleteAllSubtasks(){
        for (Epic value : epics.values()) {
            value.cleanSubtaskIds();
            updateEpicStatus(value);

        }
        subtasks.clear();

    }

    public Subtask getSubtask(int id) {
        Subtask idSubtask = subtasks.get(id);
        return idSubtask;
    }

    public int addNewSubtask(Subtask subtask) {
        final int id = ++getId;
        Epic subEpic = getEpic(subtask.getEpicId());
        if (subEpic == null){
            System.out.println("Такого эпика не существует");
            subtask.setEpicId(0);
        } else {
        subtask.setId(id);
        subtasks.put(id,subtask);
        subEpic.getSubtaskId().add(subtask.getId());
        }
        updateEpicStatus(subEpic);
        return id;
    }

    public void updateSubtask(Subtask updateSubtask) {
        Subtask subtask = subtasks.get(updateSubtask.getId());
            if (!updateSubtask.equals(subtask)) {
                subtasks.put(updateSubtask.getId(), updateSubtask);
                }
        Epic subEpic = getEpic(updateSubtask.getEpicId());
        updateEpicStatus(subEpic);
    }

    public void deleteSubtask(int id){
        if (getSubtask(id) == null){
            System.out.println("Такой подзадачи не существует");
        } else {
            Subtask deletedSubtask = getSubtask(id);
            Epic subEpic = getEpic(deletedSubtask.getEpicId());
            subEpic.removeSubtask(id);

            subtasks.remove(id);
            updateEpicStatus(subEpic);
        }
    }


}
