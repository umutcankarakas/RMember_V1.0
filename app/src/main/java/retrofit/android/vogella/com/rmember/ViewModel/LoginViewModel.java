package retrofit.android.vogella.com.rmember.ViewModel;

import android.arch.lifecycle.ViewModel;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import retrofit.android.vogella.com.rmember.Interface.LoginResultCallbacks;
import retrofit.android.vogella.com.rmember.Model.User;

public class LoginViewModel extends ViewModel {
    private User user;
    private LoginResultCallbacks loginResultCallbacks;

    public LoginViewModel(LoginResultCallbacks loginResultCallbacks) {
        this.loginResultCallbacks = loginResultCallbacks;
        this.user = new User();
    }

    public TextWatcher getEmailTextWatcher(){

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                user.setEmail(s.toString());
            }
        };
    }

    public TextWatcher getPasswordTextWatcher(){

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                user.setPassword(s.toString());
            }
        };
    }

    public void onLoginClicked(View view){
        int errorCode = user.isValidData();

        if(errorCode == 0)
            loginResultCallbacks.onError("You must enter email address!");
        else if(errorCode == 1)
            loginResultCallbacks.onError("Your email is invalid");
        else if(errorCode == 2)
            loginResultCallbacks.onError("Password length must be greater than 6 characters");
        else
            loginResultCallbacks.onSuccess("Login success");
    }
}

