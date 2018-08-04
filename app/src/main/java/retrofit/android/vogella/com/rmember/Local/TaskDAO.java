package retrofit.android.vogella.com.rmember.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import retrofit.android.vogella.com.rmember.Model.Task;


@Dao
public interface TaskDAO {

    @Query("SELECT * FROM tasks WHERE taskid=:taskId")
    Flowable<Task> getTaskById(int taskId);

    @Query("SELECT * FROM tasks")
    Flowable<List<Task>> getAllTasks();

    @Insert
    void insertTask(Task... tasks);

    @Update
    void updateTask(Task... tasks);

    @Delete
    void deleteTask(Task task);

    @Query("DELETE FROM tasks")
    void deleteAllTasks();

}