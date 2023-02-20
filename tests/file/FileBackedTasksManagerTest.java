package file;

import manager.file.FileBackedTasksManager;
import manager.exceptions.ManagerSaveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Task;
import tasks.TaskManagerTest;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    private File file;
    private File emptyFile;
    @BeforeEach
    public void setUp() throws ManagerSaveException {
        file = new File("resources/test.csv");
        emptyFile = new File("resources/emptyTest.csv");
        taskManager = new FileBackedTasksManager(file);
        emptyManager = new FileBackedTasksManager(emptyFile);
        initTasks();
    }



    @Test
    public void loadFromFile() throws ManagerSaveException, IOException {
        FileBackedTasksManager taskManagerEmpty = FileBackedTasksManager.loadFromFile(emptyFile);
        assertTrue(taskManagerEmpty.getAllTasks().isEmpty(), "Вовращает не пустой список");
        assertTrue(taskManagerEmpty.getPrioritizedTasks().isEmpty(),"Возвращает не пустой список");
        assertTrue(taskManagerEmpty.getHistory().isEmpty(),"Возвращает не пустой список истории");
        Epic emptyEpic = new Epic("Epic 2", "epic-description 2");
        emptyManager.addNewEpic(emptyEpic);
        taskManagerEmpty = FileBackedTasksManager.loadFromFile(emptyFile);
        assertTrue(taskManagerEmpty.getAllEpics().isEmpty(), "Вовращает не пустой список");
        assertTrue(taskManagerEmpty.getHistory().isEmpty(),"Возвращает не пустой список истории");

        FileBackedTasksManager taskManager2 = FileBackedTasksManager.loadFromFile(file);
        final List<Task> tasks = taskManager2.getAllTasks();
        assertNotNull(tasks, "Вовращает пустой список");
        assertEquals(taskManager.getTask(1), taskManager2.getTask(1), "Возращает неодинаковые задачи");
        assertEquals(1,tasks.size(), "Возвращает неверный список");
        assertNotNull(taskManager2.getPrioritizedTasks(),"Возвращает пустой список");
        assertEquals(taskManager.getHistory().size(),taskManager2.getHistory().size(), "Возвращает неверную историю");
  }
}
