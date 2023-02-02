package manager;

import tasks.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager  extends InMemoryTaskManager{
    private final File file;


    public FileBackedTasksManager(File file){
        this.file = file;
    }



   public static FileBackedTasksManager loadFromFile(File file) throws IOException, ManagerSaveException {
        final FileBackedTasksManager taskManager = new FileBackedTasksManager(file);
        FileReader reader = new FileReader(file);
        BufferedReader readFile = new BufferedReader(reader);
        List <String> lines = new ArrayList<>();
        int getId = 0;

        while(readFile.ready()){
         String line = readFile.readLine();
            lines.add(line);
        }

        for (int i=1; i<lines.size()-2; i++){
            String taskLine = lines.get(i);
            Task task = CSVTaskFormat.fromString(taskLine);
            if (task.getType().equals(TaskType.TASK)) {
                taskManager.tasks.put(task.getId(), task);
            } else if(task.getType().equals(TaskType.EPIC)){
                Epic epic = (Epic)task;
                taskManager.epics.put(task.getId(), epic);
            } else {
                taskManager.subtasks.put(task.getId(), (Subtask)task);
            }
            if (task.getId() > getId){
                getId = task.getId();
            }
        }
        String historyLine = lines.get(lines.size()-1);
       List<Integer> historyIds = CSVTaskFormat.historyFromString(historyLine);
       for (Integer id : historyIds) {
           Task task = taskManager.findTask(id);
           taskManager.historyManager.addTask(task);
       } return taskManager;
    }
    protected void save() throws ManagerSaveException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
           writer.write("id,type,name,status,description,localtime,duration,epic\n");
            for (Task task : tasks.values()) {
               String str = CSVTaskFormat.toString(task);
                writer.write(str + "\n");
            }
            for (Epic epic : epics.values()) {
                String str = CSVTaskFormat.toString(epic);
                writer.write(str + "\n");
            }
            for (Subtask sub : subtasks.values()) {
                String str = CSVTaskFormat.toString(sub);
                writer.write(str + "\n");
            }
            writer.write("\n" + CSVTaskFormat.historyToString(historyManager));

        } catch (IOException exp) {
            throw new ManagerSaveException("Ошибка сохранения");
        }
    }
    public Task findTask(int id){
       if (tasks.containsKey(id)){
            Task task = tasks.get(id);
            return task;
        } else if(epics.containsKey(id)){
           Epic epic = epics.get(id);
           return epic;
       } else {
           Subtask subtask = subtasks.get(id);
           return subtask;
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

    @Override
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
    public List<Epic> getAllEpics() throws ManagerSaveException {
        List<Epic> epics = super.getAllEpics();
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
        List<Subtask> subtasks = super.getEpicSubtasks(id);
        ArrayList<Integer> subId = new ArrayList<>();
        for (Subtask subtask : subtasks) {
            Integer sId = subtask.getId();
            subId.add(sId);
        }
        epic.setSubtaskIds(subId);
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
