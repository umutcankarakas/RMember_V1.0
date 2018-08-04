package retrofit.android.vogella.com.rmember;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.lifecycle.ViewModelStoreOwner;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import retrofit.android.vogella.com.rmember.Interface.LoginResultCallbacks;
import retrofit.android.vogella.com.rmember.ViewModel.LoginViewModel;
import retrofit.android.vogella.com.rmember.ViewModel.LoginViewModelFactory;
import retrofit.android.vogella.com.rmember.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity  implements LoginResultCallbacks{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        activityMainBinding.setViewModel(ViewModelProviders.of(
                this,
                new LoginViewModelFactory(this))
        .get(LoginViewModel.class));

    }
    public void goRegister(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSuccess(String message) {
        Toasty.success(this,message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String message) {
        Toasty.error(this,message, Toast.LENGTH_SHORT).show();
    }
}
