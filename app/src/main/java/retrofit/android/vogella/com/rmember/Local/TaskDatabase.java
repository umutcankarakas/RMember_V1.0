package retrofit.android.vogella.com.rmember.Local;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import retrofit.android.vogella.com.rmember.Model.Task;

import static retrofit.android.vogella.com.rmember.Local.TaskDatabase.DATABASE_VERSION;

@Database(entities = Task.class, version = DATABASE_VERSION)
public abstract class TaskDatabase extends RoomDatabase{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EDMT-Database-Room";

    public abstract TaskDAO taskDAO();

    private static TaskDatabase mInstance;

    public static TaskDatabase getmInstance(Context context){
        if(mInstance == null){

            mInstance = Room.databaseBuilder(context, TaskDatabase.class,DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return mInstance;
    }

}