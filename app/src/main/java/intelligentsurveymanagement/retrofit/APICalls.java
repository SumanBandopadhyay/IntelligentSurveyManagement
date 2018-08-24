package intelligentsurveymanagement.retrofit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.suman.intelligentsurveymanagement.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import intelligentsurveymanagement.activities.DigitalFormActivity;
import intelligentsurveymanagement.activities.LoginActivity;
import intelligentsurveymanagement.database.AppDatabase;
import intelligentsurveymanagement.entity.Job;
import intelligentsurveymanagement.entity.JobForm;
import intelligentsurveymanagement.entity.LoginForm;
import intelligentsurveymanagement.entity.ModelApiResponse;
import intelligentsurveymanagement.executor.AppExecutors;
import intelligentsurveymanagement.utils.DatabaseInitializer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APICalls {

    private static final String TAG = "APICalls";

    public static Map<String, String> setHeaders(SharedPreferences sharedPreferences) {
        String jwtToken = sharedPreferences.getString(String.valueOf(R.string.jwtToken), null);
        Map<String, String> map = new HashMap<>();
        map.put("Accept", "application/json");
        if (jwtToken != null) {
            map.put("Authorization", jwtToken);
        }
        return map;
    }

    public static void loginUserWithNetwork(ItemsService mService, AppExecutors executors, Context context, String username, String password) {
        LoginForm loginForm = new LoginForm();
        loginForm.setUsername(username);
        loginForm.setPassword(password);
        executors.getNetworkIO().execute(() -> {
            mService.login(loginForm, setHeaders(LoginActivity.sharedPreferences)).enqueue(new Callback<ModelApiResponse>() {
                @Override
                public void onResponse(Call<ModelApiResponse> call, Response<ModelApiResponse> response) {
                    if (response.isSuccessful()) {
//                        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
                        String jwtToken = response.body().getMessage();
                        SharedPreferences.Editor editor = LoginActivity.sharedPreferences.edit();
                        editor.putString(context.getString(R.string.jwtToken), jwtToken);
                        editor.apply();
                        context.startActivity(new Intent(context, DigitalFormActivity.class));
                        Log.e(TAG, "JWT Key : " + jwtToken);
                    } else {
                        int statusCode = response.code();
                        Log.e(TAG, "Status Code : " + statusCode + "; Error : " + response.errorBody() + response.raw());
                        Toast.makeText(context, "Invalid Username/Password", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ModelApiResponse> call, Throwable t) {
                    Log.e(TAG, "Error during API call" + call.toString() + t);
                    Toast.makeText(context, "Error during API call", Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    public static void populateJobFromNetwork(ItemsService mService, AppExecutors executors, Context context) {
        executors.getNetworkIO().execute(() -> {
            mService.getJobs(APICalls.setHeaders(DigitalFormActivity.sharedPreferences)).enqueue(new Callback<List<Job>>() {
                @Override
                public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                    if (response.isSuccessful()) {
                        DigitalFormActivity.jobs = response.body();
//                        for (int i = 0; i < response.body().size(); i++) {
//                            Log.e(TAG, "Jobs : " + response.body().get(i).getJobTitle());
//                        }
                    } else {
                        int statusCode = response.code();
                        Log.e(TAG, "Status Code : " + statusCode + "; Error : " + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<List<Job>> call, Throwable t) {
                    Log.e(TAG, "Error during API call" + call.toString() + t);
                }
            });
        });
    }

    public void populateJobsByUsernameFromNetwork(ItemsService mService, AppExecutors executors, Context context, String username) {
        executors.getNetworkIO().execute(() -> {
            mService.getJobsByUsername(username, setHeaders(DigitalFormActivity.sharedPreferences)).enqueue(new Callback<List<Job>>() {
                @Override
                public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                    if (response.isSuccessful()) {
                        for (int i = 0; i < response.body().size(); i++) {
                            Log.e(TAG, "Jobs for username : " + response.body().get(i).getJobTitle());
                        }
                    } else {
                        int statusCode = response.code();
                        Log.e(TAG, "Status Code : " + statusCode + "; Error : " + response.errorBody() + response.raw());
                    }
                }

                @Override
                public void onFailure(Call<List<Job>> call, Throwable t) {
                    Log.e(TAG, "Error during API call" + call.toString() + t);
                }
            });
        });
    }

    public void populateFormByIdFromNetwork(ItemsService mService, AppExecutors executors, Context context, int formId) {
        executors.getNetworkIO().execute(() -> {
            mService.getForm(formId, setHeaders(DigitalFormActivity.sharedPreferences)).enqueue(new Callback<List<JobForm>>() {
                @Override
                public void onResponse(Call<List<JobForm>> call, Response<List<JobForm>> response) {
                    if (response.isSuccessful()) {
                        for (int i = 0; i < response.body().size(); i++) {
                            Log.e(TAG, "Form : " + response.body().get(i).getCustomerSignUrl());
                        }
                    } else {
                        int statusCode = response.code();
                        Log.e(TAG, "Status Code : " + statusCode + "; Error : " + response.errorBody() + response.raw());
                    }
                }

                @Override
                public void onFailure(Call<List<JobForm>> call, Throwable t) {
                    Log.e(TAG, "Error during API call" + call.toString() + t);
                }
            });
        });
    }

}
