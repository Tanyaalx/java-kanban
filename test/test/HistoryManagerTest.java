package test;

import manager.manager.*;
import manager.tasks.Epic;
import manager.tasks.SubTask;
import manager.tasks.Task;
import manager.tasks.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {
    private InMemoryHistoryManager historyManager;
    private Task task1;
    private Task task2;
    private SubTask subTask1;
    private SubTask subTask2;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
        task1 = new Task("Task 1", "description 1", 1, TaskStatus.NEW);
        task2 = new Task("Task 2", "description 2", 2, TaskStatus.NEW);
        subTask1 = new SubTask("subTask 1", "description 1", 3, TaskStatus.NEW);
        subTask2 = new SubTask("subTask 2", "description 2", 4, TaskStatus.NEW);
    }


    @Test
    void addHistory() {
        historyManager.addHistory(task1);
        List<Task> history = historyManager.getHistory();
        assertNotNull(history, "Список истории отсутствует");
        assertEquals(1, history.size(), "История пустая");
        assertEquals(1, task1.getId(), "История сохранена неверно");
    }

    @Test
    void getHistory() {
        List<Task> history = historyManager.getHistory();
        assertNotNull(history, "Список истории отсутствует");
        assertTrue(history.isEmpty(), "История не пустая");

        historyManager.addHistory(task1);
        history = historyManager.getHistory();
        assertEquals(1, history.size(), "История не сохранена");

        historyManager.remove(1);
        history = historyManager.getHistory();
        assertTrue(history.isEmpty(), "История не пустая");

        historyManager.addHistory(task1);
        historyManager.addHistory(task1);
        history = historyManager.getHistory();
        assertEquals(1, history.size(), "История сохранена неверно");
        assertEquals(1, task1.getId(), "История сохранена неверно");
    }

    @Test
    void remove() {
        historyManager.addHistory(task1);
        historyManager.addHistory(task2);
        historyManager.addHistory(subTask1);
        List<Task> history = historyManager.getHistory();
        assertNotNull(history, "Список истории отсутствует");
        assertEquals(3, history.size(), "История сохранена неверно");

        historyManager.remove(1);
        history = historyManager.getHistory();
        assertEquals(2, history.size(), "История сохранена неверно");
        assertEquals(2, history.get(0).getId(), "История сохранена неверно");
        assertEquals(3, history.get(1).getId(), "История сохранена неверно");

        historyManager.addHistory(subTask2);
        historyManager.remove(3);
        history = historyManager.getHistory();
        assertEquals(2, history.size(), "История сохранена неверно");
        assertEquals(2, history.get(0).getId(), "История сохранена неверно");
        assertEquals(4, history.get(1).getId(), "История сохранена неверно");

        historyManager.remove(4);
        history = historyManager.getHistory();
        assertEquals(1, history.size(), "История сохранена неверно");
        assertEquals(2, history.get(0).getId(), "История сохранена неверно");
    }

}