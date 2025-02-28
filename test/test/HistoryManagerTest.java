package test;

import manager.manager.HistoryManager;
import manager.manager.InMemoryHistoryManager;
import manager.manager.Managers;
import manager.tasks.Task;
import manager.tasks.TaskStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {

    @Test
    void historyManagerReturnInitialized() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager);
        assertTrue(historyManager instanceof InMemoryHistoryManager);
    }

    @Test
    void historyManagerShouldSavePreviousVersionTask() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        Task task = new Task("Задача 1", "Описание 1", TaskStatus.NEW);
        historyManager.addHistory(task);
        Task taskFromHistory = historyManager.getHistory().get(0);

        Assertions.assertEquals(task.getTitle(), taskFromHistory.getTitle());
        Assertions.assertEquals(task.getDescription(), taskFromHistory.getDescription());
        Assertions.assertEquals(task.getId(), taskFromHistory.getId());
        Assertions.assertEquals(task.getStatus(), taskFromHistory.getStatus());
    }

}