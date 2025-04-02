package test;

import manager.manager.*;
import manager.tasks.SubTask;
import manager.tasks.Task;
import manager.tasks.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryManagerTest {
    private HistoryManager historyManager;
    private TaskManager taskManager;

    @BeforeEach
    void init() {
        historyManager = Managers.getDefaultHistory();
        taskManager = Managers.getDefault();
    }

    @Test
    void addHistory() {
        Task task = new Task("Task 1", "description 1", TaskStatus.NEW);
        taskManager.addTask(task);
        historyManager.add(task);

        List<Task> history = historyManager.getHistory();
        assertNotNull(history, "Список истории отсутствует");
        assertEquals(1, history.size(), "История пустая");
        assertEquals(task, history.get(0), "История сохранена неверно");
    }

    @Test
    void getHistory() {
        Task task1 = new Task("Task 1", "description 1", TaskStatus.NEW);
        taskManager.addTask(task1);
        historyManager.add(task1);

        List<Task> history = historyManager.getHistory();
        assertNotNull(history, "Список истории отсутствует");
        assertEquals(1, history.size(), "История не сохранена");
    }

    @Test
    void remove() {
        Task task1 = new Task("Task 1", "description 1", TaskStatus.NEW);
        Task task2 = new Task("Task 2", "description 2", TaskStatus.NEW);
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.remove(task1.getId());

        List<Task> history = historyManager.getHistory();
        assertNotNull(history, "Список истории отсутствует");
        assertEquals(1, history.size(), "История сохранена неверно");
        assertEquals(task2, history.get(0));
    }

    @Test
    void emptyHistory() {
        List<Task> history = historyManager.getHistory();
        assertTrue(history.isEmpty());
    }

    @Test
    void addDuplicate() {
        Task task = new Task("Task 1", "description 1", TaskStatus.NEW);
        taskManager.addTask(task);

        historyManager.add(task);
        historyManager.add(task);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "История сохранена неверно");
    }

    @Test
    void removeTaskFromBeginning() {
        Task task1 = new Task("Task 1", "description 1", TaskStatus.NEW);
        Task task2 = new Task("Task 2", "description 2", TaskStatus.NEW);
        Task task3 = new Task("Task 3", "description 3", TaskStatus.NEW);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task1.getId());

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "История сохранена неверно");
        assertEquals(task2, history.get(0), "История сохранена неверно");
        assertEquals(task3, history.get(1), "История сохранена неверно");
    }

    @Test
    void removeTaskFromMiddle() {
        Task task1 = new Task("Task 1", "description 1", TaskStatus.NEW);
        Task task2 = new Task("Task 2", "description 2", TaskStatus.NEW);
        Task task3 = new Task("Task 3", "description 3", TaskStatus.NEW);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task2.getId());

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "История сохранена неверно");
        assertEquals(task1, history.get(0), "История сохранена неверно");
        assertEquals(task3, history.get(1), "История сохранена неверно");
    }

    @Test
    void removeFromEnd() {
        Task task1 = new Task("Task 1", "description 1", TaskStatus.NEW);
        Task task2 = new Task("Task 2", "description 2", TaskStatus.NEW);
        Task task3 = new Task("Task 3", "description 3", TaskStatus.NEW);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task3.getId());

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "История сохранена неверно");
        assertEquals(task1, history.get(0), "История сохранена неверно");
        assertEquals(task2, history.get(1), "История сохранена неверно");
    }
}