package Http;

import com.google.gson.Gson;
import manager.FileBackedTasksManager;
import manager.Managers;

import java.io.File;

public class HttpTaskManager extends FileBackedTasksManager {

    private final Gson gson;
    private final KVTaskClient client;

    public HttpTaskManager(int port) {
        super(null);
        gson = Managers.getGson();
        client = new KVTaskClient();
            }
}
