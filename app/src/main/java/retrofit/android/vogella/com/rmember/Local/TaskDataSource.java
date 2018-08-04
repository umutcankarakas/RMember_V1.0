package retrofit.android.vogella.com.rmember.Local;

import java.util.List;

import io.reactivex.Flowable;
import retrofit.android.vogella.com.rmember.Database.ITaskDataSource;
import retrofit.android.vogella.com.rmember.Model.Task;
public class TaskDataSource implements ITaskDataSource {

    private TaskDAO taskDAO;
    private static TaskDataSource mInstance;

    public TaskDataSource(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public static TaskDataSource getInstance(TaskDAO taskDAO){
        if(mInstance == null){
            mInstance = new TaskDataSource(taskDAO);
        }
        return mInstance;
    }

    @Override
    public Flowable<Task> getTaskById(int taskId) {
        return taskDAO.getTaskById(taskId);
    }

    @Override
    public Flowable<List<Task>> getAllTasks() {
        return taskDAO.getAllTasks();
    }

    @Override
    public void insertTask(Task... tasks) {
        taskDAO.insertTask(tasks);
    }

    @Override
    public void updateTask(Task... tasks) {
        taskDAO.updateTask(tasks);
    }

    @Override
    public void deleteTask(Task task) {
        taskDAO.deleteTask(task);
    }

    @Override
    public void deleteAllTasks() {
        taskDAO.deleteAllTasks();
    }
}
