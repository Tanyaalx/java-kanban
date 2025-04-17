package test;

import manager.manager.FileBackedTaskManager;
import manager.tasks.Epic;
import manager.tasks.SubTask;
import manager.tasks.Task;
import manager.tasks.TaskStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileBackedTaskManagerTest {
    private File file;
    private FileBackedTaskManager manager;

    @BeforeEach
    void setUp() throws IOException {
        file = File.createTempFile("taskManagerTest", ".csv");
        manager = new FileBackedTaskManager(file);
    }

    @AfterEach
    void deleteFile() {
        file.delete();
    }

    @Test
    void saveAndLoad() {
        manager = FileBackedTaskManager.loadFromFile(file);
        assertTrue(manager.getAllTasks().isEmpty(), "Список должен быть пуст");

    }

    @Test
    void saveAndLoadTasks() throws IOException {
        Task task1 = new Task("Таск 1", "Описание 1", TaskStatus.NEW);
        Task task2 = new Task("Таск 2", "Описание 2", TaskStatus.NEW);
        manager.addTask(task1);
        manager.addTask(task2);
        System.out.println("Файл после сохранения: " + Files.readString(file.toPath()));

        Epic epic1 = new Epic("Эпик 1", "Описание 1");
        Epic epic2 = new Epic("Эпик 2", "Описание 2");
        manager.addEpic(epic1);
        manager.addEpic(epic2);
        System.out.println("Файл после сохранения: " + Files.readString(file.toPath()));

        SubTask subTask1 = new SubTask("Сабтаск 1", "Описание 1", epic1.getId(), TaskStatus.NEW);
        SubTask subTask2 = new SubTask("Сабтаск 2", "Описание 2", epic1.getId(), TaskStatus.NEW);
        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);
        System.out.println("Файл после сохранения: " + Files.readString(file.toPath()));

        FileBackedTaskManager loaded = FileBackedTaskManager.loadFromFile(file);

        System.out.println("Оригинальный id: " + task1.getId());
        System.out.println("Загруженный id: " + loaded.getTask(task1.getId()));

        System.out.println("Оригинальный id: " + task2.getId());
        System.out.println("Загруженный id: " + loaded.getTask(task2.getId()));

        System.out.println("Оригинальный id: " + epic1.getId());
        System.out.println("Загруженный id: " + loaded.getEpic(epic1.getId()));

        System.out.println("Оригинальный id: " + epic2.getId());
        System.out.println("Загруженный id: " + loaded.getEpic(epic2.getId()));

        System.out.println("Оригинальный id: " + subTask1.getId());
        System.out.println("Загруженный id: " + loaded.getSubTaskById(subTask1.getId()));

        System.out.println("Оригинальный id: " + subTask2.getId());
        System.out.println("Загруженный id: " + loaded.getSubTaskById(subTask2.getId()));

        assertEquals(2, loaded.getAllTasks().size(), "Неверное количество тасок");
        assertEquals(2, loaded.getAllEpics().size(), "Неверное количество эпиков");
        assertEquals(2, loaded.getAllSubtasks().size(), "Неверное количество сабтасков");

        assertEquals(task1, loaded.getTask(task1.getId()), "Таска 1 не совпадает");
        assertEquals(task2, loaded.getTask(task2.getId()), "Таска 2 не совпадает");
        assertEquals(epic1, loaded.getEpic(epic1.getId()), "Эпик 1 не совпадает");
        assertEquals(epic2, loaded.getEpic(epic2.getId()), "Эпик 2 не совпадает");
        assertEquals(subTask1, loaded.getSubTaskById(subTask1.getId()), "Сабтаск 1 не совпадает");
        assertEquals(subTask2, loaded.getSubTaskById(subTask2.getId()), "Сабтаск 2 не совпадает");
    }
}
