package tasks;

import manager.ManagerSaveException;
import manager.TaskManager;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class TaskManagerTest <T extends TaskManager> {
    protected T taskManager;
   protected T emptyManager;
    protected Task task;
    protected Epic epic;
    protected Subtask subtask;



    protected void initTasks() throws ManagerSaveException {
         task = new Task("Task 1", "description 1", LocalDateTime.of(2023,01,28,22,30),15);
        int taskId = taskManager.addNewTask(task);
        epic = new Epic("Epic 1", "epic-description 1");
        int epicId = taskManager.addNewEpic(epic);
        subtask = new Subtask("Subtask 1", "sub-description 1", 2,LocalDateTime.of(2023,01,31,13,25),25);
        int subtask1Id = taskManager.addNewSubtask(subtask);
    }


   @Test
    void getAllTasks() throws ManagerSaveException{
       final List<Task> emptyTasks = emptyManager.getAllTasks();
       assertTrue(emptyTasks.isEmpty(), "Список задач должен быть пустым");

       Task task2 = new Task("Task 2", "description 2",LocalDateTime.of(2023,01,29,10,00),15);
        int task2Id = taskManager.addNewTask(task2);
        final List<Task> tasks = taskManager.getAllTasks();
        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(2, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void getAllEpics() throws ManagerSaveException{
        final List<Epic> emptyEpics = emptyManager.getAllEpics();
        assertTrue(emptyEpics.isEmpty(), "Список задач должен быть пустым");
        Epic epic2 = new Epic("Epic 1", "epic-description 1");
        int epic2Id = taskManager.addNewEpic(epic2);
        final List<Epic> epics = taskManager.getAllEpics();
        assertNotNull(epics, "Задачи на возвращаются.");
        assertEquals(2, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.get(0), "Задачи не совпадают.");

    }

    @Test
    void getAllSubtasks() throws ManagerSaveException  {
        final List<Subtask> emptySub = emptyManager.getAllSubtasks();
        assertTrue(emptySub.isEmpty(), "Список задач должен быть пустым");
        Subtask subtask2 = new Subtask("Subtask 2","sub-description 2", 2,LocalDateTime.of(2023,01,30,15,30),15);
        int subtask2Id = taskManager.addNewSubtask(subtask2);
        final List<Subtask> subs = taskManager.getAllSubtasks();
        assertNotNull(subs, "Задачи на возвращаются.");
        assertEquals(2, subs.size(), "Неверное количество задач.");
        assertEquals(subtask, subs.get(0), "Задачи не совпадают.");
    }
    @Test
    void deleteAllTasks() throws ManagerSaveException {
        final List<Task> emptyTasks = emptyManager.getAllTasks();
        emptyManager.deleteAllTasks();
        assertTrue(emptyTasks.isEmpty(), "Список задач должен быть пустым");

        Task task2 = new Task("Task 2", "description 2",LocalDateTime.of(2023,01,29,23,00),15);
        int task2Id = taskManager.addNewTask(task2);
        final List<Task> tasks = taskManager.getAllTasks();
        assertNotNull(tasks, "Список пуст");
        taskManager.deleteAllTasks();
        final List<Task> clearTasks = taskManager.getAllTasks();
        assertTrue(clearTasks.isEmpty(), "Список не очистился");
    }

    @Test
    void deleteAllEpics() throws ManagerSaveException {
        final List<Epic> emptyEpics = emptyManager.getAllEpics();
        emptyManager.deleteAllEpics();
        assertTrue(emptyEpics.isEmpty(), "Список задач должен быть пустым");

        Epic epic2 = new Epic("Epic 1", "epic-description 1");
        int epic2Id = taskManager.addNewEpic(epic2);
        final List<Epic> epics = taskManager.getAllEpics();
        assertNotNull(epics, "Список пуст");
        taskManager.deleteAllEpics();
        final List<Epic> clearEpics = taskManager.getAllEpics();
        assertTrue(clearEpics.isEmpty(), "Список не очистился");
    }

    @Test
    void deleteAllSubtasks() throws ManagerSaveException {
        final List<Subtask> emptySub = emptyManager.getAllSubtasks();
        emptyManager.deleteAllSubtasks();
        assertTrue(emptySub.isEmpty(), "Список задач должен быть пустым");

        Subtask subtask2 = new Subtask("Subtask 2","sub-description 2", 2,LocalDateTime.of(2023,01,30,15,30),15);
        int subtask2Id = taskManager.addNewSubtask(subtask2);
        final List<Subtask> subs = taskManager.getAllSubtasks();
        assertNotNull(subs, "Список пуст");
        taskManager.deleteAllSubtasks();
        final List<Subtask> clearSubtasks = taskManager.getAllSubtasks();
        assertTrue(clearSubtasks.isEmpty(), "Список не очистился");
    }

    @Test
    void getTask() throws ManagerSaveException {
        Task emptyTask = emptyManager.getTask(1);
        assertNull(emptyTask);

        Task taskTest = taskManager.getTask(1);
        assertNotNull(taskTest, "Задача не возвращается");
        assertEquals(task, taskTest, "Задачи не совпадают.");

        Task taskTest2 = taskManager.getTask(2);
        assertNull(taskTest2);
    }

    @Test
    void getEpic() throws ManagerSaveException {
        Epic emptyEpic = emptyManager.getEpic(1);
        assertNull(emptyEpic);

        Epic epicTest = taskManager.getEpic(2);
        assertNotNull(epicTest, "Задача не возвращается");
        assertEquals(epic, epicTest, "Задачи не совпадают.");

        Epic epicTest2 = taskManager.getEpic(1);
        assertNull(epicTest2);
    }
    @Test
    void getSubtask()  throws ManagerSaveException {
        Subtask emptySub = emptyManager.getSubtask(1);
        assertNull(emptySub);

        Subtask subTest = taskManager.getSubtask(3);
        assertNotNull(subTest, "Задача не возвращается");
        assertEquals(subtask, subTest, "Задачи не совпадают.");

        Subtask subTest2 = taskManager.getSubtask(1);
        assertNull(subTest2);
    }

    @Test
    void addNewTask() throws ManagerSaveException {
        Task addTask = new Task("Task 2", "description 2", LocalDateTime.of(2023,02,02,10,00),25);
        int id = emptyManager.addNewTask(addTask);
        assertEquals(1, id, "Id не совпадают");

        final Task savedTask = taskManager.getTask(1);
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");
        assertNotNull(task.getId(), "Id не присвоилось");

        final List<Task> tasks = taskManager.getAllTasks();
        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");

        Epic addEpic = new Epic("Epic 2", "epic-description 2");
        int idTest = taskManager.addNewTask(addEpic);
        final List<Task> taskWrong = taskManager.getAllTasks();
        assertEquals(0,idTest, "Неверное Id");
        assertEquals(1, taskWrong.size(), "Неверное количество задач.");
    }

    @Test
    void addNewEpic() throws ManagerSaveException  {
        Epic addEpic = new Epic("Epic 2", "epic-description 2");
        int id = emptyManager.addNewEpic(addEpic);
        assertEquals(1, id, "Id не совпадают");

        final Epic savedEpic = taskManager.getEpic(2);
        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");
        assertNotNull(epic.getId(), "Id не присвоилось");

        final List<Epic> epics = taskManager.getAllEpics();
        assertNotNull(epics, "Задачи на возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.get(0), "Задачи не совпадают.");

    }

    @Test
    void addNewSubtask() throws ManagerSaveException  {
        Subtask addSubtask = new Subtask("Subtask 2", "description 2",1, LocalDateTime.of(2023,02,02,12,00),25);
        int id = emptyManager.addNewSubtask(addSubtask);
        assertEquals(0, id, "Нельзя создать подзадачу без эпика");

        Epic addEpic = new Epic("Epic 2", "epic-description 2");
        addSubtask = new Subtask("Subtask 2", "description 2",2, LocalDateTime.of(2023,02,02,12,30),25);
        int epicId = emptyManager.addNewEpic(addEpic);
        int id2 = emptyManager.addNewSubtask(addSubtask);
        assertEquals(3, id2, "Id не совпадают");

        final Subtask savedSubtask = taskManager.getSubtask(3);
        assertNotNull(savedSubtask, "Задача не найдена.");
        assertEquals(subtask, savedSubtask, "Задачи не совпадают.");
        assertNotNull(subtask.getId(), "Id не присвоилось");

        final List<Subtask> subtasks = taskManager.getAllSubtasks();
        assertNotNull(subtasks, "Задачи на возвращаются.");
        assertEquals(1, subtasks.size(), "Неверное количество задач.");
        assertEquals(subtask, subtasks.get(0), "Задачи не совпадают.");

        Subtask subtask2 = new Subtask("Subtask 2","sub-description 2", 3,LocalDateTime.of(2023,01,30,15,30),15);
        assertEquals(0, taskManager.addNewSubtask(subtask2));
    }
    @Test
    void updateTask() throws ManagerSaveException {
        Task update = new Task(1,"Task 1", TaskStatus.DONE, "description NEW", LocalDateTime.of(2023,01,30,22,30),30);
        List<Task> tasks = taskManager.getAllTasks();
        taskManager.updateTask(update);
        List<Task> tasks2 = taskManager.getAllTasks();
        assertNotEquals(tasks.get(0), tasks2.get(0), "Задача не изменилась");

    }
    @Test
    void updateEpic() throws ManagerSaveException {
        Epic update = new Epic(2,"Epic 1",  "description NEW");
        List<Epic> epics = taskManager.getAllEpics();
        taskManager.updateEpic(update);
        List<Epic> epics2 = taskManager.getAllEpics();
        assertNotEquals(epics.get(0), epics2.get(0), "Задача не изменилась");
    }

    @Test
    void updateSubtask() throws ManagerSaveException  {
        Subtask update = new Subtask(3,"Subtask 1", TaskStatus.IN_PROGRESS,"sub-description 1", 2,LocalDateTime.of(2023,02,01,13,00),30);
        List<Subtask> subtasks = taskManager.getAllSubtasks();
        taskManager.updateSubtask(update);
        List<Subtask> subtasks2 = taskManager.getAllSubtasks();
        assertNotEquals(subtasks.get(0), subtasks2.get(0), "Задача не изменилась");
    }

    @Test
    void deleteTask() throws ManagerSaveException {
        taskManager.deleteTask(1);
        assertTrue(taskManager.getAllTasks().isEmpty(), "Задача не удалилась");

    }

    @Test
    void deleteEpic() throws ManagerSaveException {
        taskManager.deleteEpic(2);
        assertTrue(taskManager.getAllEpics().isEmpty(), "Задача не удалилась");
    }

    @Test
    void deleteSubtask() throws ManagerSaveException {
        taskManager.deleteSubtask(3);
        assertTrue(taskManager.getAllSubtasks().isEmpty(), "Задача не удалилась");
    }

    @Test
    void updateEpicStatus() throws ManagerSaveException {
        Epic epic2 = new Epic("Epic 2", "epic-description 2");
        taskManager.updateEpicStatus(epic2);
        assertEquals(TaskStatus.NEW, epic.getStatus(), "Статус эпика без подзадач - NEW");
        Subtask subtask2 = new Subtask("Subtask 2", "sub-description 2", 2,LocalDateTime.of(2023,01,30,15,30),15);
        int subtask2Id = taskManager.addNewSubtask(subtask2);
        taskManager.updateEpicStatus(epic);
        assertEquals(TaskStatus.NEW, epic.getStatus(), "Статус эпика с двумя новыми задачами - NEW");
        subtask2.setStatus(TaskStatus.DONE);
        taskManager.updateEpicStatus(epic);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus(), "Статус не изменился");
        subtask.setStatus(TaskStatus.DONE);
        taskManager.updateEpicStatus(epic);
        assertEquals(TaskStatus.DONE, epic.getStatus(), "Статус с выполненными подзадачами - DONE");
        subtask2.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateEpicStatus(epic);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus(), "Статус не изменился");
    }

    @Test
    void updateEpicTime() throws ManagerSaveException{
        taskManager.updateEpicTime(epic);
        assertEquals(subtask.getStartTime(), epic.getStartTime(), "Время не обновилось");
        Subtask subtask2 = new Subtask("Subtask 2", "sub-description 2", 2, LocalDateTime.now(),10);
        int subtask2Id = taskManager.addNewSubtask(subtask2);
        assertEquals(subtask.getStartTime(),epic.getStartTime(),"Длительность не обновилась" );
    }

    @Test
    void updateEpicDuration() throws ManagerSaveException {
        taskManager.updateEpicDuration(epic);
        assertEquals(subtask.getDuration(), epic.getDuration(), "Длительность не обновилась");
        Subtask subtask2 = new Subtask("Subtask 2", "sub-description 2", 2, LocalDateTime.now(),10);
        int subtask2Id = taskManager.addNewSubtask(subtask2);
        taskManager.updateEpicDuration(epic);
        assertEquals(35,epic.getDuration(),"Длительность не обновилась" );
    }

    @Test
    void getEpicSubtasks() throws ManagerSaveException {
        assertTrue(emptyManager.getEpicSubtasks(1).isEmpty(), "Подчадач не существует");

        Subtask subtask2 = new Subtask("Subtask 2","sub-description 2", 2,LocalDateTime.of(2023,01,30,15,30),15);
        int subtask2Id = taskManager.addNewSubtask(subtask2);
        assertNotNull(taskManager.getEpicSubtasks(2), "Подчадачи не найдены");
        assertEquals(2,taskManager.getEpicSubtasks(2).size(), "Найдены не все подчадачи");
    }

    @Test
    void checkTime() throws ManagerSaveException {
        Task task2 = new Task("Task 2", "description 2", LocalDateTime.of(2023,01,28,22,40),25);
        int task2Id = taskManager.addNewTask(task2);
        assertEquals(2,taskManager.getPrioritizedTasks().size() ,"Добавлена пересекающаяся задача");
    }


}
