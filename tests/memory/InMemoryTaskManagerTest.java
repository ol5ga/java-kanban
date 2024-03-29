package memory;

import manager.InMemoryTaskManager;
import manager.exceptions.ManagerSaveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.TaskManagerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    public void setUp() throws ManagerSaveException {
        taskManager = new InMemoryTaskManager();
        emptyManager = new InMemoryTaskManager();
        initTasks();
    }

    @Test
    public void createInMemoryTaskManager() throws ManagerSaveException {
        assertNotNull(taskManager.getAllTasks(),"Возвращает пустой список");
        assertNotNull(taskManager.getPrioritizedTasks(),"Возвращает пустой список");

    }





}
