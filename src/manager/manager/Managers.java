package manager.manager;

public class Managers {
    public static TaskManager getDefaultManager() {
return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
