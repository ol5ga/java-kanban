package manager;

import manager.file.FileBackedTasksManager;
import manager.http.HttpTaskManager;
import manager.http.KVServer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import manager.history.HistoryManager;
import manager.history.InMemoryHistoryManager;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class Managers {
    public static TaskManager getDefault() throws IOException, InterruptedException {
        return new HttpTaskManager(KVServer.PORT);
    }
    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefaultFile(){
        return new FileBackedTasksManager(new File("resources/task.csv"));
    }

    public static KVServer getDefaultKVServer() throws IOException {
        final KVServer kvServer = new KVServer();
        kvServer.start();
        return kvServer;
    }
    public static Gson getGson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime .class, new LocalDateTimeTypeAdapter());
        Gson gson = gsonBuilder.create();
        return gson;
    }
}
