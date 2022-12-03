package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TaskManager {

    public List<Task> getAllTasks();

    public void deleteAllTasks();

    public Task getTask(int id);

    public int addNewTask(Task task);

    public void updateTask(Task updateTask);

    public void deleteTask(int id);

    public List<Epic> getAllEpics();

    public void deleteAllEpics();

    public Epic getEpic(int id);

    public int addNewEpic(Epic epic);

    public void updateEpic(Epic updateEpic);

    public void deleteEpic(int id);

    public TaskStatus updateEpicStatus(Epic epic);

    public List<Subtask> getAllSubtasks();

    public void deleteAllSubtasks();

    public Subtask getSubtask(int id);

    public int addNewSubtask(Subtask subtask);

    public void updateSubtask(Subtask updateSubtask);

    public void deleteSubtask(int id);

    public List<Subtask> getEpicSubtasks(int epicId);

    public List<Task> getHistory();

}


