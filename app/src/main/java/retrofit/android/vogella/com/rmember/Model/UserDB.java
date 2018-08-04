package retrofit.android.vogella.com.rmember.Model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "users")
public class UserDB {
    @NonNull

    @PrimaryKey
    @ColumnInfo(name="email")
    private String email;

    @ColumnInfo(name = "name")
    private String name;


    @ColumnInfo(name = "passweord")
    private String password;

    @Ignore
    public UserDB(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;


    }

    @NonNull

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return new StringBuilder(name).append("\n").append(email).append("\n").append(password).toString();
    }
}
