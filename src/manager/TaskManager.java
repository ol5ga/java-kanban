package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface TaskManager {

    public List<Task> getAllTasks() throws ManagerSaveException;

    public void deleteAllTasks() throws ManagerSaveException;

    public Task getTask(int id) throws ManagerSaveException;

    public int addNewTask(Task task) throws ManagerSaveException;

    public void updateTask(Task updateTask) throws ManagerSaveException;

    public void deleteTask(int id) throws ManagerSaveException ;

    public List<Epic> getAllEpics() throws ManagerSaveException;

    public void deleteAllEpics() throws ManagerSaveException ;

    public Epic getEpic(int id) throws ManagerSaveException ;

    public int addNewEpic(Epic epic) throws ManagerSaveException ;

    public void updateEpic(Epic updateEpic) throws ManagerSaveException ;

    public void deleteEpic(int id) throws ManagerSaveException ;

    public TaskStatus updateEpicStatus(Epic epic) throws ManagerSaveException ;

    public List<Subtask> getAllSubtasks() throws ManagerSaveException ;

    public void deleteAllSubtasks() throws ManagerSaveException ;

    public Subtask getSubtask(int id) throws ManagerSaveException ;

    public int addNewSubtask(Subtask subtask) throws ManagerSaveException ;

    public void updateSubtask(Subtask updateSubtask) throws ManagerSaveException ;
    public void updateEpicTime(Epic epic) throws ManagerSaveException;
    public int updateEpicDuration(Epic epic) throws ManagerSaveException;

    public void deleteSubtask(int id) throws ManagerSaveException ;

    public List<Subtask> getEpicSubtasks(int epicId) throws ManagerSaveException;

    public List<Task> getHistory() throws ManagerSaveException;

    public Set<Task> getPrioritizedTasks();

    public void checkTime(Task task) throws ManagerSaveException;

}


