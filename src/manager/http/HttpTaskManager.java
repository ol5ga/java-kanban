package manager.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import manager.Managers;
import manager.file.FileBackedTasksManager;
import manager.http.KVTaskClient;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HttpTaskManager extends FileBackedTasksManager {

    private final Gson gson;
    private final KVTaskClient client;



    public HttpTaskManager(int port, boolean load) throws IOException, InterruptedException {
        super(null);
        gson = Managers.getGson();
        client = new KVTaskClient(8078);
        if (load){
            load();
        }
    }
    public HttpTaskManager(int port) throws IOException, InterruptedException {
        this(port,false);
    }

    public void load(){
        ArrayList<Task> tasks = gson.fromJson(client.load("tasks"), new TypeToken<ArrayList<Task>>() {
        }.getType());
        addTasks(tasks);

        ArrayList<Epic> epics = gson.fromJson(client.load("epics"), new TypeToken<ArrayList<Epic>>() {
        }.getType());
        addTasks(epics);

        ArrayList<Subtask> subtasks = gson.fromJson(client.load("subtasks"), new TypeToken<ArrayList<Subtask>>() {
        }.getType());
        addTasks(subtasks);

        List<Integer> history = gson.fromJson(client.load("history"), new TypeToken<ArrayList<Integer>>() {
        }.getType());
        for (Integer taskId: history){
            historyManager.addTask(findTask(taskId));
        }


    }

    @Override
    protected void save(){
        String jsonTasks = gson.toJson(new ArrayList<>(tasks.values()));
        client.put("tasks", jsonTasks);
        String jsonSubtasks = gson.toJson(new ArrayList<>(subtasks.values()));
        client.put("subtasks", jsonSubtasks);
        String jsonEpics = gson.toJson(new ArrayList<>(epics.values()));
        client.put("epics", jsonEpics);

        String jsonHistory = gson.toJson(historyManager.getHistory().stream().map(Task::getId).collect(Collectors.toList()));
        client.put("history", jsonHistory);
    }

    protected void addTasks(List<? extends Task> tasks){
        for (Task task: tasks) {
            final int id = task.getId();
            if (id > getId) {
                getId = id;
            }
            TaskType type = task.getType();
            if (type == TaskType.TASK) {
                this.tasks.put(id, task);
                prioritizedTasks.add(task);
            } else if (type == TaskType.SUBTASK) {
                subtasks.put(id, (Subtask) task);
                prioritizedTasks.add(task);
            } else if(type == TaskType.EPIC){
                epics.put(id, (Epic) task);
            }
        }
    }


}


