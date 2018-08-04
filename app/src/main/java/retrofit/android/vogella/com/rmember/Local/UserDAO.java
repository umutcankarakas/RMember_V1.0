package retrofit.android.vogella.com.rmember.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import retrofit.android.vogella.com.rmember.Model.User;


@Dao
public interface UserDAO {

    @Query("SELECT * FROM users WHERE email=:email")
    Flowable<User> getUserByEmail(String email);

    @Query("SELECT * FROM users")
    Flowable<List<User>> getAllUsers();

    @Insert
    void insertUser(User... users);

    @Update
    void updateUser(User... users);

    @Delete
    void deleteUser(User user);

    @Query("DELETE FROM users")
    void deleteAllUsers();

}
