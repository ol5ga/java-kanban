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

    public void updateTask(Task updateTask, int id) {
        for (Integer taskId: tasks.keySet()) {
            if (taskId == id) {
                Task task = tasks.get(taskId);
                if (!updateTask.equals(task)) {
                    tasks.put(id, updateTask);
                }
            }
        }
    }

    public void deleteTask(int id){
        tasks.remove(id);
    }

    public HashMap<Integer, Epic> getEpics() {
        for (Epic epic: epics.values()) {
            getEpicStatus(epic);
        }
        return epics;
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
        for (Epic epic: epics.values()) {
            if (updateEpic.getId() == epic.getId()) {
                if(!updateEpic.equals(epic)){
                    epics.put(epic.getId(), updateEpic);
                }
            }
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
    public String getEpicStatus(Epic epic){
        ArrayList <Integer> subs = epic.getSubtaskId();
        String stat = null;
        if (subs.isEmpty()){
            epic.setStatus("NEW");
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
        updateEpic(epic);
        return stat;

    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public void deleteAllSubtasks(){
        for (Subtask value : subtasks.values()) {
            int delete = value.getEpicId();
            Epic cleanEpic = getEpic(delete);
            cleanEpic.getSubtaskId().clear();
        }
        subtasks.clear();

    }

    public Subtask getSubtask(int id) {
        Subtask idSubtask = subtasks.get(id);
        return idSubtask;
    }

    public int addNewSubtask(Subtask subtask) {
        final int id = ++getId;
        subtask.setId(id);
        subtasks.put(id,subtask);
        Epic subEpic = getEpic(subtask.getEpicId());

        if (subEpic == null){
            System.out.println("Такого эпика не существует");
            subtask.setEpicId(0);
        } else {
            subEpic.getSubtaskId().add(subtask.getId());
        }
        getEpicStatus(subEpic);
        return id;
    }

    public void updateSubtask(Subtask updateSubtask, int id) {
        for (Integer subId: subtasks.keySet()) {
            if (subId == id) {
                updateSubtask.setId(subId);
                Subtask subtask = subtasks.get(subId);
                if (!updateSubtask.equals(subtask)) {
                    subtasks.put(subId, updateSubtask);
                }
            }
        }
        Epic subEpic = getEpic(updateSubtask.getEpicId());
        getEpicStatus(subEpic);
    }

    public void deleteSubtask(int id){

        Subtask deleteSubtask = getSubtask(id);
        Epic subEpic = getEpic(deleteSubtask.getEpicId());
        for (int i = 0; i <subEpic.getSubtaskId().size(); i++) {
            if(deleteSubtask.getId() == subEpic.getSubtaskId().get(i)){
                subEpic.getSubtaskId().remove(i);

            }
        }
        subtasks.remove(id);
        getEpicStatus(subEpic);
    }

}
