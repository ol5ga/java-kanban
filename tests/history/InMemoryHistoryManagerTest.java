package history;

import manager.HistoryManager;
import manager.InMemoryHistoryManager;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        assertNotNull(historyManager.getHistory(), "История пуста");
    }

    @Test
    void add(){
        historyManager.addTask(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    void addTwice(){
        historyManager.addTask(task);
        historyManager.addTask(task2);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(2, history.size(), "Количество задач не совпадает");
    }

    @Test
    void addDifferentTask(){
        historyManager.addTask(task);
        historyManager.addTask(epic);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(2, history.size(), "Количество задач не совпадает");
    }

    @Test
    void remove(){
        historyManager.addTask(task);
        historyManager.addTask(epic);
        historyManager.remove(1);
        final List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "Количество задач не совпадает");
    }
}
