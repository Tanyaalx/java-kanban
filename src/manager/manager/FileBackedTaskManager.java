package manager.manager;

import manager.tasks.Epic;
import manager.tasks.SubTask;
import manager.tasks.Task;
import manager.exception.ManagerSaveException;
import manager.tasks.TaskStatus;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class FileBackedTaskManager extends InMemoryTaskManager {
    private File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);

        try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            System.out.println("Загрузка из файла: " + file.getPath());
            br.readLine();

            while (br.ready()) {
                String line = br.readLine();
                if (line.isEmpty()) {
                    continue;
                }

                Task task = fromString(line);
                int taskId = task.getId();

                switch (task.getType()) {
                    case TASKS_TYPES:
                        fileBackedTaskManager.tasks.put(taskId, task);
                        break;
                    case EPIC_TYPES:
                        fileBackedTaskManager.epics.put(taskId, (Epic) task);
                        break;
                    case SUBTASK_TYPES:
                        fileBackedTaskManager.subTasks.put(taskId, (SubTask) task);
                        break;
                }
            }
        } catch (IOException e) {
            String errorMessage = "Произошла ошибка во время чтения файла " + e.getMessage();
            System.out.println(errorMessage);
            throw new ManagerSaveException(errorMessage);
        }
        return fileBackedTaskManager;
    }

    public static String toString(Task task) {
        return String.format("%d,%s,%s,%s,%s,%s",
                task.getId(),
                task.getType(),
                task.getTitle(),
                task.getStatus(),
                task.getDescription(),
                (task instanceof SubTask ? ((SubTask) task).getEpicId() : "")
        );
    }

    public static Task fromString(String value) {
        String[] param = value.split(",");

        if (param.length < 5) {
            System.out.println("Некорректный формат строки " + value);
            return null;
        }

        int id = Integer.parseInt(param[0]);
        String type = param[1];
        if (type == null || type.isEmpty()) {
            String errorMessage = "Некорректный формат строки, отсутствует тип задачи ";
            throw new IllegalArgumentException(errorMessage + value);
        }
        String title = param[2];
        TaskStatus status = TaskStatus.valueOf(param[3]);
        String description = param[4];

        switch (type) {
            case "EPIC_TYPES":
                Epic epic = new Epic(title, description);
                epic.setId(Integer.parseInt(param[0]));
                epic.setStatus(TaskStatus.valueOf(param[3]));
                epic.setType(TasksTypes.EPIC_TYPES);
                return epic;

            case "TASKS_TYPES":
                Task task = new Task(title, description, status);
                task.setId(id);
                task.setType(TasksTypes.TASKS_TYPES);
                return task;

            case "SUBTASK_TYPES":
                if (param.length < 6) {
                    System.out.println("Ошибка: у сабтаска нет epicId " + value);
                    return null;
                }
                int epicId = Integer.parseInt(param[5]);
                SubTask subtask = new SubTask(title, description, epicId, status);
                subtask.setId(id);
                subtask.setType(TasksTypes.SUBTASK_TYPES);
                return subtask;

            default:
                String errorMessage = "Неизвестный тип задачи: ";
                throw new IllegalArgumentException(errorMessage + type);
        }
    }

    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("id,type,name,status,description,epic");

            for (Task task : tasks.values()) {
                writer.write(toString(task));
            }
            for (Epic epic : epics.values()) {
                writer.write(toString(epic));
            }
            for (SubTask subTask : subTasks.values()) {
                writer.write(toString(subTask));
            }

        } catch (IOException e) {
            String errorMessage = "Ошибка при сохранении в файл" + e.getMessage();
            System.out.println(errorMessage);
            throw new ManagerSaveException(errorMessage);
        }
    }

    @Override
    public Task addTask(Task task) {
        super.addTask(task);
        save();
        return task;
    }

    @Override
    public void deleteTaskById(int taskId) {
        super.deleteTaskById(taskId);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void deleteEpicById(int epicId) {
        super.deleteEpicById(epicId);
        save();
    }

    @Override
    public void deleteEpics() {
        super.deleteEpics();
        save();
    }

    @Override
    public void updateEpics(Epic epic) {
        super.updateEpics(epic);
        save();
    }

    @Override
    public void addSubTask(SubTask subTask) {
        super.addSubTask(subTask);
        save();
    }

    @Override
    public void deleteSubTaskById(int subTaskId) {
        super.deleteSubTaskById(subTaskId);
        save();
    }

    @Override
    public void updateEpicStatus(Epic epic) {
        super.updateEpicStatus(epic);
        save();
    }

    @Override
    public void updateSubTasks(SubTask subTask) {
        super.updateSubTasks(subTask);
        save();
    }

    @Override
    public ArrayList<SubTask> getAllSubtasks() {
        return super.getAllSubtasks();
    }
}
