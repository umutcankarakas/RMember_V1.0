package retrofit.android.vogella.com.rmember.Local;

import java.util.List;

import io.reactivex.Flowable;
import retrofit.android.vogella.com.rmember.Database.IUserDataSource;
import retrofit.android.vogella.com.rmember.Model.User;

public class UserDataSource implements IUserDataSource {

    private UserDAO userDAO;
    private static UserDataSource mInstance;

    public UserDataSource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public static UserDataSource getInstance(UserDAO userDAO){
        if(mInstance == null){
            mInstance = new UserDataSource(userDAO);
        }
        return mInstance;
    }

    @Override
    public Flowable<User> getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    @Override
    public Flowable<List<User>> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public void insertUser(User... users) {
        userDAO.insertUser(users);
    }

    @Override
    public void updateUser(User... users) {
        userDAO.updateUser(users);
    }

    @Override
    public void deleteUser(User user) {
        userDAO.deleteUser(user);
    }

    @Override
    public void deleteAllUsers() {
        userDAO.deleteAllUsers();
    }
}
