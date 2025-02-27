package manager.manager;

import manager.tasks.Epic;
import manager.tasks.SubTask;
import manager.tasks.Task;

import java.util.ArrayList;

public interface TaskManager {

    Task addTask(Task task);

    void deleteTaskById(int taskId);

    Task getTask(int taskId);

    ArrayList<Task> getAllTasks();

    void deleteAllTasks();

    void updateTask(Task task);


    void addEpic(Epic epic);

    void deleteEpicById(int epicId);

    Epic getEpic(int epicId);

    ArrayList<Epic> getAllEpics();

    void deleteEpics();

    void updateEpics(Epic epic);


    void addSubTask(SubTask subTask);

    void deleteSubTaskById(int subTaskId);

    SubTask getSubTaskById(int id);

    ArrayList<SubTask> getSubtasksForEpic(int epicId);

    void updateEpicStatus(Epic epic);

    void updateSubTasks(SubTask subTask);


}
