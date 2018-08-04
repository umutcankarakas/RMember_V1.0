package retrofit.android.vogella.com.rmember.Database;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import retrofit.android.vogella.com.rmember.Model.Task;

public interface ITaskDataSource {
    public Flowable<Task> getTaskById(int TaskId);
    public Flowable<List<Task>> getAllTasks();
    public void insertTask(Task... tasks);
    public void updateTask(Task... tasks);
    public void deleteTask(Task task);
    public void deleteAllTasks();

}
