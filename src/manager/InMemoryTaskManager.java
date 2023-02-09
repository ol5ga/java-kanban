package manager;

import java.time.LocalDateTime;
import java.util.*;
import java.util.Comparator;

import tasks.*;

public class InMemoryTaskManager implements TaskManager {
    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();
    protected HashMap<Integer, Subtask> subtasks = new HashMap<>();
    public int getId = 0;
    protected final HistoryManager historyManager = Managers.getDefaultHistory();

    Comparator<Task> comparator = Comparator.comparing(Task::getStartTime, Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(Task::getId);
    protected Set<Task> prioritizedTasks = new TreeSet<>(comparator);
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
            try {
                historyManager.remove(task.getId());
                prioritizedTasks.remove(task);
            } catch (NullPointerException exp) {
                System.out.println("История пуста");
            }

        }

        tasks.clear();
    }

    @Override
    public Task getTask(int id) throws ManagerSaveException {
        try{
            Task idTask = tasks.get(id);
            historyManager.addTask(idTask);
            return idTask;
        } catch (NullPointerException exp){
            System.out.println("Такой задачи не существует");
            return null;
        }


    }

    @Override
    public int addNewTask(Task task) throws ManagerSaveException {
        if (task.getType() == TaskType.TASK) {
            final int id = ++getId;
                task.setId(id);
                checkTime(task);
                tasks.put(id, task);
                prioritizedTasks.add(task);
                return id;
        } else{
            return 0;
        }
    }

    @Override
    public void updateTask(Task updateTask) throws ManagerSaveException {
        Task task = tasks.get(updateTask.getId());
        if (!updateTask.equals(task)) {
            prioritizedTasks.remove(task);
            checkTime(updateTask);
            prioritizedTasks.add(updateTask);
            tasks.put(updateTask.getId(), updateTask);
        }
    }

    @Override
    public void deleteTask(int id) throws ManagerSaveException,NullPointerException {
        try {
            prioritizedTasks.remove(tasks.get(id));
            tasks.remove(id);
            if (historyManager.getHistory() != null) {
                historyManager.remove(id);
            }
        }catch (NullPointerException exp){
            System.out.println("Задача не существует");
        }
    }

    @Override
    public List<Epic> getAllEpics() throws ManagerSaveException {
        List<Epic> allEpics = new ArrayList<>();
        for (Epic epic: epics.values()) {
            updateEpicStatus(epic);
            updateEpicTime(epic);
            updateEpicDuration(epic);
        }
        for (Integer id : epics.keySet()) {
            allEpics.add(epics.get(id));
        }
        return allEpics;


    }

    @Override
    public void deleteAllEpics() throws ManagerSaveException {
        for (Epic epic : epics.values()) {
            if(historyManager != null) {
                historyManager.remove(epic.getId());
            }
        }
        epics.clear();
        deleteAllSubtasks();
    }

    @Override
    public Epic getEpic(int id)  throws ManagerSaveException {
        try{
            Epic idEpic = epics.get(id);
            historyManager.addTask(idEpic);
            return idEpic;
        } catch (NullPointerException exp){
            System.out.println("Такой задачи не существует");
            return null;
        }
    }

    @Override
    public int addNewEpic(Epic epic) throws ManagerSaveException  {
            final int id = ++getId;
                epic.setId(id);
                epics.put(id, epic);
                updateEpicTime(epic);
                return id;

     }
    @Override
    public void updateEpic(Epic updateEpic)  throws ManagerSaveException {
        Epic epic = epics.get(updateEpic.getId());
        if (!updateEpic.equals(epic)) {
           updateEpicTime(epic);
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
        try {
            historyManager.remove(id);
        } catch (NullPointerException exp) {
            System.out.println("История пуста");
        }
        prioritizedTasks.remove(epic);
        epics.remove(id);

    }

    @Override
    public TaskStatus updateEpicStatus(Epic epic) throws ManagerSaveException {
        ArrayList <Integer> subs = epic.getSubtaskId();
        TaskStatus stat = TaskStatus.NEW;
        if (subs.isEmpty()) {
            return epic.getStatus();
        } else {
            Subtask first = subtasks.get(subs.get(0));
            for (int i = 0; i < subs.size(); i++) {
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
    public void updateEpicTime(Epic epic) throws ManagerSaveException{
        ArrayList <Integer> subs = epic.getSubtaskId();
        LocalDateTime startTime;
        LocalDateTime endTime;
        if (subs.isEmpty()){
            startTime = null;
            endTime = null;
        } else {
            startTime = getSubtask(subs.get(0)).getStartTime();
            endTime = getSubtask(subs.get(0)).getStartTime();
            for (int i = 1; i < subs.size(); i++) {
                LocalDateTime time = getSubtask(subs.get(i)).getStartTime();
                if (startTime.isAfter(time)) {
                    startTime = time;
                }
                if (endTime.isBefore(time)) {
                    endTime = time;
                }

            }
            epic.setStartTime(startTime);
            epic.setEndTime(endTime);
        }
    }

    @Override
    public int updateEpicDuration(Epic epic) throws ManagerSaveException {
        ArrayList <Integer> subs = epic.getSubtaskId();
        int duration = 0;
        for (Integer sub : subs) {
            duration = duration + getSubtask(sub).getDuration();
            }
        epic.setDuration(duration);
        return duration;
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
            updateEpicTime(value);
            updateEpicDuration(value);
        }
        for (Subtask subtask : subtasks.values()) {
            try {
                historyManager.remove(subtask.getId());
                prioritizedTasks.remove(subtask);
            } catch (NullPointerException exp) {
                System.out.println("История пуста");
            }
        }
        subtasks.clear();

    }

    @Override
    public Subtask getSubtask(int id)  throws ManagerSaveException {
        try {
            Subtask idSubtask = subtasks.get(id);
            try {
                historyManager.addTask(idSubtask);
            } catch (NullPointerException exp) {
                System.out.println("История пуста");
            }
            return idSubtask;
        } catch (NullPointerException exp) {
            System.out.println("Такой задачи не существует");
            return null;
        }
    }


    @Override
    public int addNewSubtask(Subtask subtask) throws ManagerSaveException  {
        final int id = ++getId;
        int idCatch=0;
        Epic subEpic = epics.get(subtask.getEpicId());
        if (subEpic == null){
            System.out.println("Такого эпика не существует");
            subtask.setEpicId(0);
        } else {
            checkTime(subtask);
            subtask.setId(id);
            subtasks.put(id,subtask);
            prioritizedTasks.add(subtask);
            subEpic.getSubtaskId().add(subtask.getId());
            updateEpicStatus(subEpic);
            updateEpicTime(subEpic);
            updateEpicDuration(subEpic);
            idCatch = id;
        } return idCatch;

    }



    @Override
    public void updateSubtask(Subtask updateSubtask) throws ManagerSaveException  {
        Subtask subtask = subtasks.get(updateSubtask.getId());
            if (!updateSubtask.equals(subtask)) {
              try {
                  prioritizedTasks.remove(subtask);
                  checkTime(updateSubtask);
                  prioritizedTasks.add(updateSubtask);
                  subtasks.put(updateSubtask.getId(), updateSubtask);
                  Epic subEpic = epics.get(updateSubtask.getEpicId());
                  updateEpicStatus(subEpic);
                  updateEpicTime(subEpic);
                  updateEpicDuration(subEpic);
                } catch (ManagerSaveException exp) {
                    System.out.println(exp.getMessage());

                }

            }

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
            prioritizedTasks.remove(subtasks.get(id));
            subtasks.remove(id);
            updateEpicStatus(subEpic);
            updateEpicTime(subEpic);
            updateEpicDuration(subEpic);

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
    @Override
    public Set<Task> getPrioritizedTasks(){
        return prioritizedTasks;
    }

    @Override
    public void checkTime(Task task) throws ManagerSaveException {
        if (task.getStartTime() != null) {
        LocalDateTime startTime = task.getStartTime();
        LocalDateTime endTime = startTime.plusMinutes(task.getDuration());
        if (!prioritizedTasks.isEmpty()) {
            for (Task taskTree : prioritizedTasks) {
              if (startTime.isAfter(taskTree.getStartTime()) && startTime.isBefore(taskTree.getEndTime()) || endTime.isAfter(taskTree.getStartTime()) && endTime.isBefore(taskTree.getEndTime())) {
                        throw new TimeCheckException("Задачи нельзя выполнять одновременно");
                    }
                }
            }
        }
    }










}
