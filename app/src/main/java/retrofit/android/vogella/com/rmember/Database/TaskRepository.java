package retrofit.android.vogella.com.rmember.Database;

import java.util.List;

import io.reactivex.Flowable;
import retrofit.android.vogella.com.rmember.Model.Task;

public class TaskRepository implements  ITaskDataSource {

    private ITaskDataSource mLocalDataSource;

    private static TaskRepository mInstance;

    public TaskRepository(ITaskDataSource mLovalDataSource) {
        this.mLocalDataSource = mLocalDataSource;
    }

    public static TaskRepository getInstance(ITaskDataSource mLocalDataSource){
        if(mInstance == null){
            mInstance = new TaskRepository(mLocalDataSource);
        }
        return mInstance;
    }

    @Override
    public Flowable<Task> getTaskById(int TaskId) {
        return mLocalDataSource.getTaskById(TaskId);
    }

    @Override
    public Flowable<List<Task>> getAllTasks() {
        return mLocalDataSource.getAllTasks();
    }

    @Override
    public void insertTask(Task... tasks) {
        mLocalDataSource.insertTask(tasks);

    }

    @Override
    public void updateTask(Task... tasks) {
        mLocalDataSource.updateTask(tasks);
    }

    @Override
    public void deleteTask(Task task) {
        mLocalDataSource.deleteTask(task);
    }

    @Override
    public void deleteAllTasks() {
        mLocalDataSource.deleteAllTasks();

    }
}
