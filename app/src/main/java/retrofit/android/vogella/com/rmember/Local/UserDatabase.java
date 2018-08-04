package retrofit.android.vogella.com.rmember.Local;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import retrofit.android.vogella.com.rmember.Model.User;

import static retrofit.android.vogella.com.rmember.Local.UserDatabase.DATABASE_VERSION;

@Database(entities = User.class, version = DATABASE_VERSION)
public abstract class UserDatabase extends RoomDatabase{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EDMT-User-Database-Room";  //BURAYA DİKKAT*************************

    public abstract UserDAO userDAO();

    private static UserDatabase mInstance;

    public static UserDatabase getmInstance(Context context){
        if(mInstance == null){

            mInstance = Room.databaseBuilder(context, UserDatabase.class,DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mInstance;
    }

}
