package manager;

import tasks.*;
import java.util.ArrayList;
import java.util.List;

public class CSVTaskFormat {
    public static String toString(Task task){
        String taskToString = "";
        if (task.getType().equals(TaskType.SUBTASK)){
            Subtask subTask = (Subtask) task;
            taskToString = subTask.getId() + ","  + subTask.getType()+ "," + subTask.getName() + "," + subTask.getStatus() + "," + subTask.getDescription() + "," + subTask.getEpicId();
        } else {
            taskToString = task.getId() + "," + task.getType() + "," + task.getName() + "," + task.getStatus() + "," + task.getDescription();
        }
        return taskToString;
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
            Task subtask = new Subtask(id,name,status,description,epicId);
            return subtask;
        } else if(type.equals(TaskType.EPIC)) {
            Task epic = new Epic(id, name,status, description);

            return epic;
        } else{
            Task task = new Task(id, name,status, description);

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
}
