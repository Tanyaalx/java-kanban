package test;

import manager.manager.*;
import manager.tasks.Epic;
import manager.tasks.SubTask;
import manager.tasks.Task;
import manager.tasks.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;
    private TaskManager taskManager;

    @BeforeEach
    public void init() {
        historyManager = Managers.getDefaultHistory();
    }

    @Test
    void addTaskInHistory() {
        taskManager = Managers.getDefaultManager();

        Task task1 = new Task("Task 1", "description 1", TaskStatus.IN_PROGRESS);
        Task task2 = new Task("Task 2", "description 2", TaskStatus.NEW);

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        historyManager.add(task1);
        historyManager.add(task2);

        System.out.println("task1 id: " + task1.getId());
        System.out.println("task2 id: " + task2.getId());

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "История сохранена неверно");
        assertEquals(task1, history.get(0), "История сохранена неверно");
        assertEquals(task2, history.get(1), "История сохранена неверно");
    }

    @Test
    void removeTasks() {
        taskManager = Managers.getDefaultManager();

        Task task1 = new Task("Task 1", "description 1", TaskStatus.IN_PROGRESS);
        Task task2 = new Task("Task 2", "description 2", TaskStatus.NEW);

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        historyManager.add(task1);
        historyManager.add(task2);

        System.out.println("task1 id: " + task1.getId());
        System.out.println("task2 id: " + task2.getId());

        System.out.println("До удаления: " + historyManager.getHistory());
        historyManager.remove(task1.getId());

        System.out.println("После удаления: " + historyManager.getHistory());

        List<Task> history = historyManager.getHistory();
        assertNotNull(history, "Список истории отсутствует");
        assertEquals(1, history.size(), "История сохранена неверно");
        assertEquals(task2, history.get(0));
    }

    @Test
    void addDuplicate() {
        HistoryManager historyManager = Managers.getDefaultHistory();

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

    @Test
    void subTaskIdShouldNotBeInEpicAfterDeletion() {
        taskManager = Managers.getDefaultManager();

        Epic epic = new Epic("Epic 1", "description 1");
        taskManager.addEpic(epic);

        SubTask subTask = new SubTask("SubTask 1", "description 1", epic.getId(), TaskStatus.NEW);
        taskManager.addSubTask(subTask);

        int subTaskId = subTask.getId();
        taskManager.deleteTaskById(subTaskId);

        assertNull(taskManager.getSubTaskById(subTaskId), "SubTask не удалена из Epic");
        assertFalse(epic.getSubTasksIdList().contains(subTaskId));
    }

    @Test
    void epicDoNotHaveDeletedSubTasksId() {
        taskManager = Managers.getDefaultManager();

        Epic epic = new Epic("Epic 1", "description 1");
        taskManager.addEpic(epic);

        SubTask subTask1 = new SubTask("SubTask 1", "description 1", epic.getId(), TaskStatus.NEW);
        SubTask subTask2 = new SubTask("SubTask 2", "description 2", epic.getId(), TaskStatus.NEW);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);

        taskManager.deleteSubTaskById(subTask1.getId());

        assertFalse(epic.getSubTasksIdList().contains(subTask1.getId()));
        assertTrue(epic.getSubTasksIdList().contains(subTask2.getId()));
    }

    @Test
    void taskFieldsChangedInSettersShouldBeSavedCorrectly() {
        taskManager = Managers.getDefaultManager();

        Task task = new Task("Task 1", "description 1", TaskStatus.NEW);
        taskManager.addTask(task);

        task.setTitle("Task 123");
        task.setDescription("description 123");
        task.setStatus(TaskStatus.IN_PROGRESS);

        Task updatedTask = taskManager.getTask(task.getId());

        assertEquals("Task 123", updatedTask.getTitle());
        assertEquals("description 123", updatedTask.getDescription());
        assertEquals(TaskStatus.IN_PROGRESS, updatedTask.getStatus());
    }
}