package manager;
import Http.TaskHandler;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import tasks.Task;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

public class HttpTaskServer {
    public static final int PORT = 8080;
    private final HttpServer server;
    private final Gson gson;
    private final TaskManager manager;


    public HttpTaskServer() throws IOException {
        this(Managers.getDefaultFile());
    }

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.manager = taskManager;
        server = HttpServer.create(new InetSocketAddress("localhost", PORT),0);
        HttpContext context = server.createContext("/tasks",this::handler);//new TaskHandler(Managers.getDefaultFile())
        gson = Managers.getGson();


    }

    public void handler(HttpExchange h){
        String response;
        try (h) {
            System.out.println("\n/tasks: " + h.getRequestURI());
            final String path = h.getRequestURI().getPath().substring(7);
            switch (path) {
                case "" -> {
                    if (!h.getRequestMethod().equals("GET")) {
                        System.out.println("Ожидается GET-запрос, а получен " + h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                    }
                    response = gson.toJson(manager.getPrioritizedTasks());
                    sendText(h, response);
                }

                case "history" -> {
                    if (!h.getRequestMethod().equals("GET")) {
                        System.out.println("Ожидается GET-запрос, а получен " + h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                    }
                    response = gson.toJson(manager.getHistory());
                    sendText(h, response);

                }
                case "task" -> handleTask(h);

                default -> {
                    System.out.println("Получен неизвестный запрос " + h.getRequestMethod());
                    h.sendResponseHeaders(404, 0);
                }
            }
        } catch (Exception e){
            e.printStackTrace();

        }
    }


    private void handleTask(HttpExchange h) throws IOException{
    final String query = h.getRequestURI().getQuery();
    String response;
        switch (h.getRequestMethod()) {
            case "GET" -> {
                if (query == null) {
                    List<Task> tasks = manager.getAllTasks();
                    response = gson.toJson(tasks);
                    System.out.println("Получили все задачи");
                    sendText(h, response);
                    return;
                    }
                String idString = query.substring(3);
                int id = Integer.parseInt(idString);
                final Task task = manager.getTask(id);
                response = gson.toJson(task);
                System.out.println("Получили задачу с id " + id);
                sendText(h, response);
            }
            case "POST" -> {
                String json = readText(h);
                if (json.isEmpty()) {
                    System.out.println("В теле запроса должны быть данные задачи");
                    h.sendResponseHeaders(400, 0);
                    return;
                }
                final Task task = gson.fromJson(json, Task.class);
                Integer id = task.getId();
                if (id != 0) {
                    manager.updateTask(task);
                    System.out.println("Обновлена задача id= " + id);
                    h.sendResponseHeaders(200, 0);
                } else {
                    manager.addNewTask(task);
                    System.out.println("Создана новая задача под id=" + id);
                    response = gson.toJson(task);
                    sendText(h, response);
                }

            }
            case "DELETE" -> {
                if (query == null){
                    manager.deleteAllTasks();
                    System.out.println("Все задачи удалены");
                    h.sendResponseHeaders(200,0);
                    return;
                }
                String idString = query.substring(3);
                int id = Integer.parseInt(idString);
                manager.deleteTask(id);
                System.out.println("Задача с id " + id + "удалена");
                h.sendResponseHeaders(200,0);
            }
            default -> {
                System.out.println("Получен неизвестный запрос " + h.getRequestMethod());
                h.sendResponseHeaders(404, 0);
            }

        }

    }



    protected void sendText(HttpExchange ex, String text) throws IOException{
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().add("Content_Type", "application/json");
        ex.sendResponseHeaders(200,resp.length );
        OutputStream stream = ex.getResponseBody();
        stream.write(text.getBytes(StandardCharsets.UTF_8));
        stream.close();
    }
     protected String readText(HttpExchange ex) throws IOException {
        return new String(ex.getRequestBody().readAllBytes(),StandardCharsets.UTF_8);

     }

    public void start(){
        System.out.println("Сервер запущен");
        server.start();
    }

    public void stop(){
        server.stop(0);
        System.out.println("Сервер остановлен");
    }
}
