package history;

import manager.HistoryManager;
import manager.InMemoryHistoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryHistoryManagerTest {
    protected Task task;
    protected Task task2;
    protected Epic epic;
    protected Subtask subtask;
    HistoryManager historyManager;
    @BeforeEach
    void setUp(){
    historyManager = new InMemoryHistoryManager();
        task = new Task(1,"Task 1", "description 1", LocalDateTime.of(2023,01,28,22,30),15);
        epic = new Epic(2,"Epic 1", "epic-description 1");
        subtask = new Subtask(3,"Subtask 1", "sub-description 1", 2,LocalDateTime.of(2023,01,31,13,25),25);
        task2 = new Task(4,"Task 2", "description 2", LocalDateTime.of(2023,01,29,22,30),15);
    }


    @Test
    void getHistory(){

        assertTrue(historyManager.getHistory().isEmpty(), "История не пуста");
        historyManager.addTask(task);
        assertFalse(historyManager.getHistory().isEmpty(), "История пуста");

    }

    @Test
    void add(){
        historyManager.addTask(task);
        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "Размер истории не правильный");

        historyManager.addTask(task2);
        history = historyManager.getHistory();
        assertEquals(2, history.size(), "Количество задач не совпадает");
    }


    @Test
    void addDifferentTask(){
        historyManager.addTask(task);
        historyManager.addTask(epic);
        final List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "Количество задач не совпадает");
    }

    @Test
    void addRepeat(){
        historyManager.addTask(task);
        historyManager.addTask(epic);
        historyManager.addTask(subtask);
        List<Task> history = historyManager.getHistory();
        int historySize = history.size();
        historyManager.addTask(task);
        history = historyManager.getHistory();
        assertEquals(historySize, history.size(), "Количество задач не совпадает");
        assertNotEquals(task, history.get(0),"Дублирование работает неверно");
    }

    @Test
    void removeFirst(){
        historyManager.addTask(task);
        historyManager.addTask(epic);
        historyManager.addTask(subtask);
        List<Task> history = historyManager.getHistory();
        int historySize = history.size();
        historyManager.remove(1);
        history = historyManager.getHistory();
        assertEquals(historySize -1, history.size(), "Количество задач не совпадает");
        assertNotEquals(task, history.get(0),"Первая задача не удалена");
    }

    @Test
    void removeMiddle(){
        historyManager.addTask(task);
        historyManager.addTask(epic);
        historyManager.addTask(subtask);
        List<Task> history = historyManager.getHistory();
        int historySize = history.size();
        historyManager.remove(2);
        history = historyManager.getHistory();
        assertEquals(historySize -1, history.size(), "Количество задач не совпадает");
        assertNotEquals(epic, history.get(1),"Задача в середине истории не удалена");

    }
    @Test
    void removeLast(){
        historyManager.addTask(task);
        historyManager.addTask(epic);
        historyManager.addTask(subtask);
        List<Task> history = historyManager.getHistory();
        int historySize = history.size();
        historyManager.remove(3);
        history = historyManager.getHistory();
        assertEquals(historySize -1, history.size(), "Количество задач не совпадает");

    }
}
