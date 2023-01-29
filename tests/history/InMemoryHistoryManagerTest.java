package history;

import manager.HistoryManager;
import manager.InMemoryHistoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InMemoryHistoryManagerTest {
    protected Task task;
    protected Epic epic;
    protected Subtask subtask;
    HistoryManager historyManager;

    @BeforeEach
    void setUp(){
    historyManager = new InMemoryHistoryManager();
    task = new Task("Task 1", "description 1", LocalDateTime.of(2023,01,28,22,30),15);
//    epic = new Epic;
//    subtask = new Subtask();
    }

    @Test
    void getHistory(){
        assertNotNull(historyManager.getHistory(), "История пуста");
    }

    @Test
    void add(){

    }

    @Test
    void addTwice(){

    }

    @Test
    void addDifferentTask(){

    }
}
