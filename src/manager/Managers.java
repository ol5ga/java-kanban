package manager;

import Http.HttpTaskManager;
import Http.KVServer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class Managers {
    public static TaskManager getDefault(){
        return new InMemoryTaskManager();
    }
    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefaultFile(){
        return new FileBackedTasksManager(new File("resources/task.csv"));
    }

    public static TaskManager getDefaultHttp() throws IOException, InterruptedException {
        return new HttpTaskManager(KVServer.PORT);
    }

    public static Gson getGson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime .class, new LocalDateTimeTypeAdapter());
        Gson gson = gsonBuilder.create();
        return gson;
    }
}
