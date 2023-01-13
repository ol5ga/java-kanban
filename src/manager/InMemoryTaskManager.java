package manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import tasks.Epic;
import tasks.Task;
import tasks.Subtask;
import tasks.TaskStatus;

public class InMemoryTaskManager implements TaskManager {
    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();
    protected HashMap<Integer, Subtask> subtasks = new HashMap<>();
    public int getId = 0;
    protected final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public ArrayList<Task> getAllTasks() throws ManagerSaveException {
        ArrayList<Task> allTasks = new ArrayList<>();
        for (Integer id : tasks.keySet()) {
            allTasks.add(tasks.get(id));
        }
        return allTasks;
    }

    @Override
    public void deleteAllTasks() throws ManagerSaveException {

        for (Task task : tasks.values()) {

            historyManager.remove(task.getId());
        }
        tasks.clear();
    }

    @Override
    public Task getTask(int id) throws ManagerSaveException {
        Task idTask = tasks.get(id);
        historyManager.addTask(idTask);
        return idTask;
    }
    @Override
    public int addNewTask(Task task) throws ManagerSaveException {
        final int id = ++getId;
        task.setId(id);
        tasks.put(id,task);
        return id;
    }

    @Override
    public void updateTask(Task updateTask) throws ManagerSaveException {
        Task task = tasks.get(updateTask.getId());
            if (!updateTask.equals(task)) {
                tasks.put(updateTask.getId(), updateTask);
            }
    }

    @Override
    public void deleteTask(int id) throws ManagerSaveException {

        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public ArrayList<Epic> getAllEpics() throws ManagerSaveException {
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
    public void deleteAllEpics() throws ManagerSaveException {
        ArrayList<Integer> subs = new ArrayList<>();
        for (Epic epic : epics.values()) {
            historyManager.remove(epic.getId());
        }
        epics.clear();
        deleteAllSubtasks();
    }

    @Override
    public Epic getEpic(int id)  throws ManagerSaveException {
        Epic idEpic = epics.get(id);
        historyManager.addTask(idEpic);
        return idEpic;
    }

    @Override
    public int addNewEpic(Epic epic) throws ManagerSaveException  {
        final int id = ++getId;
        epic.setId(id);
        epics.put(id,epic);
        return id;
    }
    @Override
    public void updateEpic(Epic updateEpic)  throws ManagerSaveException  {
        Epic epic = epics.get(updateEpic.getId());
            if(!updateEpic.equals(epic)){
                epics.put(updateEpic.getId(), updateEpic);
                }
    }

    @Override
    public void deleteEpic(int id) throws ManagerSaveException {
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
    public TaskStatus updateEpicStatus(Epic epic) throws ManagerSaveException {
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
    public ArrayList<Subtask> getAllSubtasks() throws ManagerSaveException  {
        ArrayList<Subtask> allSubtasks = new ArrayList<>();
        for (Integer id : subtasks.keySet()) {
            allSubtasks.add(subtasks.get(id));
        }
        return allSubtasks;

    }

    @Override
    public void deleteAllSubtasks() throws ManagerSaveException {
        for (Epic value : epics.values()) {
            value.cleanSubtaskIds();
            updateEpicStatus(value);

        }
        for (Subtask subtask : subtasks.values()) {

            historyManager.remove(subtask.getId());
        }
        subtasks.clear();

    }

    @Override
    public Subtask getSubtask(int id)  throws ManagerSaveException {
        Subtask idSubtask = subtasks.get(id);
        historyManager.addTask(idSubtask);
        return idSubtask;
    }


    @Override
    public int addNewSubtask(Subtask subtask) throws ManagerSaveException  {
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
    public void updateSubtask(Subtask updateSubtask) throws ManagerSaveException  {
        Subtask subtask = subtasks.get(updateSubtask.getId());
            if (!updateSubtask.equals(subtask)) {
                subtasks.put(updateSubtask.getId(), updateSubtask);
                }
        Epic subEpic = epics.get(updateSubtask.getEpicId());
        updateEpicStatus(subEpic);
    }

    @Override
    public void deleteSubtask(int id) throws ManagerSaveException {
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
    public List<Subtask> getEpicSubtasks(int epicId) throws ManagerSaveException {
        List<Subtask> epicSubtasks = new ArrayList<>();
        for (Integer id : subtasks.keySet()) {
            if (subtasks.get(id).getEpicId() == epicId) {
                epicSubtasks.add(subtasks.get(id));
            }
        }
        return epicSubtasks;
    }

    @Override
    public List<Task> getHistory() throws ManagerSaveException {
        return historyManager.getHistory();
    }


}
