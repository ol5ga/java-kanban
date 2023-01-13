package manager;

import tasks.*;

import manager.Managers;
import manager.TaskManager;

import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager  extends InMemoryTaskManager{
    private final File file;


    public FileBackedTasksManager(File file){
        this.file = file;
    }

    public static String toString(Task task){
        String taskToString = "";
        if (task.getType().equals(TaskType.SUBTASK)){
            Subtask subTask = (Subtask) task;
            taskToString = subTask.getId() + ","  + subTask.getType()+ "," + subTask.getName() + "," + subTask.getStatus() + "," + subTask.getDescription() + "," + subTask.getEpicId();
        } else {
            taskToString = task.getId() + "," + task.getType() + "," + task.getName() + "," + task.getStatus() + "," + task.getDescription();

        }return taskToString;
    }

    public static Task fromString(String value) throws ManagerSaveException {
        final String[] values = value.split(",");
        int id = Integer.parseInt(values[0]);
        final TaskType type = TaskType.valueOf(values[1]);
        String name = values[2];
        TaskStatus status = TaskStatus.valueOf(values[3]);
        String description = values[4];
        if (type.equals(TaskType.SUBTASK)){
            int epicId = Integer.parseInt(values[5]);
            Task subtask = new Subtask(id,name,description,epicId);
            return subtask;
        } else{
            Task task = new Task(id,name,description);
            return task;
        }
    }

    static String historyToString(HistoryManager manager){
        String history = "";
        List<Task> historyList = manager.getHistory();
        for (Task task : historyList) {
            if (history.isBlank()){
            history = history + task.getId();
            } else{
                history = history + "," + task.getId();
            }
        }
        return history;
    }

   public static List<Integer> historyFromString(String value) throws ManagerSaveException {
        final String[] historyId = value.split(",");
        final List<Integer> history = new ArrayList<>();
        for (int i = 0; i < historyId.length; i++) {
            int id = Integer.parseInt(historyId[i]);
            history.add(id);
        } return history;

    }

   public static FileBackedTasksManager loadFromFile(File file) throws IOException, ManagerSaveException {
        final FileBackedTasksManager taskManager = new FileBackedTasksManager(file);
        FileReader reader = new FileReader(file);
        BufferedReader readFile = new BufferedReader(reader);
       List <String> lines = new ArrayList<>();
       HashMap<Integer, Task> tasks = new HashMap<>();
       HistoryManager historyManager = Managers.getDefaultHistory();
       int getId = 0;
        while(readFile.ready()){
         String line = readFile.readLine();
            lines.add(line);
        }

        for (int i=1; i<lines.size()-2; i++){
            String taskLine = lines.get(i);
            Task task = fromString(taskLine);
            tasks.put(task.getId(), task);

            }
        String historyLine = lines.get(lines.size()-1);
       List<Integer> historyIds = historyFromString(historyLine);
       for (Integer id : historyIds) {
           System.out.println(id);
           Task task = getTask(id);
           historyManager.addTask(task);
       }
         return taskManager;
    }
    protected void save() throws ManagerSaveException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
           writer.write("id,type,name,status,description,epic\n");
            for (Task task : tasks.values()) {
               String str =  toString(task);
                writer.write(str + "\n");
            }
            for (Epic epic : epics.values()) {
                String str = toString(epic);
                writer.write(str + "\n");
            }
            for (Subtask sub : subtasks.values()) {
                String str = toString(sub);
                writer.write(str + "\n");
            }
            writer.write("\n" + historyToString(historyManager));

        } catch (IOException exp) {
            throw new ManagerSaveException("Ошибка сохранения");
        }
    }
    @Override
    public Task getTask(int id) throws ManagerSaveException {
        final Task task = super.getTask(id);
        save();
        return task;
    }

    @Override
    public ArrayList<Task> getAllTasks() throws ManagerSaveException {
        ArrayList<Task> allTasks = super.getAllTasks();
        save();
        return allTasks;
    }

    public void deleteAllTasks() throws ManagerSaveException {
        super.deleteAllTasks();
        save();
    }

    @Override
    public int addNewTask(Task task) throws ManagerSaveException {
        int id = super.addNewTask(task);
        save();
        return id;
    }

    @Override
    public void updateTask(Task updateTask) throws ManagerSaveException {
        super.updateTask(updateTask);
        save();
    }

    @Override
    public void deleteTask(int id) throws ManagerSaveException {
        super.deleteTask(id);
        save();
    }

    @Override
    public ArrayList<Epic> getAllEpics() throws ManagerSaveException {
        ArrayList<Epic> epics = super.getAllEpics();
        save();
        return epics;
    }

    @Override
    public void deleteAllEpics() throws ManagerSaveException {
        super.deleteAllEpics();
        save();
    }

    @Override
    public Epic getEpic(int id) throws ManagerSaveException {
        Epic epic = super.getEpic(id);
        save();
        return epic;
    }

    @Override
    public int addNewEpic(Epic epic) throws ManagerSaveException {
        int epicId = super.addNewEpic(epic);
        save();
        return epicId;
    }

    @Override
    public void updateEpic(Epic updateEpic) throws ManagerSaveException {
        super.updateEpic(updateEpic);
        save();
    }

    @Override
    public void deleteEpic(int id) throws ManagerSaveException {
        super.deleteEpic(id);
        save();
    }

    @Override
    public TaskStatus updateEpicStatus(Epic epic) throws ManagerSaveException {
        TaskStatus status = super.updateEpicStatus(epic);
        save();
        return status;
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() throws ManagerSaveException {
        ArrayList<Subtask> subtasks = super.getAllSubtasks();
        save();
        return subtasks;
    }

    @Override
    public void deleteAllSubtasks() throws ManagerSaveException {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public Subtask getSubtask(int id) throws ManagerSaveException {
        Subtask subtask =  super.getSubtask(id);
        save();
        return subtask;
    }

    @Override
    public int addNewSubtask(Subtask subtask) throws ManagerSaveException {
        int id = super.addNewSubtask(subtask);
        save();
        return id;
    }

    @Override
    public void updateSubtask(Subtask updateSubtask) throws ManagerSaveException {
        super.updateSubtask(updateSubtask);
        save();
    }

    @Override
    public void deleteSubtask(int id) throws ManagerSaveException {
        super.deleteSubtask(id);
        save();
    }

    @Override
    public List<Subtask> getEpicSubtasks(int epicId) throws ManagerSaveException {
        List<Subtask> subtask = super.getEpicSubtasks(epicId);
        save();
        return subtask;
    }

    @Override
    public List<Task> getHistory() throws ManagerSaveException {
        save();
        return historyManager.getHistory();
    }
}
