package file;

import manager.FileBackedTasksManager;
import manager.ManagerSaveException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.TaskManagerTest;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    private File file;

    @BeforeEach
    public void setUp() throws ManagerSaveException {
        file = new File("resources/test.csv");
        taskManager = new FileBackedTasksManager(file);
        initTasks();
    }

    @AfterEach
    protected void tearDown(){
        assertTrue(file.delete());
    }

    @Test
    public void loadFromFile() throws ManagerSaveException, IOException {
        FileBackedTasksManager taskManager2 = FileBackedTasksManager.loadFromFile(file);
        final List<Task> tasks = taskManager2.getAllTasks();
        assertNotNull(tasks, "Вовращает не пустой список");
        assertEquals(1,tasks.size(), "Возвращает не пустой список");
    }
}
