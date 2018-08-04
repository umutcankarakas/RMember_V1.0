package retrofit.android.vogella.com.rmember.Database;

import java.util.List;

import io.reactivex.Flowable;
import retrofit.android.vogella.com.rmember.Model.User;

public class UserRepository implements  IUserDataSource {

    private IUserDataSource mLocalDataSource;

    private static UserRepository mInstance;

    public UserRepository(IUserDataSource mLovalDataSource) {
        this.mLocalDataSource = mLocalDataSource;
    }

    public static UserRepository getInstance(IUserDataSource mLocalDataSource){
        if(mInstance == null){
            mInstance = new UserRepository(mLocalDataSource);
        }
        return mInstance;
    }

    @Override
    public Flowable<User> getUserByEmail(String email) {
        return mLocalDataSource.getUserByEmail(email);
    }

    @Override
    public Flowable<List<User>> getAllUsers() {
        return mLocalDataSource.getAllUsers();
    }

    @Override
    public void insertUser(User... users) {
        mLocalDataSource.insertUser(users);

    }

    @Override
    public void updateUser(User... users) {
        mLocalDataSource.updateUser(users);
    }

    @Override
    public void deleteUser(User user) {
        mLocalDataSource.deleteUser(user);
    }

    @Override
    public void deleteAllUsers() {
        mLocalDataSource.deleteAllUsers();

    }
}
