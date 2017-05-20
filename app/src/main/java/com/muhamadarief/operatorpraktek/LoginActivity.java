package com.muhamadarief.operatorpraktek;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.muhamadarief.operatorpraktek.API.ApiService;
import com.muhamadarief.operatorpraktek.Model.User;
import com.muhamadarief.operatorpraktek.Utils.ApiUtils;
import com.muhamadarief.operatorpraktek.Utils.PrefUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";

    @BindView(R.id.edt_email)
    EditText edtEmail;

    @BindView(R.id.edt_password)
    EditText edtPassword;

    @BindView(R.id.input_layout_email)
    TextInputLayout inputEmail;

    @BindView(R.id.input_layout_password)
    TextInputLayout inputPassword;

    @BindView(R.id.txt_login)
    TextView btn_login;

    private ApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setElevation(0);
        mApiService = ApiUtils.getApiService();

        if (isSessionLogin()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @OnClick(R.id.txt_login)
    public void onBtnLoginClick() {
        loginAct();
    }

    private void loginAct() {
        String email = edtEmail.getText().toString();
        String passwowrd = edtPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            inputEmail.setError("Enter valid email address");
            requestFocus(edtEmail);
            return;
        } else {
            inputEmail.setErrorEnabled(false);
        }

        if (TextUtils.isEmpty(passwowrd)) {
            inputPassword.setError("Enter your password");
            requestFocus(edtPassword);
            return;
        } else {
            inputPassword.setErrorEnabled(false);
        }

        mApiService.login(email, passwowrd).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = (User) response.body();

                if (user != null) {
                    if (!user.isError()) {
                        PrefUtil.putUser(LoginActivity.this, PrefUtil.USER_SESSION, user);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                }

                Toast.makeText(LoginActivity.this, "" + user.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "An error Occurred", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " + t);
            }
        });


    }

    public boolean isSessionLogin() {
        return PrefUtil.getUser(this, PrefUtil.USER_SESSION) != null;
    }
}
