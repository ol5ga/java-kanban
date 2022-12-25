package manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import tasks.Epic;
import tasks.Task;
import tasks.Subtask;
import tasks.TaskStatus;

public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    public int getId = 0;
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> allTasks = new ArrayList<>();
        for (Integer id : tasks.keySet()) {
            allTasks.add(tasks.get(id));
        }
        return allTasks;
    }

    @Override
    public void deleteAllTasks(){
        tasks.clear();
    }

    @Override
    public Task getTask(int id) {
        Task idTask = tasks.get(id);
        historyManager.addTask(idTask);
        return idTask;
    }
    @Override
    public int addNewTask(Task task) {
        final int id = ++getId;
        task.setId(id);
        tasks.put(id,task);
        return id;
    }

    @Override
    public void updateTask(Task updateTask) {
        Task task = tasks.get(updateTask.getId());
            if (!updateTask.equals(task)) {
                tasks.put(updateTask.getId(), updateTask);
            }
    }

    @Override
    public void deleteTask(int id){

        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
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

    @Override
    public void deleteAllEpics(){

        epics.clear();
        deleteAllSubtasks();
    }

    @Override
    public Epic getEpic(int id) {
        Epic idEpic = epics.get(id);
        historyManager.addTask(idEpic);
        return idEpic;
    }

    @Override
    public int addNewEpic(Epic epic) {
        final int id = ++getId;
        epic.setId(id);
        epics.put(id,epic);
        return id;
    }
    @Override
    public void updateEpic(Epic updateEpic) {
        Epic epic = epics.get(updateEpic.getId());
            if(!updateEpic.equals(epic)){
                epics.put(updateEpic.getId(), updateEpic);
                }
    }

    @Override
    public void deleteEpic(int id){
        Epic epic = epics.get(id);
        List<Integer> subs = epic.getSubtaskId();
        List<Integer> epicsSubs = new ArrayList<>();
        for (Integer subId : subs) {
            epicsSubs.add(subId);
        }
        for (Integer subId : epicsSubs) {
            deleteSubtask(subId);
        }
        historyManager.remove(id);
        epics.remove(id);

    }

    @Override
    public TaskStatus updateEpicStatus(Epic epic){
        ArrayList <Integer> subs = epic.getSubtaskId();
        TaskStatus stat = TaskStatus.NEW;
        if (subs.isEmpty()){
            return epic.getStatus();
        } else {
            Subtask first = subtasks.get(subs.get(0));
            for (int i = 1; i < subs.size(); i++) {
                Subtask n = subtasks.get(subs.get(i));
                if (!n.getStatus().equals(first.getStatus())){
                    stat = TaskStatus.IN_PROGRESS;
                    break;
                } else stat = first.getStatus();
            }
        }
        epic.setStatus(stat);
        return stat;

    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        ArrayList<Subtask> allSubtasks = new ArrayList<>();
        for (Integer id : subtasks.keySet()) {
            allSubtasks.add(subtasks.get(id));
        }
        return allSubtasks;

    }

    @Override
    public void deleteAllSubtasks(){
        for (Epic value : epics.values()) {
            value.cleanSubtaskIds();
            updateEpicStatus(value);

        }
        subtasks.clear();

    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask idSubtask = subtasks.get(id);
        historyManager.addTask(idSubtask);
        return idSubtask;
    }


    @Override
    public int addNewSubtask(Subtask subtask) {
        final int id = ++getId;
        Epic subEpic = epics.get(subtask.getEpicId());
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



    @Override
    public void updateSubtask(Subtask updateSubtask) {
        Subtask subtask = subtasks.get(updateSubtask.getId());
            if (!updateSubtask.equals(subtask)) {
                subtasks.put(updateSubtask.getId(), updateSubtask);
                }
        Epic subEpic = epics.get(updateSubtask.getEpicId());
        updateEpicStatus(subEpic);
    }

    @Override
    public void deleteSubtask(int id){
        if (getSubtask(id) == null){
            System.out.println("Такой подзадачи не существует");
        } else {
            Subtask deletedSubtask = getSubtask(id);
            Epic subEpic = epics.get(deletedSubtask.getEpicId());

            historyManager.remove(id);
            subEpic.removeSubtask(id);


            subtasks.remove(id);
            updateEpicStatus(subEpic);

        }
    }

    @Override
    public List<Subtask> getEpicSubtasks(int epicId) {
        List<Subtask> epicSubtasks = new ArrayList<>();
        for (Integer id : subtasks.keySet()) {
            if (subtasks.get(id).getEpicId() == epicId) {
                epicSubtasks.add(subtasks.get(id));
            }
        }
        return epicSubtasks;
    }

    @Override
    public List<Task> getHistory(){
        return historyManager.getHistory();
    }


}
