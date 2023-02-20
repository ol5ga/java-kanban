package manager.http;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.google.gson.Gson;
import manager.Managers;
import manager.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HttpTaskServer {
    public static final int PORT = 8080;
    private final HttpServer server;
    private final Gson gson;
    private final TaskManager manager;


    public HttpTaskServer() throws IOException, InterruptedException {
        this(Managers.getDefault());
    }

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.manager = taskManager;
        server = HttpServer.create(new InetSocketAddress("localhost", PORT),0);
        HttpContext context = server.createContext("/tasks",this::handler);
        gson = Managers.getGson();


    }

    public void handler(HttpExchange h){
        String response;
        try {
            System.out.println("\n/tasks: " + h.getRequestURI());
            final String path = h.getRequestURI().getPath().substring(7);
            switch (path) {
                case "" :
                    if (!h.getRequestMethod().equals("GET")) {
                        System.out.println("Ожидается GET-запрос, а получен " + h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                    }
                    response = gson.toJson(manager.getPrioritizedTasks());
                    sendText(h, response);

                break;
                case "history":
                    if (!h.getRequestMethod().equals("GET")) {
                        System.out.println("Ожидается GET-запрос, а получен " + h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                    }
                    response = gson.toJson(manager.getHistory());
                    sendText(h, response);

                break;

                case "subtask/epic":
                    if (!h.getRequestMethod().equals("GET")) {
                        System.out.println("Ожидается GET-запрос, а получен " + h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                    }
                    final String query = h.getRequestURI().getQuery();
                    String idString = query.substring(3);
                    int id = Integer.parseInt(idString);
                    final List<Subtask> subtasks = manager.getEpicSubtasks(id);
                    response = gson.toJson(subtasks);
                    System.out.println("Получили подзадачи эпика id " + id);
                    sendText(h, response);
                break;
                case "task":
                    handleTask(h);
                    break;
                case "subtask":
                    handleSubtask(h);
                    break;
                case "epic":
                    handleEpic(h);
                    break;
                default:
                    System.out.println("Получен неизвестный запрос " + h.getRequestMethod());
                    h.sendResponseHeaders(404, 0);
                    break;

            }
        } catch (Exception e){
            e.printStackTrace();
            h.close();
        } h.close();
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
                    System.out.println("Создана новая задача под id=" + task.getId());
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

    private void handleSubtask(HttpExchange h) throws IOException {
        final String query = h.getRequestURI().getQuery();
        String response;
        switch (h.getRequestMethod()) {
            case "GET" -> {
                if (query == null) {
                    List<Subtask> subtasks = manager.getAllSubtasks();
                    response = gson.toJson(subtasks);
                    System.out.println("Получили все подзадачи");
                    sendText(h, response);
                    return;
                }
                String idString = query.substring(3);
                int id = Integer.parseInt(idString);
                final Subtask subtask = manager.getSubtask(id);
                response = gson.toJson(subtask);
                System.out.println("Получили подзадачу с id " + id);
                sendText(h, response);
            }
            case "POST" -> {
                String json = readText(h);
                if (json.isEmpty()) {
                    System.out.println("В теле запроса должны быть данные задачи");
                    h.sendResponseHeaders(400, 0);
                    return;
                }
                final Subtask subtask = gson.fromJson(json, Subtask.class);

                Integer id = subtask.getId();
                if (id != 0) {
                    manager.updateSubtask(subtask);
                    System.out.println("Обновлена подзадача id= " + id);
                    h.sendResponseHeaders(200, 0);
                } else {
                    manager.addNewSubtask(subtask);
                    System.out.println("Создана новая подзадача под id=" + subtask.getId());
                    response = gson.toJson(subtask);
                    sendText(h, response);
                }

            }
            case "DELETE" -> {
                if (query == null) {
                    manager.deleteAllSubtasks();
                    System.out.println("Все задачи удалены");
                    h.sendResponseHeaders(200, 0);
                    return;
                }
                String idString = query.substring(3);
                int id = Integer.parseInt(idString);
                manager.deleteSubtask(id);
                System.out.println("Подзадача с id " + id + "удалена");
                h.sendResponseHeaders(200, 0);
            }
            default -> {
                System.out.println("Получен неизвестный запрос " + h.getRequestMethod());
                h.sendResponseHeaders(404, 0);
            }
        }
    }

    private void handleEpic(HttpExchange h) throws IOException {
        final String query = h.getRequestURI().getQuery();
        String response;
        switch (h.getRequestMethod()) {
            case "GET" -> {
                if (query == null) {
                    List<Epic> epics = manager.getAllEpics();
                    response = gson.toJson(epics);
                    System.out.println("Получили все эпики");
                    sendText(h, response);
                    return;
                }
                String idString = query.substring(3);
                int id = Integer.parseInt(idString);
                final Epic epic = manager.getEpic(id);
                response = gson.toJson(epic);
                System.out.println("Получили эпик с id " + id);
                sendText(h, response);
            }
            case "POST" -> {
                String json = readText(h);
                if (json.isEmpty()) {
                    System.out.println("В теле запроса должны быть данные задачи");
                    h.sendResponseHeaders(400, 0);
                    return;
                }
                final Epic epic = gson.fromJson(json, Epic.class);

                Integer id = epic.getId();
                if (id != 0) {
                    manager.updateEpic(epic);
                    System.out.println("Обновлен эпик id= " + id);
                    h.sendResponseHeaders(200, 0);
                } else {
                    if (epic.getSubtaskId() == null){
                        epic.setSubtaskId(new ArrayList<Integer>());
                    }
                    manager.addNewEpic(epic);
                    System.out.println("Создан новый эпик под id=" + epic.getId());
                    response = gson.toJson(epic);
                    sendText(h, response);
                }

            }
            case "DELETE" -> {
                if (query == null) {
                    manager.deleteAllSubtasks();
                    System.out.println("Все эпики удалены");
                    h.sendResponseHeaders(200, 0);
                    return;
                }
                String idString = query.substring(3);
                int id = Integer.parseInt(idString);
                manager.deleteSubtask(id);
                System.out.println("Эпик с id " + id + "удален");
                h.sendResponseHeaders(200, 0);
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
