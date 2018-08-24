package intelligentsurveymanagement.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.suman.intelligentsurveymanagement.R;

import intelligentsurveymanagement.executor.AppExecutors;
import intelligentsurveymanagement.retrofit.APICalls;
import intelligentsurveymanagement.retrofit.ApiUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputUsername;
    private EditText inputPassword;
    private AppCompatButton btnLogin;

    public static SharedPreferences sharedPreferences;

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputUsername = (EditText) findViewById(R.id.input_username);
        inputPassword = (EditText) findViewById(R.id.input_password);
        btnLogin = (AppCompatButton) findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(this);

        sharedPreferences = getPreferences(MODE_PRIVATE);
        String jwtToken = sharedPreferences.getString(getString(R.string.jwtToken), null);
        Log.e(TAG, "JWT Token in Login : " + jwtToken);
        jwtToken = sharedPreferences.getString(getString(R.string.jwtToken), null);
        if (jwtToken != null) {
            Intent intent = new Intent(LoginActivity.this, DigitalFormActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onClick(View v) {
        APICalls.loginUserWithNetwork(ApiUtils.getItemsService(), new AppExecutors(), this, inputUsername.getText().toString(), inputPassword.getText().toString());
    }
}
