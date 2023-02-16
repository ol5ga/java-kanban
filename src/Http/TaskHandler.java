package Http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.Managers;
import manager.TaskManager;
import manager.LocalDateTimeTypeAdapter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class TaskHandler implements HttpHandler {
    private TaskManager taskManager;

    Gson gson = Managers.getGson();

    public TaskHandler(TaskManager taskManager){
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String requestMethod = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath().substring(7);
            switch (path) {
                case "":
                    if (!requestMethod.equals("GET")) {
                        System.out.println("Ожидается GET-запрос, а получен " + requestMethod);
                        exchange.sendResponseHeaders(405, 0);
                    }
                    String response = gson.toJson(taskManager.getPrioritizedTasks());
                    sendText(exchange,response);
                    break;
                case "history":
                    if (!requestMethod.equals("GET")) {
                        System.out.println("Ожидается GET-запрос, а получен " + requestMethod);
                        exchange.sendResponseHeaders(405, 0);
                    }
                    response = gson.toJson(taskManager.getHistory());
                    sendText(exchange,response);
                    break;
                case "task":
                    handleTask(exchange);
                    break;
                case "subtask":
                    handleSubtask(exchange);
                    break;
                case "epic":
                    handleEpic(exchange);
                    break;
                case "subtask/epic":
                    if (!requestMethod.equals("GET")) {
                        System.out.println("Ожидается GET-запрос, а получен " + requestMethod);
                        exchange.sendResponseHeaders(405, 0);
                    }
                    break;
                default:
                    System.out.println("Получен неизвестный запрос " + requestMethod);
                    exchange.sendResponseHeaders(404, 0);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void handleTask(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        String path =exchange.getRequestURI().getPath().substring(12);
        switch(requestMethod){
            case "GET":
                if(path.isEmpty()){
                    String response = gson.toJson(taskManager.getAllTasks());
                } else {
                    String idParam = exchange.getRequestURI().getQuery().substring(3);
                    int id = Integer.parseInt(idParam);
                    final String response = gson.toJson(taskManager.getTask(id));
                    System.out.println("Получили задачу " + id);
                    sendText(exchange,response);
                }
                break;
            case "POST":
                System.out.println("Получен неизвестный запрос " + requestMethod);
                break;

        }
    }

    private void handleSubtask(HttpExchange exchange){

    }

    private void handleEpic(HttpExchange exchange){

    }

    protected void sendText(HttpExchange ex, String text) throws IOException{
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().add("Content_Type", "application/json");
        ex.sendResponseHeaders(200,resp.length );
    }
}
