package test;
import manager.manager.*;
import manager.tasks.Epic;
import manager.tasks.SubTask;
import manager.tasks.Task;
import manager.tasks.TaskStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    public void setUp() {
        taskManager = Managers.getDefault();
    }

    @Test
    public void addTask() {
        String title = "Приготовить обед";
        String description = "Нарезать продукты";
        Task task = new Task(title, description, TaskStatus.NEW);

        Task createdTask = taskManager.addTask(task);

        assertNotNull(createdTask.getId());
        assertEquals(TaskStatus.NEW, createdTask.getStatus());
        assertEquals(description, createdTask.getDescription());
        assertEquals(title, createdTask.getTitle());
    }

    @Test
    void idTask() {
        Task task1 = new Task("Task 1", "description 1", TaskStatus.NEW);
        Task task2 = new Task("Task 2", "description 2", TaskStatus.DONE);

        taskManager.addTask(task1);
        task2.setId(task1.getId());

        assertEquals(task1, task2);
    }

    @Test
    void epicsShouldBeEqualIfIdsEqual() {
        Epic epic1 = new Epic("Epic 1", "description 1");
        epic1.setId(1);
        Epic epic2 = new Epic("Epic 2", "description 2");
        epic2.setId(1);
        assertEquals(epic1, epic2);
    }

    @Test
    void subTasksShouldBeEqualIfIdsEqual() {
        SubTask subTask1 = new SubTask("SubTask 1", "description 1", 2, TaskStatus.NEW);
        subTask1.setId(1);
        SubTask subTask2 = new SubTask("SubTask 2", "description 2", 2, TaskStatus.DONE);
        subTask2.setId(1);

        assertEquals(subTask1, subTask2);
    }

    @Test
    void utilitarianClassShouldReturnInitializedInstances() {
        assertNotNull(taskManager, "Экземпляр таскменеджера не должен быть нулевым");
        assertTrue(taskManager instanceof InMemoryTaskManager, "TaskManager should be instance of InMemoryTaskManager");
    }

    @Test
    void testHistoryManagersReturnInitialized() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager);
        assertTrue(historyManager instanceof InMemoryHistoryManager);
    }

    @Test
    void addDifferentTypes() {
        Task task = new Task("Task 1", "description 1", TaskStatus.NEW);
        Epic epic = new Epic("Epic 1", "description 1");
        taskManager.addTask(task);
        taskManager.addEpic(epic);

        SubTask subTask = new SubTask("SubTask 1", "description 1", epic.getId(), TaskStatus.NEW);
        taskManager.addSubTask(subTask);

        Task addedTask = taskManager.getTask(task.getId());
        Epic addedEpic = taskManager.getEpic(epic.getId());
        SubTask addedSubTask = taskManager.getSubTaskById(subTask.getEpicId());

        assertNotNull(addedTask);
        assertNotNull(addedEpic);
        assertNotNull(addedSubTask);

        assertEquals(task.getTitle(), addedTask.getTitle());
        assertEquals(task.getDescription(), addedTask.getDescription());
        assertEquals(task.getStatus(), addedTask.getStatus());

        assertEquals(epic.getTitle(), addedEpic.getTitle());
        assertEquals(epic.getDescription(), addedEpic.getDescription());

        assertEquals(subTask.getTitle(), addedSubTask.getTitle());
        assertEquals(subTask.getDescription(), addedSubTask.getDescription());
        assertEquals(subTask.getStatus(), addedSubTask.getStatus());
    }

    @Test
    void taskWithGivenIdAndGeneratedId() {
        Task givenIdTask = new Task("Задача с заданным id", "Описание 1", TaskStatus.NEW);
        givenIdTask.setId(24);
        taskManager.addTask(givenIdTask);

        Task generationIdTask = new Task("Задача с генерированным id", "Описание 2", TaskStatus.NEW);
        taskManager.addTask(generationIdTask);

        Task addedGivenIdTask = taskManager.getTask(givenIdTask.getId());
        Task addedGenerationIdTask = taskManager.getTask(generationIdTask.getId());

        assertNotNull(addedGivenIdTask, "Задача с заданным id");
        assertNotNull(addedGenerationIdTask, "Задача с сгенерированным id");

        assertEquals(givenIdTask.getTitle(), addedGivenIdTask.getTitle());
        assertEquals(givenIdTask.getDescription(), addedGivenIdTask.getDescription());
        assertEquals(givenIdTask.getStatus(), addedGenerationIdTask.getStatus());
        assertEquals(generationIdTask.getTitle(), addedGenerationIdTask.getTitle());
        assertEquals(generationIdTask.getDescription(), addedGenerationIdTask.getDescription());
        assertEquals(generationIdTask.getStatus(), addedGenerationIdTask.getStatus());

        assertNotEquals(givenIdTask.getId(), generationIdTask.getId());
    }

    @Test
    void taskShouldBeUnchangedInTaskManager() {
        Task initialTask = new Task("Задача 1", "Описание 1", TaskStatus.NEW);
        taskManager.addTask(initialTask);

        Task secondTask = taskManager.getTask(initialTask.getId());

        assertEquals(initialTask.getTitle(), secondTask.getTitle());
        assertEquals(initialTask.getDescription(), secondTask.getDescription());
        assertEquals(initialTask.getId(), secondTask.getId());
        assertEquals(initialTask.getStatus(), secondTask.getStatus());
    }

}