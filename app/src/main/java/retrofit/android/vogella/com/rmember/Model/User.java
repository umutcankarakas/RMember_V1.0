package retrofit.android.vogella.com.rmember.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Patterns;

@Entity(tableName = "loginuser")
public class User extends BaseObservable {

    @NonNull

    private String email, password;

    public User() {
    }

    @Ignore
    public User(@NonNull String email, @NonNull String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    @NonNull
    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public int isValidData(){
        if(TextUtils.isEmpty((getEmail())))
            return 0;
        else if(!Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches())
            return 1;
        else if(getPassword().length() < 6)
            return 2;
        else
            return -1;
    }
}
