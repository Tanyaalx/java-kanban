package manager.Main;

import manager.manager.HistoryManager;
import manager.manager.InMemoryTaskManager;
import manager.manager.Managers;
import manager.tasks.Epic;
import manager.tasks.SubTask;
import manager.tasks.Task;
import manager.tasks.TaskStatus;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        Task task1 = new Task("Задача 1", "Описание 1", TaskStatus.NEW);
        Task task2 = new Task("Задача 2", "Описание 2", TaskStatus.IN_PROGRESS);
        Task task3 = new Task("Задача 3", "Описание 3", TaskStatus.DONE);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        HistoryManager historyManager = Managers.getDefaultHistory();
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task1);
        System.out.println("История: ");
        for (Task tasks : historyManager.getHistory()) {
            System.out.println(String.format(" - ID: %d, Название: %s, Описание: %s, Статус: %s",
                    tasks.getId(), tasks.getTitle(), tasks.getDescription(), tasks.getStatus()));
        }
        List<Task> history = historyManager.getHistory();
        System.out.println(history);

        Epic epic1 = new Epic("Эпик 1", "Описание 1");
        Epic epic2 = new Epic("Эпик 2", "Описание 2");
        Epic epic3 = new Epic("Эпик 3", "Описание 3");
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        taskManager.addEpic(epic3);

        SubTask subTask1 = new SubTask("Сабтаск 1", "Описание 1", epic1.getId(), TaskStatus.NEW);
        SubTask subTask2 = new SubTask("Сабтаск 2", "Описание 2", epic1.getId(), TaskStatus.IN_PROGRESS);
        SubTask subTask3 = new SubTask("Сабтаск 3", "Описание 3", epic1.getId(), TaskStatus.DONE);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);

        SubTask subTask4 = new SubTask("Сабтаск 4", "Описание 4", epic2.getId(), TaskStatus.NEW);
        SubTask subTask5 = new SubTask("Сабтаск 5", "Описание 5", epic2.getId(), TaskStatus.NEW);
        SubTask subTask6 = new SubTask("Сабтаск 6", "Описание 6", epic2.getId(), TaskStatus.NEW);
        taskManager.addSubTask(subTask4);
        taskManager.addSubTask(subTask5);
        taskManager.addSubTask(subTask6);

        SubTask subTask7 = new SubTask("Сабтаск 7", "Описание 7", epic3.getId(), TaskStatus.DONE);
        SubTask subTask8 = new SubTask("Сабтаск 8", "Описание 8", epic3.getId(), TaskStatus.DONE);
        SubTask subTask9 = new SubTask("Сабтаск 9", "Описание 9", epic3.getId(), TaskStatus.DONE);
        taskManager.addSubTask(subTask7);
        taskManager.addSubTask(subTask8);
        taskManager.addSubTask(subTask9);

        System.out.println("Таски:");
        for (Task task : taskManager.getAllTasks()) {
            System.out.println(String.format(" - ID: %d, Название: %s, Описание: %s, Статус: %s",
                    task.getId(), task.getTitle(), task.getDescription(), task.getStatus()));
        }

        System.out.println("Эпики:");
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(String.format(" - ID: %d, Название: %s, Описание: %s, Статус: %s",
                    epic.getId(), epic.getTitle(), epic.getDescription(), epic.getStatus()));
        }

        System.out.println("Сабтаски эпика 1:");
        for (SubTask subTask : taskManager.getSubtasksForEpic(epic1.getId())) {
            System.out.println(String.format(" - ID: %d, Название: %s, Описание: %s, Статус: %s",
                    subTask.getId(), subTask.getTitle(), subTask.getDescription(), subTask.getStatus()));
        }

        System.out.println("Сабтаски эпика 2:");
        for (SubTask subTask : taskManager.getSubtasksForEpic(epic2.getId())) {
            System.out.println(String.format(" - ID: %d, Название: %s, Описание: %s, Статус: %s",
                    subTask.getId(), subTask.getTitle(), subTask.getDescription(), subTask.getStatus()));
        }

        System.out.println("Сабтаски эпика 3:");
        for (SubTask subTask : taskManager.getSubtasksForEpic(epic3.getId())) {
            System.out.println(String.format(" - ID: %d, Название: %s, Описание: %s, Статус: %s",
                    subTask.getId(), subTask.getTitle(), subTask.getDescription(), subTask.getStatus()));
        }

        task1.setDescription("Новое описание задачи 1");
        task1.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateTask(task1);

        taskManager.deleteTaskById(task2.getId());

        taskManager.deleteEpicById(epic1.getId());

        taskManager.deleteSubTaskById(subTask9.getId());

        System.out.println("Удаление:");
        System.out.println(String.format(" - ID: %d, Название: %s, Описание: %s, Статус: %s",
                task2.getId(), task2.getTitle(), task2.getDescription(), task2.getStatus()));
        System.out.println(String.format(" - ID: %d, Название: %s, Описание: %s, Статус: %s",
                epic1.getId(), epic1.getTitle(), epic1.getDescription(), epic1.getStatus()));
        System.out.println(String.format(" - ID: %d, Название: %s, Описание: %s, Статус: %s",
                subTask9.getId(), subTask9.getTitle(), subTask9.getDescription(), subTask9.getStatus()));

        System.out.println("Обновление:");
        System.out.println(String.format(" - ID: %d, Название: %s, Описание: %s, Статус: %s",
                task1.getId(), task1.getTitle(), task1.getDescription(), task1.getStatus()));

        System.out.println("Все таски после обновления и удаления:");
        for (Task task : taskManager.getAllTasks()) {
            System.out.println(String.format(" - ID: %d, Название: %s, Описание: %s, Статус: %s",
                    task.getId(), task.getTitle(), task.getDescription(), task.getStatus()));
        }

        System.out.println("Все эпики после обновления и удаления:");
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(String.format(" - ID: %d, Название: %s, Описание: %s, Статус: %s",
                    epic.getId(), epic.getTitle(), epic.getDescription(), epic.getStatus()));
        }

        System.out.println("Все сабтаски после обновления и удаления:");
        for (SubTask subTask : taskManager.getAllSubtasks()) {
            System.out.println(String.format(" - ID: %d, Название: %s, Описание: %s, Статус: %s",
                    subTask.getId(), subTask.getTitle(), subTask.getDescription(), subTask.getStatus()));
        }
    }
}

