package manager.Main;

import manager.manager.TaskManager;
import manager.tasks.Epic;
import manager.tasks.SubTask;
import manager.tasks.Task;
import manager.tasks.TaskStatus;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Задача 1", "Описание 1", TaskStatus.NEW);
        Task task2 = new Task("Задача 2", "Описание 2", TaskStatus.IN_PROGRESS);
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Epic epic1 = new Epic("Эпик 1", "Описание 1");
        Epic epic2 = new Epic("Эпик 2", "Описание 2");
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        SubTask subTask1 = new SubTask("Сабтаск 1", "Описание 1", epic1.getId(), TaskStatus.NEW);
        SubTask subTask2 = new SubTask("Сабтаск 2", "Описание 2", epic1.getId(), TaskStatus.IN_PROGRESS);
        SubTask subTask3 = new SubTask("Сабтаск 3", "Описание 3", epic2.getId(), TaskStatus.DONE);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);

        System.out.println("Задачи: " + taskManager.getAllTasks());
        System.out.println("Эпики: " + taskManager.getAllEpics());
        System.out.println("Сабтаски эпика 1: " + taskManager.getSubtasksForEpic(epic1.getId()));
        System.out.println("Сабтаски эпика 2: " + taskManager.getSubtasksForEpic(epic2.getId()));

        taskManager.deleteTaskById(task1.getId());
        taskManager.deleteEpicById(epic2.getId());

        System.out.println("Задачи: " + taskManager.getAllTasks());
        System.out.println("Эпики: " + taskManager.getAllEpics());
        System.out.println("Сабтаски эпика 1: " + taskManager.getSubtasksForEpic(epic1.getId()));
        System.out.println("Сабтаски эпика 2: " + taskManager.getSubtasksForEpic(epic2.getId()));
    }
}

