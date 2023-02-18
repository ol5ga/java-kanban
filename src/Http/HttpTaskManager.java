package Http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import manager.FileBackedTasksManager;
import manager.Managers;
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


        ArrayList<Epic> epics = gson.fromJson(client.load("epics"), new TypeToken<ArrayList<Epic>>() {
        }.getType());


        ArrayList<Subtask> subtasks = gson.fromJson(client.load("epics"), new TypeToken<ArrayList<Subtask>>() {
        }.getType());


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
        client.put("subtasks", jsonTasks);
        String jsonEpics = gson.toJson(new ArrayList<>(epics.values()));
        client.put("epics", jsonTasks);

        String jsonHistory = gson.toJson(historyManager.getHistory().stream().map(Task::getId).collect(Collectors.toList()));
        client.put("history", jsonHistory);
    }


}


