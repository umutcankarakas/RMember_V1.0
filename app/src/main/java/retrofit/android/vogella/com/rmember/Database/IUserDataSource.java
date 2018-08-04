package retrofit.android.vogella.com.rmember.Database;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import retrofit.android.vogella.com.rmember.Model.User;

public interface IUserDataSource {
    Flowable<User> getUserByEmail(String email);
    Flowable<List<User>> getAllUsers();
    void insertUser(User... users);
    void updateUser(User... users);
    void deleteUser(User user);
    void deleteAllUsers();

}
