package retrofit.android.vogella.com.rmember.Model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "tasks")
public class Task {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "taskid")
    private int taskid;

    @ColumnInfo(name = "taskTitle")
    private String taskTitle;

    @ColumnInfo(name="taskDetail")
    private String taskDetail;

   /* @ColumnInfo(name="taskDate")
    private String taskDate;

    @ColumnInfo(name="userid")
    private String userid;*/

    @Ignore
    public Task(String taskTitle, String taskDetail){
        this.taskTitle = taskTitle;
        this.taskDetail = taskDetail;

    }

    @NonNull
    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(@NonNull int taskid) {
        this.taskid = taskid;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDetail() {
        return taskDetail;
    }

    public void setTaskDetail(String taskDetail) {
        this.taskDetail = taskDetail;
    }

    /* public String getTaskDate() {
                return taskDate;
            }

            public void setTaskDate(String taskDate) {
                this.taskDate = taskDate;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }
            */
    public String toString() {
        return new StringBuilder(taskTitle).append("\n").append(taskDetail).toString();
    }
}